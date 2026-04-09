package org.rpg.rPGCraft;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.Definitions.EntityDefinitions;
import org.rpg.rPGCraft.Definitions.StateDefinitions;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.Entities.LegendaryComponent;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityManager implements Listener
{
    private Main main;

    public EntityManager()
    {
        this.main = Main.GetInstance();
        Bukkit.getPluginManager().registerEvents(this,main);

        // set up the OnTick scheduler
        AtomicInteger tick = new AtomicInteger();

        Bukkit.getScheduler().runTaskTimer(main, () -> {
            if (tick.get() >= 20)
            {
                tick.set(0);
            }

            tick.set(tick.get()+1);

            OnTick(tick.get());
        }, 1, 0);
    }

    @EventHandler
    public void OnDeath(EntityDeathEvent e)
    {
        if (EntityDefinitions.HasDefinitionForEntity(e.getEntity()))
        {
            List<ItemStack> drops = EntityDefinitions.GetRPGEntityByEntity(e.getEntity()).GetDrops(e.getEntity(), e.getDrops().stream().toList(), e.getDamageSource());

            e.getDrops().clear();
            e.getDrops().addAll(drops);
        }
    }

    @EventHandler
    public void OnPlayerKillEvent(EntityDeathEvent e)
    {
        // if the entity was killed by a player
        if (e.getEntity().getKiller() == null)
        {
            return;
        }

        Entity entity = e.getEntity();

        Player player = e.getEntity().getKiller();

        int droppedExp = 0;

        // check if the entity has a custom xp key
        if (entity.getPersistentDataContainer().has(NamespaceDefinitions.GetCustomXpDropNumberKey()))
        {
            droppedExp = entity.getPersistentDataContainer().get(NamespaceDefinitions.GetCustomXpDropNumberKey(), PersistentDataType.INTEGER);
        }
        else
        {
            // if there is a definition for the entity
            if (EntityDefinitions.HasDefinitionForEntity(e.getEntity()))
            {
                if (EntityDefinitions.GetRPGEntityByEntity(e.getEntity()).GetXpDropped() != -1)
                {
                    droppedExp = EntityDefinitions.GetRPGEntityByEntity(e.getEntity()).GetXpDropped();
                }
                else
                {
                    droppedExp = e.getDroppedExp();
                }
            }
            else
            {
                droppedExp = e.getDroppedExp();
            }
        }

        if (entity.getPersistentDataContainer().has(NamespaceDefinitions.GetLegendaryMobKey()))
        {
            droppedExp *= new BaseLegendaryComponent().GetXpMultiplier();
        }

        Main.GetInstance().statSheetManager.FindStatSheetByPlayer(player).GiveXP(droppedExp);
    }

    @EventHandler
    public void OnEntitySpawn(EntitySpawnEvent e)
    {
        boolean shouldShowLevel = false;

        float legendaryChance = 0;
        int level = 0;

        Entity entity = e.getEntity();

        // if there is a definition for the entity
        if (EntityDefinitions.HasDefinitionForEntity(e.getEntity()))
        {
            RPGEntity rpgEntity = EntityDefinitions.GetRPGEntityByEntity(e.getEntity());

            level = rpgEntity.GetLevel();
            shouldShowLevel = rpgEntity.ShouldShowLevel();

            legendaryChance = rpgEntity.GetLegendaryComponent().GetLegendaryChance();

            // set the initial state key
            if (rpgEntity.GetInitialState() != null)
            {
                e.getEntity().getPersistentDataContainer().set(NamespaceDefinitions.GetCurrentStateKey(), PersistentDataType.STRING, rpgEntity.GetInitialState().GetStateID());
            }
        }
        else
        {
            // if the entity is not misc (boats, item frames, paintings, etc.)
            if (!entity.getSpawnCategory().equals(SpawnCategory.MISC))
            {
                shouldShowLevel = true;
                level = (int) ((LivingEntity)entity).getAttribute(Attribute.MAX_HEALTH).getValue()  / 4;

                legendaryChance = new BaseLegendaryComponent().GetLegendaryChance();
            }
        }

        // if the entity doesn't have its legendary key set
        if (!entity.getPersistentDataContainer().has(NamespaceDefinitions.GetLegendaryMobKey()))
        {
            // if the random number is bigger than the legendaryChance
            if (Main.GetInstance().GetRandom().nextFloat() < legendaryChance)
            {
                MakeEntityLegendary((LivingEntity) e.getEntity());
            }
            else
            {
                entity.getPersistentDataContainer().set(NamespaceDefinitions.GetLegendaryMobKey(), PersistentDataType.BOOLEAN, false);
            }
        }

        SetEntityLevel(e.getEntity(), level, shouldShowLevel);
    }

    public static void MakeEntityLegendary(@NotNull LivingEntity entity)
    {
        entity.getPersistentDataContainer().set(NamespaceDefinitions.GetLegendaryMobKey(), PersistentDataType.BOOLEAN, true);

        if (EntityDefinitions.HasDefinitionForEntity(entity))
        {
            EntityDefinitions.GetRPGEntityByEntity(entity).GetLegendaryComponent().ApplyLegendaryAttributeMods(entity);
        }
        else
        {
            (new BaseLegendaryComponent()).ApplyLegendaryAttributeMods(entity);
        }
    }

    public static void SetEntityLevel(@NotNull Entity entity, int level, boolean shouldShowLevel)
    {
        RPGEntity rpgEntity = null;
        if (EntityDefinitions.HasDefinitionForEntity(entity)) rpgEntity = EntityDefinitions.GetRPGEntityByEntity(entity);

        // update the level if legendary
        if (entity.getPersistentDataContainer().has(NamespaceDefinitions.GetLegendaryMobKey()) && entity.getPersistentDataContainer().get(NamespaceDefinitions.GetLegendaryMobKey(), PersistentDataType.BOOLEAN) == true)
        {
            if (rpgEntity != null)
            {
                level *= rpgEntity.GetLegendaryComponent().GetLevelMultiplier();
            }
            else
            {
                level *= 4;
            }
        }

        entity.getPersistentDataContainer().set(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER, level);

        // set the name
        if (shouldShowLevel)
        {
            SetName(entity, rpgEntity, false);
        }

    }

    public static void SetEntityLevel(@NotNull Entity entity)
    {
        int level = 0;
        boolean shouldShowLevel = false;

        // if there is a definition for the entity
        if (EntityDefinitions.HasDefinitionForEntity(entity))
        {
            level = EntityDefinitions.GetRPGEntityByEntity(entity).GetLevel();
            shouldShowLevel = EntityDefinitions.GetRPGEntityByEntity(entity).ShouldShowLevel();
        }
        else
        {
            // if the entity is not misc (boats, item frames, paintings, etc.)
            if (!entity.getSpawnCategory().equals(SpawnCategory.MISC))
            {
                shouldShowLevel = true;
                level = (int) ((LivingEntity)entity).getAttribute(Attribute.MAX_HEALTH).getValue()  / 4;
            }
        }

        SetEntityLevel(entity, level, shouldShowLevel);
    }

    public static void SetName(@NotNull Entity entity, RPGEntity rpgEntity, boolean resetName)
    {
        if (resetName) entity.setCustomName("");

        // if there is a definition for the entity
        if (rpgEntity != null)
        {
            entity.setCustomName(rpgEntity.GetNameWithLevel(entity));
        }
        else
        {
            // set up the custom name
            String customName = "";

            // set the name to use as the entity name
            customName += entity.getName();

            // get the level
            int level = 0;

            if (entity.getPersistentDataContainer().has(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER)) level = entity.getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER);

            // set the level
            customName += " [lvl:" + level + "]";

            // if legendary
            if (entity.getPersistentDataContainer().get(NamespaceDefinitions.GetLegendaryMobKey(), PersistentDataType.BOOLEAN) == true)
            {
                // make it look legendary
                customName = (new BaseLegendaryComponent().GetLegendaryName(customName));
            }

            // set the name
            entity.setCustomName(customName);
        }
    }

    private void OnTick(int tick)
    {
        // get all worlds
        for (World world : Bukkit.getWorlds())
        {
            // get all entities
            for (Entity entity : world.getEntities())
            {
                LegendaryComponent legendaryComponent;

                // if there is a definition for the entity
                if (EntityDefinitions.HasDefinitionForEntity(entity))
                {
                    legendaryComponent = EntityDefinitions.GetRPGEntityByEntity(entity).GetLegendaryComponent();

                    if (entity.getPersistentDataContainer().has(NamespaceDefinitions.GetCurrentStateKey()))
                    {
                        EntityState newState = StateDefinitions.GetStateByID(entity.getPersistentDataContainer().get(NamespaceDefinitions.GetCurrentStateKey(), PersistentDataType.STRING)).OnTick(entity);

                        RPGEntity.SetStateOfEntity(entity, newState);
                    }
                }
                else
                {
                    legendaryComponent = new BaseLegendaryComponent();
                }

                // if this is a legendary mob that need to be given visuals
                if (entity.getPersistentDataContainer().has(NamespaceDefinitions.GetLegendaryMobKey())
                        && entity.getPersistentDataContainer().get(NamespaceDefinitions.GetLegendaryMobKey(), PersistentDataType.BOOLEAN))
                {
                    legendaryComponent.ShowTickOfLegendary(tick, entity);
                }
            }
        }
    }

