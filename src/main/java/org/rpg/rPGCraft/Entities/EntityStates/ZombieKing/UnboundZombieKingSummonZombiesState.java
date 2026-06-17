package org.rpg.rPGCraft.Entities.EntityStates.ZombieKing;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Animation.Animations.TestBlackSquare;
import org.rpg.rPGCraft.Animation.Animations.UnTestBlackSquare;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.Entities.RPGCustomEntities.ZombieKingFootman;
import org.rpg.rPGCraft.Entities.RPGCustomEntities.ZombieKingRepellingWaveEntity;
import org.rpg.rPGCraft.Entities.RPGCustomEntities.ZombiePeasant;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.Objects;
import java.util.UUID;

public class UnboundZombieKingSummonZombiesState extends EntityState
{
    Animation animation = new TestBlackSquare();

    int minNumberToBeSummoned = 3;
    int maxNumberToBeSummoned = 5;

    public UnboundZombieKingSummonZombiesState()
    {
        super("unbound_zombie_king_summon_zombies_state");
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        System.out.println("summon 1");

        UnboundZombieKingIdleState defaultState = (UnboundZombieKingIdleState) EntityStates.UNBOUND_ZOMBIE_KING_IDLE.GetEntityState();
        defaultState.ScanForShieldCatalysts(thisEntity);

        return this;
    }

    public EntityState OnAnimationEnd(Animation animation, Entity thisEntity)
    {
        UnboundZombieKingIdleState defaultState = (UnboundZombieKingIdleState) EntityStates.UNBOUND_ZOMBIE_KING_IDLE.GetEntityState();

        if (animation.GetNameID().equals(this.animation.GetNameID()))
        {
            // get the marker
            Entity marker = Bukkit.getEntity(UUID.fromString(thisEntity.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER.GetNamespacedKey(), PersistentDataType.STRING)));

            Vector3d floorOffset = new Vector3d(
                    marker.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER_FLOOR_START_OFFSET_X.GetNamespacedKey(), PersistentDataType.FLOAT),
                    marker.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER_FLOOR_START_OFFSET_Y.GetNamespacedKey(), PersistentDataType.FLOAT),
                    marker.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER_FLOOR_START_OFFSET_Z.GetNamespacedKey(), PersistentDataType.FLOAT)
            );

            Vector2d floorSize = new Vector2d(
                    marker.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER_FLOOR_WIDTH.GetNamespacedKey(), PersistentDataType.INTEGER),
                    marker.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER_FLOOR_LENGTH.GetNamespacedKey(), PersistentDataType.INTEGER)
            );

            // spawn the footman
            int amountToBeSpawn = Main.GetInstance().GetRandom().nextInt(minNumberToBeSummoned, maxNumberToBeSummoned);
            ZombieKingFootman zombieKingFootman = new ZombieKingFootman();

            float waveSize = 10;

            for (int i = 0; i < amountToBeSpawn; i++)
            {
                Location loc = new Location(
                        marker.getWorld(),
                        marker.getX() + floorOffset.x + Main.GetInstance().GetRandom().nextInt(0, (int) floorSize.x),
                        marker.getY() + floorOffset.y,
                        marker.getZ() + floorOffset.z + Main.GetInstance().GetRandom().nextInt(0, (int) floorSize.y)
                );

                if (RPGutils.getDistance(thisEntity.getLocation(), loc) > waveSize)
                {
                    i--;
                    continue;
                }

                Entity footman = zombieKingFootman.SpawnCustomEntity(loc);
                footman.getPersistentDataContainer().set(new NamespacedKey(Main.GetInstance(), "zombie_king_commander"), PersistentDataType.STRING, thisEntity.getUniqueId().toString());
            }

            // spawn the wave
            ZombieKingRepellingWaveEntity zombieKingRepellingWave = new ZombieKingRepellingWaveEntity();
            zombieKingRepellingWave.SpawnCustomEntity(thisEntity.getLocation());

            return defaultState;
        }

        return this;
    }

    @Override
    public EntityState OnApply(Entity thisEntity)
    {
        EntityManager.AssignAnimation(thisEntity, animation);

        return this;
    }

    @Override
    public EntityState OnDeath(EntityDeathEvent e, Entity thisEntity)
    {
        UnboundZombieKingIdleState defaultState = (UnboundZombieKingIdleState) EntityStates.UNBOUND_ZOMBIE_KING_IDLE.GetEntityState();

        EntityState newState = defaultState.OnDeath(e, thisEntity);
        if (!Objects.equals(newState.GetStateID(), defaultState.GetStateID())) return newState;

        return this;
    }

    @Override
    public EntityState OnTakeDamage(EntityDamageEvent e, Entity thisEntity)
    {
        UnboundZombieKingIdleState defaultState = (UnboundZombieKingIdleState) EntityStates.UNBOUND_ZOMBIE_KING_IDLE.GetEntityState();

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
