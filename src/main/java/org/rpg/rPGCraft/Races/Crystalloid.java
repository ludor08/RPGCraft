package org.rpg.rPGCraft.Races;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Races.Subraces.*;
import org.rpg.rPGCraft.Traits.Passive.Regeneration;
import org.rpg.rPGCraft.Traits.Passive.SuperHardenedSkin;

import java.util.List;

public class Crystalloid extends Race
{
    // name of the race
    public Crystalloid()
    {
        super(ChatColor.WHITE + ChatColor.BOLD.toString() + "Crystalloid", Material.DIAMOND_PICKAXE, List.of(new SuperHardenedSkin()),List.of(new Diamoid(), new Emeroid(), new Quartzoid()));
    }
}
