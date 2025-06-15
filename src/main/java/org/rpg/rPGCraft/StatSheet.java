package org.rpg.rPGCraft;

import org.bukkit.entity.Player;

public class StatSheet {

    private Race race;
    private Race subrace;

    private Player player;

    public Player GetPlayer()
    {
        return player;
    }

    public StatSheet(Player player)
    {
        this.player = player;
    }
}
