package org.rpg.rPGCraft.Entities.EntityStates.ZombieKing;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Animation.Animations.TestBlackSquare;
import org.rpg.rPGCraft.Animation.Animations.UnTestBlackSquare;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;
import java.util.UUID;

public class WeakenZombieKingEnhanceZombiesState extends EntityState {

    Animation startAnimation = new TestBlackSquare();
    Animation stopAnimation = new UnTestBlackSquare();

    int healing = 5;
    List<PotionEffect> buffs = List.of(new PotionEffect(PotionEffectType.RESISTANCE, 300, 0), new PotionEffect(PotionEffectType.STRENGTH, 300, 0));

    WeakenZombieKingIdleState defaultState = (WeakenZombieKingIdleState) EntityStates.WEAKEN_ZOMBIE_KING_IDLE.GetEntityState();

    public WeakenZombieKingEnhanceZombiesState()
    {
        super("weaken_zombie_king_enhance_zombies_state");
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        defaultState.ScanForShieldCatalysts(thisEntity);

        return this;
    }

    public EntityState OnAnimationEnd(Animation animation, Entity thisEntity)
    {
        // get the marker
        Entity marker = Bukkit.getEntity(UUID.fromString(thisEntity.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER.GetNamespacedKey(), PersistentDataType.STRING)));

        if (animation.GetNameID().equals(startAnimation.GetNameID()))
        {
            List<Entity> orderTargets = RPGutils.SortEntityListByDistance(thisEntity.getNearbyEntities(50,10,50), thisEntity.getLocation());
            for (Entity entity : orderTargets)
            {
                if (entity instanceof Zombie zombie)
                {
                    RPGparticles.SpawnParticle(20, new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY() + (entity.getHeight()/2), entity.getLocation().getZ()), new Vector3d(0.625, 0.125*(entity.getHeight()/2), 0.625), Particle.SOUL, 0);
                    zombie.heal(healing);

                    for (PotionEffect buff : buffs)
                    {
                        zombie.addPotionEffect(buff);
                    }
                }
            }

            EntityManager.AssignAnimation(thisEntity, stopAnimation);
        }
        else if (animation.GetNameID().equals(stopAnimation.GetNameID()))
        {
            return EntityStates.WEAKEN_ZOMBIE_KING_IDLE.GetEntityState();
        }

        return this;
    }

    @Override
    public EntityState OnApply(Entity thisEntity)
    {
        EntityManager.AssignAnimation(thisEntity, startAnimation);

        return this;
    }

    @Override
    public EntityState OnDeath(EntityDeathEvent e, Entity thisEntity)
    {
        defaultState.OnDeath(e, thisEntity);

        return this;
    }

    @Override
    public EntityState OnTakeDamage(EntityDamageEvent e, Entity thisEntity)
    {
        defaultState.OnTakeDamage(e, thisEntity);

        return this;
    }

    @Override
    public EntityState OnMove(EntityMoveEvent e, Entity thisEntity)
    {
        e.setCancelled(true);

        return this;
    }
}
