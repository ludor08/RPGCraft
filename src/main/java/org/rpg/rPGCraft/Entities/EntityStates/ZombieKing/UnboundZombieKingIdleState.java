package org.rpg.rPGCraft.Entities.EntityStates.ZombieKing;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.ArrayList;
import java.util.List;

public class UnboundZombieKingIdleState extends EntityState
{
    NamespacedKey shieldCatalystLiving = new NamespacedKey(Main.GetInstance(), "shield_catalyst_living");
    NamespacedKey attackCooldown = new NamespacedKey(Main.GetInstance(), "attack_cooldown");
    NamespacedKey shieldCatalystKey = new NamespacedKey(Main.GetInstance(), "zombie_king_shield_catalyst");
    NamespacedKey zombieKingCommander = new NamespacedKey(Main.GetInstance(), "zombie_king_commander");
    NamespacedKey damageMilestone = new NamespacedKey(Main.GetInstance(), "zombie_king_damage_milestone");

    //EntityState[] attacks = {EntityStateEnum.ZOMBIE_KING_SHOOT_AT_PLAYER.GetEntityState(), EntityStateEnum.ZOMBIE_KING_ENHANCE_ZOMBIES.GetEntityState()};

    int timeBetweenAttacks = 60;

    public UnboundZombieKingIdleState()
    {
        super("unbound_zombie_king_idle_state");
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        ScanForShieldCatalysts(thisEntity);

        List<EntityState> attacks = List.of(EntityStates.UNBOUND_ZOMBIE_KING_SUMMON_ZOMBIES.GetEntityState(), EntityStates.UNBOUND_ZOMBIE_KING_TOXIN_RAIN.GetEntityState());

        if (thisEntity.getPersistentDataContainer().has(attackCooldown) && thisEntity.getPersistentDataContainer().get(attackCooldown, PersistentDataType.INTEGER) <= 0)
        {
            RPGutils.SetNamespacedKeyValue(thisEntity, attackCooldown, timeBetweenAttacks);
            return attacks.get(Main.GetInstance().GetRandom().nextInt(attacks.size()));
        }
        else
        {
            RPGutils.AddToNamespacedKey(thisEntity, attackCooldown, timeBetweenAttacks, -(EntityManager.GetEntityTicksPerSecond() /20));

            if (thisEntity instanceof Mob mob)
            {
                Location loc = thisEntity.getLocation();

                List<Entity> scannedEntities = RPGutils.SortEntityListByDistance(thisEntity.getNearbyEntities(10,10,10), thisEntity.getLocation());

                for (Entity entity : scannedEntities)
                {
                    if (entity instanceof Player)
                    {
                        Vector3d runAwayOffset = RPGutils.getDirection(thisEntity.getLocation(), entity.getLocation()).normalize().mul(2);

                        loc.add(runAwayOffset.x, runAwayOffset.y, runAwayOffset.z);
                    }
                }

                mob.getPathfinder().moveTo(loc);
            }
        }

        return this;
    }

    @Override
    public EntityState OnTakeDamage(EntityDamageEvent e, Entity thisEntity)
    {
        ScanForShieldCatalysts(thisEntity);

        if (thisEntity instanceof LivingEntity living)
        {
            if (living.getPersistentDataContainer().has(shieldCatalystLiving) && living.getPersistentDataContainer().get(shieldCatalystLiving, PersistentDataType.INTEGER) > 0)
            {
                e.setCancelled(true);
            }
            else
            {
                float currentDamageMilestone = living.getPersistentDataContainer().get(damageMilestone, PersistentDataType.FLOAT);

                if ((living.getHealth() / living.getAttribute(Attribute.MAX_HEALTH).getValue()) <= currentDamageMilestone)
                {
                    double health = living.getAttribute(Attribute.MAX_HEALTH).getValue() * currentDamageMilestone;

                    living.setHealth(health);
                    RPGutils.AddToNamespacedKey(living, damageMilestone, 1f, -0.25f);
                    e.setCancelled(true);

                    SelectNewShieldCatalysts(thisEntity);
                }
            }
        }

        return this;
    }

    @Override
    public EntityState OnDeath(EntityDeathEvent e, Entity thisEntity)
    {
        for (Entity entity : GetMinions(thisEntity))
        {
            if (entity instanceof LivingEntity livingEntity) livingEntity.setHealth(0);
        }

        return this;
    }

    public List<Entity> SelectNewShieldCatalysts(Entity thisEntity)
    {
        // spawn the zombie peasants
        int numberOfShieldCatalyst = 3;

        List<Entity> minions = GetMinions(thisEntity);
        List<Entity> newCatalysts = new ArrayList<>();

        // make a number of them shield catalysts
        for (int i = 0; i < numberOfShieldCatalyst; i++)
        {
            int index = Main.GetInstance().GetRandom().nextInt(minions.size());

            Entity minion = minions.get(index);
            minion.getPersistentDataContainer().set(shieldCatalystKey, PersistentDataType.STRING, thisEntity.getUniqueId().toString());
            minion.setGlowing(true);

            newCatalysts.add(minion);
            minions.remove(index);
        }

        return newCatalysts;
    }

    public List<Entity> GetMinions(Entity thisEntity)
    {
        List<Entity> scannedEntities = RPGutils.SortEntityListByDistance(thisEntity.getNearbyEntities(50,50,50), thisEntity.getLocation());
        List<Entity> minions = new ArrayList<>();

        for (Entity entity : scannedEntities)
        {
            if (entity.getPersistentDataContainer().has(zombieKingCommander) && thisEntity.getUniqueId().toString().equals(entity.getPersistentDataContainer().get(zombieKingCommander, PersistentDataType.STRING)))
            {
                minions.add(entity);
            }
        }

        return minions;
    }

    public void ScanForShieldCatalysts(Entity thisEntity)
    {
        List<Entity> scannedMinions = GetMinions(thisEntity);
        int numberOfShieldCatalystLiving = 0;

        for (Entity entity : scannedMinions)
        {
            if (entity.getPersistentDataContainer().has(shieldCatalystKey))
            {
                numberOfShieldCatalystLiving++;
            }
        }

        thisEntity.getPersistentDataContainer().set(shieldCatalystLiving, PersistentDataType.INTEGER, numberOfShieldCatalystLiving);
    }
}
