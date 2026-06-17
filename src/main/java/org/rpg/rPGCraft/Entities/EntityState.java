package org.rpg.rPGCraft.Entities;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.rpg.rPGCraft.Animation.Animation;

public abstract class EntityState
{
    private String stateID;

    public EntityState(String stateID)
    {
        this.stateID = stateID;
    }

    public String GetStateID()
    {
        return stateID;
    }

    // runs every tick
    public EntityState OnTick(Entity thisEntity, int tick)
    {
        return this;
    }

    // runs every time the entity takes damage
    public EntityState OnTakeDamage(EntityDamageEvent e, Entity thisEntity)
    {
        return this;
    }

    // runs every time the entity dies
    public EntityState OnDeath(EntityDeathEvent e, Entity thisEntity)
    {
        return this;
    }

    // runs every time the entity's animation ends
    public EntityState OnAnimationEnd(Animation animation, Entity thisEntity)
    {
        return this;
    }

    // runs every time the entity moves
    public EntityState OnMove(EntityMoveEvent e, Entity thisEntity)
    {
        return this;
    }

    // runs when the state is applied
    public EntityState OnApply(Entity thisEntity)
    {
        return this;
    }

    // runs when the entity is interacted (right-clicked) with by a player
    public EntityState OnInteractedWith(PlayerInteractEntityEvent event, Entity thisEntity)
    {
        return this;
    }
}
