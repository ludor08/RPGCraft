package org.rpg.rPGCraft.Entities.EntityStates.DiscardedSentientArmament;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.RPGutils;

import java.util.UUID;

public class DiscardedSentientArmamentChasingState extends EntityState
{
    // amount of time that this entity can not see a player, and still fellow them
    float MEMORY_TIME = 1.0f;

    public DiscardedSentientArmamentChasingState()
    {
        super("discarded_sentient_armament_chasing_state");
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        // update last seen target
        RPGutils.AddToNamespacedKey(thisEntity, MyNamespaces.LAST_SEEN_TARGET.GetNamespacedKey(), MEMORY_TIME, -0.05f * (EntityManager.GetEntityTicksPerSecond()/20));

        // if thisEntity is not an instanceof mob
        if (!(thisEntity instanceof Mob mob))
        {
            Bukkit.getLogger().warning("thisEntity is not an instance of Mob.");
            return this;
        }

        // if the entity has a target, move them towards the target, if not, go to the idle state.
        if (thisEntity.getPersistentDataContainer().has(MyNamespaces.TARGETING.GetNamespacedKey()) &&
                Bukkit.getEntity(UUID.fromString(thisEntity.getPersistentDataContainer().get(MyNamespaces.TARGETING.GetNamespacedKey(), PersistentDataType.STRING))) instanceof LivingEntity target)
        {
            double distance = RPGutils.getDistance(thisEntity.getLocation(), target.getLocation());

            if (((Mob) thisEntity).getAttribute(Attribute.FOLLOW_RANGE) != null && distance <= ((Mob) thisEntity).getAttribute(Attribute.FOLLOW_RANGE).getValue())
            {
                // if this entity can see its target, walk towards the target, and return this state
                if (RPGraycast.RecastForEntities((int) Math.ceil(distance), RPGutils.getDirection(target.getEyeLocation(), ((Mob) thisEntity).getEyeLocation()), ((Mob) thisEntity).getEyeLocation(), true, thisEntity, null, 0, new Vector3d(0.5f, 0.5f, 0.5f)).contains(target))
                {
                    mob.getPathfinder().moveTo(target.getLocation());

                    RPGutils.SetNamespacedKeyValue(thisEntity, MyNamespaces.LAST_SEEN_TARGET.GetNamespacedKey(), MEMORY_TIME);
                    return this;
                }

                // if last seen target time is <= 0, go to the Idle State
                if (target.getPersistentDataContainer().get(MyNamespaces.LAST_SEEN_TARGET.GetNamespacedKey(), PersistentDataType.FLOAT) <= 0)
                {
                    EntityManager.AssignDefaultAnimation(thisEntity);
                    return EntityStates.DISCARDED_SENTIENT_ARMAMENT_IDLE.GetEntityState();
                }
                else
                {
                    return this;
                }

            }

            EntityManager.AssignDefaultAnimation(thisEntity);
            return EntityStates.DISCARDED_SENTIENT_ARMAMENT_IDLE.GetEntityState();
        }

        EntityManager.AssignDefaultAnimation(thisEntity);
        return EntityStates.DISCARDED_SENTIENT_ARMAMENT_IDLE.GetEntityState();
    }

}
