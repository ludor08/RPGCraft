package org.rpg.rPGCraft.Entities.EntityStates.ZombieKing;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Animation.Animations.TestBlackSquare;
import org.rpg.rPGCraft.Animation.Animations.UnTestBlackSquare;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.Entities.RPGCustomEntities.ZombieKingToxicCloudEntity;
import org.rpg.rPGCraft.Entities.RPGCustomEntities.ZombieKingVoidBombEntity;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;
import java.util.UUID;

public class UnboundZombieKingToxinRainState extends EntityState
{
    int MAX_NUMBER_OF_CLOUDS = 3;

    Animation shootAnimation = new TestBlackSquare();
    Animation stopShootingAnimation = new UnTestBlackSquare();

    public UnboundZombieKingToxinRainState()
    {
        super("unbound_zombie_king_toxin_rain_state");
    }

    public EntityState OnAnimationEnd(Animation animation, Entity thisEntity)
    {
        // get the marker
        Entity marker = Bukkit.getEntity(UUID.fromString(thisEntity.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER.GetNamespacedKey(), PersistentDataType.STRING)));

        if (animation.GetNameID().equals(shootAnimation.GetNameID()))
        {
            List<Entity> orderTargets = RPGutils.SortEntityListByDistance(thisEntity.getNearbyEntities(30,30,30), thisEntity.getLocation());
            int cloudsSpawned = 0;

            for (Entity entity : orderTargets)
            {
                if (entity instanceof Player targetPlayer)
                {
                    Location attackLoc = targetPlayer.getLocation();
                    attackLoc.setY(marker.getY()+12);

                    RPGCustomEntity RPGCloud = new ZombieKingToxicCloudEntity();
                    RPGCloud.SpawnCustomEntity(attackLoc);

                    // make sure that we're not spawning too many clouds
                    cloudsSpawned++;
                    if (cloudsSpawned >= MAX_NUMBER_OF_CLOUDS)
                    {
                        break;
                    }
                }
            }

            EntityManager.AssignAnimation(thisEntity, stopShootingAnimation);
        }
        else if (animation.GetNameID().equals(stopShootingAnimation.GetNameID()))
        {
            return EntityStates.UNBOUND_ZOMBIE_KING_IDLE.GetEntityState();
        }

        return this;
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        ((UnboundZombieKingIdleState)EntityStates.UNBOUND_ZOMBIE_KING_IDLE.GetEntityState()).ScanForShieldCatalysts(thisEntity);

        return this;
    }

    @Override
    public EntityState OnApply(Entity thisEntity)
    {
        EntityManager.AssignAnimation(thisEntity, shootAnimation);

        return this;
    }

    @Override
    public EntityState OnTakeDamage(EntityDamageEvent e, Entity thisEntity)
    {
        EntityStates.UNBOUND_ZOMBIE_KING_IDLE.GetEntityState().OnTakeDamage(e, thisEntity);

        return this;
    }

    @Override
    public EntityState OnDeath(EntityDeathEvent e, Entity thisEntity)
    {
        EntityStates.UNBOUND_ZOMBIE_KING_IDLE.GetEntityState().OnDeath(e, thisEntity);

        return this;
    }

    @Override
    public EntityState OnMove(EntityMoveEvent e, Entity thisEntity)
    {
        e.setCancelled(true);

        return this;
    }
}
