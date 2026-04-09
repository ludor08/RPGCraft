package org.rpg.rPGCraft.Entities.EntityStates;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.Main;

public class ZombieKingSummonState extends EntityState
{

    public ZombieKingSummonState()
    {
        super("zombie_king_summon_state");
    }

    @Override
    public EntityState OnTick(Entity thisEntity) {
        return this;
    }

    @Override
    public EntityState OnAct(Event event, Entity thisEntity) {
        return this;
    }

    @Override
    public EntityState OnInteracted(PlayerInteractEntityEvent event, Entity thisEntity) {
        Player player = event.getPlayer();

        player.teleport(new Location(Main.GetInstance().worldManager.GetBossWorld(), 0, 0, 0));

        return this;
    }
}
