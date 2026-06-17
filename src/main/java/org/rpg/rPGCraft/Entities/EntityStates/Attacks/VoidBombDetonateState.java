package org.rpg.rPGCraft.Entities.EntityStates.Attacks;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Animation.Animations.VoidBombExplode;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class VoidBombDetonateState extends EntityState
{
    int damage = 10;
    Animation explodeAnimation = new VoidBombExplode();

    public VoidBombDetonateState()
    {
        super("void_bomb_detonate_state");
    }

    @Override
    public EntityState OnApply(Entity thisEntity)
    {
        if (thisEntity instanceof LivingEntity livingEntity) livingEntity.setAI(false);
        EntityManager.AssignAnimation(thisEntity, explodeAnimation);

        return this;
    }

    @Override
    public EntityState OnAnimationEnd(Animation animation, Entity thisEntity)
    {
        if (animation.GetNameID().equals(explodeAnimation.GetNameID()))
        {
            List<Entity> orderTargets = RPGutils.SortEntityListByDistance(thisEntity.getNearbyEntities(5,5,5), thisEntity.getLocation());
            for (Entity entity : orderTargets)
            {
                if (entity instanceof Player targetPlayer)
                {
                    targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2, 0));
                    targetPlayer.damage(damage, thisEntity);
                }
            }

            thisEntity.remove();
        }

        return this;
    }

}
