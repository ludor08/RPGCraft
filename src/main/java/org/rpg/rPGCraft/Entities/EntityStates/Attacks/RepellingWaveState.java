package org.rpg.rPGCraft.Entities.EntityStates.Attacks;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class RepellingWaveState extends EntityState
{
    int damage = 10;

    float maxRadius = 10;
    int stepsToMaxRadius = 60;

    NamespacedKey waveStep = new NamespacedKey(Main.GetInstance(), "repelling_wave_step");

    public RepellingWaveState()
    {
        super("repelling_wave_state");
    }

    @Override
    public EntityState OnApply(Entity thisEntity)
    {
        RPGutils.SetNamespacedKeyValue(thisEntity, waveStep, 0f);

        return this;
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        float step = thisEntity.getPersistentDataContainer().get(waveStep, PersistentDataType.FLOAT);

        if (step <= stepsToMaxRadius)
        {
            int segments = (int) Math.floor((maxRadius * ((float) step /stepsToMaxRadius)) * Math.PI * 2);

            for (Location point : RPGutils.GetPointsOfACircle(thisEntity.getLocation(), maxRadius * ((float) step /stepsToMaxRadius), segments, new Vector3d(0, 1, 0)))
            {
                // scan for players
                List<Entity> targets = point.getWorld().getNearbyEntities(point, 0.5, 0.5, 0.5).stream().toList();
                for (Entity entity : targets)
                {
                    if (entity instanceof Player targetPlayer
                        && targetPlayer.isOnGround())
                    {
                        targetPlayer.damage(damage, thisEntity);
                    }
                }

                RPGparticles.SpawnParticle(1, point, new Vector3d(), Particle.SOUL, 0);
            }

            RPGutils.AddToNamespacedKey(thisEntity, waveStep, 0f, EntityManager.GetEntityTicksPerSecond()/20f);
        }
        else
        {
            thisEntity.remove();
        }

        return this;
    }

}
