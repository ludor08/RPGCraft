package org.rpg.rPGCraft.Entities;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;

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
    public abstract EntityState OnTick(Entity thisEntity);

    // runs when the entity acts at all (move, attack, kill, etc.)
    public abstract EntityState OnAct(Event event, Entity thisEntity);

    // runs when the entity is interacted (right-clicked) with by a player
    public abstract EntityState OnInteracted(PlayerInteractEntityEvent event, Entity thisEntity);
}
