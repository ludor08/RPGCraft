package org.rpg.rPGCraft.Entities.EntityStates.Attacks;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.RPGparticles;

public class VoidBombFallingState extends EntityState
{
    public VoidBombFallingState()
    {
        super("void_bomb_falling_state");
    }

    @Override
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        RPGparticles.SpawnParticle(100, thisEntity.getLocation(), new Vector3d(0.25f, 0.25f, 0.25f), Particle.CRIMSON_SPORE, 0);

        if (thisEntity.isOnGround())
        {
            return EntityStates.VOID_BOMB_DETONATE.GetEntityState();
        }

        return this;
    }

}
