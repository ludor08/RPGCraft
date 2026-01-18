package org.rpg.rPGCraft.Races;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Races.Subraces.Mushoid;
import org.rpg.rPGCraft.Races.Subraces.Skulkoid;
import org.rpg.rPGCraft.Traits.Passive.Regeneration;

import java.util.List;

public class Fungoid extends Race
{
    // name of the race
    public Fungoid()
    {
        super(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Fungoid", Material.BROWN_MUSHROOM, List.of(new Regeneration()),List.of(new Mushoid(), new Skulkoid()));
    }
}