//    @EventHandler
//    public void OnEntityEvent(EntityEvent e)
//    {
//        // if there is a definition for the entity
//        if (EntityDefinitions.HasDefinitionForEntity(e.getEntity()))
//        {
//            // if it has a CurrentStateKey
//            if (e.getEntity().getPersistentDataContainer().has(NamespaceDefinitions.GetCurrentStateKey()))
//            {
//                EntityState newState = StateDefinitions.GetStateByID(e.getEntity().getPersistentDataContainer().get(NamespaceDefinitions.GetCurrentStateKey(), PersistentDataType.STRING)).OnAct(e, e.getEntity());
//
//                RPGEntity.SetStateOfEntity(e.getEntity(), newState);
//            }
//        }
//    }

    @EventHandler
    public void OnEntityInteract(PlayerInteractEntityEvent e)
    {
        // if there is a definition for the entity
        if (EntityDefinitions.HasDefinitionForEntity(e.getRightClicked()))
        {
            // if it has a CurrentStateKey
            if (e.getRightClicked().getPersistentDataContainer().has(NamespaceDefinitions.GetCurrentStateKey()))
            {
                EntityState newState = StateDefinitions.GetStateByID(e.getRightClicked().getPersistentDataContainer().get(NamespaceDefinitions.GetCurrentStateKey(), PersistentDataType.STRING)).OnInteracted(e, e.getRightClicked());

                RPGEntity.SetStateOfEntity(e.getRightClicked(), newState);
            }
        }
    }
}
