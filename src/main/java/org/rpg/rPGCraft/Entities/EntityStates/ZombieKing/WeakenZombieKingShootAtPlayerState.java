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
import org.rpg.rPGCraft.Entities.RPGCustomEntities.ZombieKingVoidBombEntity;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WeakenZombieKingShootAtPlayerState extends EntityState
{
    Animation shootAnimation = new TestBlackSquare();
    Animation stopShootingAnimation = new UnTestBlackSquare();

    WeakenZombieKingIdleState defaultState = (WeakenZombieKingIdleState) EntityStates.WEAKEN_ZOMBIE_KING_IDLE.GetEntityState();

    public WeakenZombieKingShootAtPlayerState()
    {
        super("weaken_zombie_king_shoot_at_player_state");
    }

    public EntityState OnAnimationEnd(Animation animation, Entity thisEntity)
    {
        // get the marker
        Entity marker = Bukkit.getEntity(UUID.fromString(thisEntity.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER.GetNamespacedKey(), PersistentDataType.STRING)));

        if (animation.GetNameID().equals(shootAnimation.GetNameID()))
        {
            List<Entity> orderTargets = RPGutils.SortEntityListByDistance(thisEntity.getNearbyEntities(30,30,30), thisEntity.getLocation());
            for (Entity entity : orderTargets)
            {
                if (entity instanceof Player targetPlayer)
                {
                    Location attackLoc = targetPlayer.getLocation();
                    attackLoc.setY(marker.getY()+8);

                    ZombieKingVoidBombEntity customVoidBomb = new ZombieKingVoidBombEntity();
                    Entity voidBombEntity = customVoidBomb.SpawnCustomEntity(attackLoc);
                }
            }

            EntityManager.AssignAnimation(thisEntity, stopShootingAnimation);
        }
        else if (animation.GetNameID().equals(stopShootingAnimation.GetNameID()))
        {
            return EntityStates.WEAKEN_ZOMBIE_KING_IDLE.GetEntityState();
        }

        return this;
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        defaultState.ScanForShieldCatalysts(thisEntity);

        return this;
    }

    @Override
    public EntityState OnApply(Entity thisEntity)
    {
        EntityManager.AssignAnimation(thisEntity, shootAnimation);

        return this;
    }

    @Override
    public EntityState OnDeath(EntityDeathEvent e, Entity thisEntity)
    {
        EntityState newState = defaultState.OnDeath(e, thisEntity);
        if (!Objects.equals(newState.GetStateID(), defaultState.GetStateID())) return newState;

        return this;
    }

    @Override
    public EntityState OnTakeDamage(EntityDamageEvent e, Entity thisEntity)
    {
        EntityState newState = defaultState.OnTakeDamage(e, thisEntity);
        if (!Objects.equals(newState.GetStateID(), defaultState.GetStateID())) return newState;

        return this;
    }

    @Override
    public EntityState OnMove(EntityMoveEvent e, Entity thisEntity)
    {
        e.setCancelled(true);

        return this;
    }
}
