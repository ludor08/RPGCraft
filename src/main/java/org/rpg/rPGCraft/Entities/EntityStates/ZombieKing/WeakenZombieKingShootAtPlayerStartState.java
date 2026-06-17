package org.rpg.rPGCraft.Entities.EntityStates.ZombieKing;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.Main;

import java.util.Objects;
import java.util.UUID;

public class WeakenZombieKingShootAtPlayerStartState extends EntityState
{
    NamespacedKey hasShield = new NamespacedKey(Main.GetInstance(), "zombie_king_has_shield");

    WeakenZombieKingIdleState defaultState = (WeakenZombieKingIdleState) EntityStates.WEAKEN_ZOMBIE_KING_IDLE.GetEntityState();

    public WeakenZombieKingShootAtPlayerStartState()
    {
        super("weaken_zombie_king_shoot_at_player_start_state");
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        defaultState.ScanForShieldCatalysts(thisEntity);

        // get the marker
        Entity marker = Bukkit.getEntity(UUID.fromString(thisEntity.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER.GetNamespacedKey(), PersistentDataType.STRING)));

        // if the entity is at the middle of the platform
        if ((thisEntity.getX() >= marker.getX()+9.25 && thisEntity.getX() <= marker.getX()+9.75) && (thisEntity.getZ() >= marker.getZ()+9.25 && thisEntity.getZ() <= marker.getZ()+9.75))
        {
            return EntityStates.WEAKEN_ZOMBIE_KING_SHOOT_AT_PLAYER.GetEntityState();
        }
        else PathfindToMiddle(thisEntity, marker);

        return this;
    }

    private void PathfindToMiddle(Entity thisEntity, Entity marker)
    {
       if (!(thisEntity instanceof Mob mob))
       {
           Bukkit.getLogger().warning(thisEntity.getUniqueId() + " can not pathfind, because they are not a mob.");
           return;
       }

        mob.getPathfinder().moveTo(new Location(marker.getWorld(), marker.getX()+9.5, marker.getY()+2, marker.getZ()+9.5));
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
}
