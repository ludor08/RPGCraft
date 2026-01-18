package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.ExtremeDiet_Meat;
import org.rpg.rPGCraft.Traits.Passive.Pack;

import java.util.List;

public class Canineoid extends Race
{
    // set up the race
    public Canineoid()
    {
        super(ChatColor.GRAY + ChatColor.BOLD.toString() + "Canineoid", Material.BONE, List.of(new Pack(), new ExtremeDiet_Meat()),null);
    }
}
