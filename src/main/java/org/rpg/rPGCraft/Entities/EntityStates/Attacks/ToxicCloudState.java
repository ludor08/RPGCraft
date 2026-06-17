package org.rpg.rPGCraft.Entities.EntityStates.Attacks;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.Entities.RPGCustomEntities.ZombieKingVoidBombEntity;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.RPGutils;

public class ToxicCloudState extends EntityState
{
    NamespacedKey attacksLeft = new NamespacedKey(Main.GetInstance(), "toxic_cloud_attacks_left");
    NamespacedKey attackCooldown = new NamespacedKey(Main.GetInstance(), "toxic_cloud_attack_cooldown");

    int TOTAL_NUMBER_OF_ATTACKS = 3;
    float TIME_BETWEEN_ATTACKS = 10f;

    Vector3d CLOUD_SIZE = new Vector3d(4,2,4);

    RPGCustomEntity attackEntity = new ZombieKingVoidBombEntity();

    public ToxicCloudState()
    {
        super("toxic_cloud_state");
    }

    @Override
    public EntityState OnApply(Entity thisEntity)
    {
        RPGutils.SetNamespacedKeyValue(thisEntity, attacksLeft, TOTAL_NUMBER_OF_ATTACKS);
        RPGutils.SetNamespacedKeyValue(thisEntity, attackCooldown, TIME_BETWEEN_ATTACKS);

        return this;
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        // spawn the cloud particles
        RPGparticles.SpawnParticle(1000, thisEntity.getLocation(), new Vector3d(0.125 * CLOUD_SIZE.x, 0.125 * CLOUD_SIZE.y, 0.125 * CLOUD_SIZE.z), Particle.SOUL, 0);
        RPGparticles.SpawnParticle(1000, thisEntity.getLocation(), new Vector3d(0.125 * CLOUD_SIZE.x, 0.125 * CLOUD_SIZE.y, 0.125 * CLOUD_SIZE.z), Particle.END_ROD, 0);

        // if this attack is off cool down
        if (thisEntity.getPersistentDataContainer().get(attackCooldown, PersistentDataType.FLOAT) * EntityManager.GetEntityTicksPerSecond() <= 0)
        {
            // find a spawn location for the void bomb
            Location spawnLoc = thisEntity.getLocation();
            spawnLoc.add(Main.GetInstance().GetRandom().nextDouble(-CLOUD_SIZE.x/2, CLOUD_SIZE.x/2), 0, Main.GetInstance().GetRandom().nextDouble(-CLOUD_SIZE.z/2, CLOUD_SIZE.z/2));

            // spawn the void bomb
            attackEntity.SpawnCustomEntity(spawnLoc);

            // remove 1 from attacks left
            RPGutils.AddToNamespacedKey(thisEntity, attacksLeft, 0, -1);

            // remove this entity for there are no attacks left
            if (thisEntity.getPersistentDataContainer().get(attacksLeft, PersistentDataType.INTEGER) == 0)
            {
                thisEntity.remove();
            }

            // reset the cooldown
            RPGutils.SetNamespacedKeyValue(thisEntity, attackCooldown, TIME_BETWEEN_ATTACKS);
        }
        else
        {
            // remove some time from the cooldown
            RPGutils.AddToNamespacedKey(thisEntity, attackCooldown, TIME_BETWEEN_ATTACKS, (float) -(EntityManager.GetEntityTicksPerSecond()/20));
        }

        return this;
    }

}
