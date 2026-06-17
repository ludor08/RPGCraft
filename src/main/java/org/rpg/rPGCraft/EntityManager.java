package org.rpg.rPGCraft;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Definitions.*;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.Entities.LegendaryComponent;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGEntity;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityManager implements Listener
{
    static final int ENTITY_TICKS_PER_SECOND = 20; // max of 20 and this formula ((1/ ENTITY_TICKS_PER_SECOND) * 20) must give a real number
    public static int GetEntityTicksPerSecond() { return ENTITY_TICKS_PER_SECOND; }

    private Main main;

    public EntityManager()
    {
        this.main = Main.GetInstance();
        Bukkit.getPluginManager().registerEvents(this,main);

        // set up the OnTick scheduler
        AtomicInteger tick = new AtomicInteger();

        Bukkit.getScheduler().runTaskTimer(main, () -> {
            if (tick.get() >= ENTITY_TICKS_PER_SECOND)
            {
                tick.set(0);
            }

            tick.set(tick.get()+1);

            OnTick(tick.get());
        }, (long) Math.floor((1/ (float) ENTITY_TICKS_PER_SECOND) * 20), 0);
    }

    public static void MakeEntityLegendary(@NotNull LivingEntity entity)
    {
        entity.getPersistentDataContainer().set(MyNamespaces.LEGENDARY_MOB.GetNamespacedKey(), PersistentDataType.BOOLEAN, true);

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
        if (entity.getPersistentDataContainer().has(MyNamespaces.LEGENDARY_MOB.GetNamespacedKey()) && entity.getPersistentDataContainer().get(MyNamespaces.LEGENDARY_MOB.GetNamespacedKey(), PersistentDataType.BOOLEAN) == true)
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

        entity.getPersistentDataContainer().set(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER, level);

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

            if (entity.getPersistentDataContainer().has(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER)) level = entity.getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER);

            // set the level
            customName += " [lvl:" + level + "]";

            // if legendary
            if (entity.getPersistentDataContainer().get(MyNamespaces.LEGENDARY_MOB.GetNamespacedKey(), PersistentDataType.BOOLEAN) == true)
            {
                // make it look legendary
                customName = (new BaseLegendaryComponent().GetLegendaryName(customName));
            }

            // set the name
            entity.setCustomName(customName);
        }
    }

    public static ItemDisplay GetDisplayEntity(Entity entity)
    {
        if (entity.getPersistentDataContainer().has(MyNamespaces.DISPLAY_ENTITY.GetNamespacedKey()))
        {
            return (ItemDisplay) Bukkit.getEntity(UUID.fromString(entity.getPersistentDataContainer().get(MyNamespaces.DISPLAY_ENTITY.GetNamespacedKey(), PersistentDataType.STRING)));
        }
        else
        {
            return null;
        }
    }

    public static void AssignAnimation(Entity entity, Animation animation)
    {
        RPGutils.SetNamespacedKeyValue(entity, MyNamespaces.ANIMATION_FRAME.GetNamespacedKey(), 0);
        entity.getPersistentDataContainer().set(MyNamespaces.ANIMATION.GetNamespacedKey(), PersistentDataType.STRING, animation.GetNameID());

        if (GetDisplayEntity(entity) == null)
        {
            ItemDisplay itemDisplay = entity.getWorld().spawn(entity.getLocation(), ItemDisplay.class);

            Location location = entity.getLocation();
            location.setPitch(0);

            itemDisplay.teleport(location);

            entity.getPersistentDataContainer().set(MyNamespaces.DISPLAY_ENTITY.GetNamespacedKey(), PersistentDataType.STRING, itemDisplay.getUniqueId().toString());
        }
    }

    public static void AssignDefaultAnimation(Entity entity)
    {
        if (entity.getPersistentDataContainer().has(MyNamespaces.DEFAULT_ANIMATION.GetNamespacedKey()))
        {
            AssignAnimation(entity, AnimationDefinitions.GetAnimationByID(entity.getPersistentDataContainer().get(MyNamespaces.DEFAULT_ANIMATION.GetNamespacedKey(), PersistentDataType.STRING)));
        }
        else
        {
            RPGutils.SetNamespacedKeyValue(entity, MyNamespaces.ANIMATION_FRAME.GetNamespacedKey(), 0);
            entity.getPersistentDataContainer().remove(MyNamespaces.ANIMATION.GetNamespacedKey());

            if (GetDisplayEntity(entity) != null)
            {
                GetDisplayEntity(entity).remove();
            }
        }
    }

    public static void StopAnimation(Entity entity)
    {
        // if there is a definition for the entity
        if (EntityDefinitions.HasDefinitionForEntity(entity))
        {
            // if it has a CurrentStateKey
            if (entity.getPersistentDataContainer().has(MyNamespaces.CURRENT_STATE.GetNamespacedKey()))
            {
                Animation animation = AnimationDefinitions.GetAnimationByID(entity.getPersistentDataContainer().get(MyNamespaces.ANIMATION.GetNamespacedKey(), PersistentDataType.STRING));

                RPGutils.SetNamespacedKeyValue(entity, MyNamespaces.ANIMATION_FRAME.GetNamespacedKey(), 0);
                entity.getPersistentDataContainer().remove(MyNamespaces.ANIMATION.GetNamespacedKey());

                EntityState newState = EntityStates.GetEntityStateByString(entity.getPersistentDataContainer().get(MyNamespaces.CURRENT_STATE.GetNamespacedKey(), PersistentDataType.STRING)).OnAnimationEnd(animation, entity);

                RPGEntity.SetStateOfEntity(entity, newState);
            }
        }
        else
        {
            RPGutils.SetNamespacedKeyValue(entity, MyNamespaces.ANIMATION_FRAME.GetNamespacedKey(), 0);
            entity.getPersistentDataContainer().remove(MyNamespaces.ANIMATION.GetNamespacedKey());

        }

        if (GetDisplayEntity(entity) != null)
        {
            GetDisplayEntity(entity).remove();
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
                // if the entity has a OnTick(), run it
                if (entity.getPersistentDataContainer().has(MyNamespaces.CURRENT_STATE.GetNamespacedKey()))
                {
                    EntityState newState = EntityStates.GetEntityStateByString(entity.getPersistentDataContainer().get(MyNamespaces.CURRENT_STATE.GetNamespacedKey(), PersistentDataType.STRING)).OnTick(entity, tick);

                    RPGEntity.SetStateOfEntity(entity, newState);
                }


                AnimateEntity(entity);
                /// TODO check in the Config for if legendary particle should be shown
                ShowLegendaryComponent(tick, entity);
            }
        }
    }

    private void AnimateEntity(Entity entity)
    {
        // if the entity has a display entity
        if (GetDisplayEntity(entity) != null)
        {
            ItemDisplay displayEntity = GetDisplayEntity(entity);

            float yaw = 0;
            if (entity instanceof LivingEntity living) yaw = living.getBodyYaw();

            Location location = entity.getLocation();
            location.setYaw(yaw);
            location.setPitch(0);

            displayEntity.teleport(location);

            // if the entity has an animation key
            if (entity.getPersistentDataContainer().has(MyNamespaces.ANIMATION.GetNamespacedKey()))
            {
                // get the animation
                Animation animation = AnimationDefinitions.GetAnimationByID(entity.getPersistentDataContainer().get(MyNamespaces.ANIMATION.GetNamespacedKey(), PersistentDataType.STRING));

                // update the frame
                RPGutils.AddToNamespacedKey(entity, MyNamespaces.ANIMATION_FRAME.GetNamespacedKey(), 0, 1);

                // if the current frame is greater than the number of frames
                if (entity.getPersistentDataContainer().get(MyNamespaces.ANIMATION_FRAME.GetNamespacedKey(), PersistentDataType.INTEGER) > animation.GetNumberOfFrames())
                {
                    // if the animation is looping
                    if (animation.IsLooping())
                    {
                        RPGutils.SetNamespacedKeyValue(entity, MyNamespaces.ANIMATION_FRAME.GetNamespacedKey(), 1);
                    }
                    else
                    {
                        AssignDefaultAnimation(entity);

                        EntityState newState = EntityStates.GetEntityStateByString(entity.getPersistentDataContainer().get(MyNamespaces.CURRENT_STATE.GetNamespacedKey(), PersistentDataType.STRING)).OnAnimationEnd(animation, entity);

                        RPGEntity.SetStateOfEntity(entity, newState);
                        return;
                    }

                }

                int frame = entity.getPersistentDataContainer().get(MyNamespaces.ANIMATION_FRAME.GetNamespacedKey(), PersistentDataType.INTEGER);

                // if the shown frame is not the same as the old frame
                if (!displayEntity.getItemStack().isSimilar(animation.GetFrame(frame).GetItemStackForFrame()))
                {
                    // update the shown frame
                    displayEntity.setItemStack(animation.GetFrame(frame).GetItemStackForFrame());
                }

                // if the transformation of the display isn't the same as the frame transformation
                if (!displayEntity.getTransformation().equals(animation.GetFrame(frame).GetTransformation()))
                {
                    // update the Transformation
                    animation.GetFrame(frame).SetTransformationForDisplay(displayEntity);
                }
            }
        }
    }

    private static void ShowLegendaryComponent(int tick, Entity entity)
    {
        // show the legendaryComponent
        LegendaryComponent legendaryComponent;

        // if there is a definition for the entity
        if (EntityDefinitions.HasDefinitionForEntity(entity))
        {
            legendaryComponent = EntityDefinitions.GetRPGEntityByEntity(entity).GetLegendaryComponent();
        }
        else
        {
            legendaryComponent = new BaseLegendaryComponent();
        }

        // if this is a legendary mob that need to be given visuals
        if (entity.getPersistentDataContainer().has(MyNamespaces.LEGENDARY_MOB.GetNamespacedKey())
                && entity.getPersistentDataContainer().get(MyNamespaces.LEGENDARY_MOB.GetNamespacedKey(), PersistentDataType.BOOLEAN))
        {
            legendaryComponent.ShowTickOfLegendary(tick, entity);
        }
    }

    @EventHandler
    public void OnEntityInteract(PlayerInteractEntityEvent e)
    {
        // if it has a CurrentStateKey
        if (e.getRightClicked().getPersistentDataContainer().has(MyNamespaces.CURRENT_STATE.GetNamespacedKey()))
        {
            EntityState newState = EntityStates.GetEntityStateByString(e.getRightClicked().getPersistentDataContainer().get(MyNamespaces.CURRENT_STATE.GetNamespacedKey(), PersistentDataType.STRING)).OnInteractedWith(e, e.getRightClicked());

            RPGEntity.SetStateOfEntity(e.getRightClicked(), newState);
        }
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

        if (e.getEntity().getPersistentDataContainer().has(MyNamespaces.CURRENT_STATE.GetNamespacedKey()))
        {
            EntityState newState = EntityStates.GetEntityStateByString(e.getEntity().getPersistentDataContainer().get(MyNamespaces.CURRENT_STATE.GetNamespacedKey(), PersistentDataType.STRING)).OnDeath(e, e.getEntity());

            RPGEntity.SetStateOfEntity(e.getEntity(), newState);
        }

        // if the entity has a display entity, remove the display entity
        if (GetDisplayEntity(e.getEntity()) != null)
        {
            StopAnimation(e.getEntity());
        }
    }

    @EventHandler
    public void OnMove(EntityMoveEvent e)
    {
        if (e.getEntity().getPersistentDataContainer().has(MyNamespaces.CURRENT_STATE.GetNamespacedKey()))
        {
            EntityState newState = EntityStates.GetEntityStateByString(e.getEntity().getPersistentDataContainer().get(MyNamespaces.CURRENT_STATE.GetNamespacedKey(), PersistentDataType.STRING)).OnMove(e, e.getEntity());

            RPGEntity.SetStateOfEntity(e.getEntity(), newState);
        }

        // if the entity has a display entity, move the display entity
        if (GetDisplayEntity(e.getEntity()) != null)
        {
            ItemDisplay displayEntity = GetDisplayEntity(e.getEntity());

            float yaw = 0;
            if (e.getEntity() instanceof LivingEntity living) yaw = living.getBodyYaw();

            Location location = e.getEntity().getLocation();
            location.setYaw(yaw);
            location.setPitch(0);

            displayEntity.teleport(location);
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

        int droppedExp;

        // check if the entity has a custom xp key
        if (entity.getPersistentDataContainer().has(MyNamespaces.CUSTOM_XP_DROP_NUMBER.GetNamespacedKey()))
        {
            droppedExp = entity.getPersistentDataContainer().get(MyNamespaces.CUSTOM_XP_DROP_NUMBER.GetNamespacedKey(), PersistentDataType.INTEGER);
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

        if (entity.getPersistentDataContainer().has(MyNamespaces.LEGENDARY_MOB.GetNamespacedKey()))
        {
            if (EntityDefinitions.HasDefinitionForEntity(e.getEntity()))
            {
                droppedExp *= EntityDefinitions.GetRPGEntityByEntity(e.getEntity()).GetLegendaryComponent().GetXpMultiplier();
            }
            else
            {
                droppedExp *= new BaseLegendaryComponent().GetXpMultiplier();
            }
        }

        Main.GetInstance().statSheetManager.FindStatSheetByPlayer(player).GiveXP(droppedExp, true);
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
                e.getEntity().getPersistentDataContainer().set(MyNamespaces.CURRENT_STATE.GetNamespacedKey(), PersistentDataType.STRING, rpgEntity.GetInitialState().GetStateID());
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
        if (!entity.getPersistentDataContainer().has(MyNamespaces.LEGENDARY_MOB.GetNamespacedKey()))
        {
            // if the random number is bigger than the legendaryChance
            if (Main.GetInstance().GetRandom().nextFloat() < legendaryChance)
            {
                MakeEntityLegendary((LivingEntity) e.getEntity());
            }
            else
            {
                entity.getPersistentDataContainer().set(MyNamespaces.LEGENDARY_MOB.GetNamespacedKey(), PersistentDataType.BOOLEAN, false);
            }
        }

        SetEntityLevel(e.getEntity(), level, shouldShowLevel);
    }

    @EventHandler
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (e.getEntity().getPersistentDataContainer().has(MyNamespaces.CURRENT_STATE.GetNamespacedKey()))
        {
            EntityState newState = EntityStates.GetEntityStateByString(e.getEntity().getPersistentDataContainer().get(MyNamespaces.CURRENT_STATE.GetNamespacedKey(), PersistentDataType.STRING)).OnTakeDamage(e, e.getEntity());

            RPGEntity.SetStateOfEntity(e.getEntity(), newState);
        }
    }
}
