package org.rpg.rPGCraft.Entities.EntityStates.DiscardedSentientArmament;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Animation.Animations.TestBlackSquare;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.RPGutils;

public class DiscardedSentientArmamentIdleState extends EntityState
{
    float TARGET_COOLDOWN = 1.0f;

    public DiscardedSentientArmamentIdleState()
    {
        super("discarded_sentient_armament_idle_state");
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        // update the target cooldown
        RPGutils.AddToNamespacedKey(thisEntity, MyNamespaces.TARGET_COOLDOWN.GetNamespacedKey(), TARGET_COOLDOWN, -0.05f * (EntityManager.GetEntityTicksPerSecond()/20));

        // if the target cooldown is > 0, return this state
        if (thisEntity.getPersistentDataContainer().get(MyNamespaces.TARGET_COOLDOWN.GetNamespacedKey(), PersistentDataType.FLOAT) > 0)
        {
            return this;
        }

        // reset the target cooldown
        RPGutils.SetNamespacedKeyValue(thisEntity, MyNamespaces.TARGET_COOLDOWN.GetNamespacedKey(), TARGET_COOLDOWN);

        // get the fellow range
        double fellowRange = 30;
        if (((Mob) thisEntity).getAttribute(Attribute.FOLLOW_RANGE) != null) fellowRange = ((Mob) thisEntity).getAttribute(Attribute.FOLLOW_RANGE).getValue();

        // for all of the players near the DSA
        for (Entity entity : RPGutils.SortEntityListByDistance(thisEntity.getNearbyEntities(fellowRange, fellowRange, fellowRange), thisEntity.getLocation()))
        {
            // if the entity is a player
            if (entity instanceof Player player)
            {
                // get the distance between the entity and the player
                double distance = RPGutils.getDistance(thisEntity.getLocation(), player.getLocation());

                // if the entity can see the player
                if (RPGraycast.RecastForEntities((int) Math.ceil(distance), RPGutils.getDirection(player.getEyeLocation(), ((Mob) thisEntity).getEyeLocation()), ((Mob) thisEntity).getEyeLocation(), true, thisEntity, null, 0, new Vector3d(0.5f, 0.5f, 0.5f)).contains(player))
                {
                    thisEntity.getPersistentDataContainer().set(MyNamespaces.TARGETING.GetNamespacedKey(), PersistentDataType.STRING, player.getUniqueId().toString());
                    EntityManager.AssignAnimation(thisEntity, new TestBlackSquare());

                    return EntityStates.DISCARDED_SENTIENT_ARMAMENT_CHASING.GetEntityState();
                }
            }
        }

        // return the same state
        return this;
    }

}
