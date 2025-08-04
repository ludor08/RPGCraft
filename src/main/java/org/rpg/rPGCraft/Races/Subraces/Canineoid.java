package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.Diet.ExtremeDiet_Meat;
import org.rpg.rPGCraft.Traits.Passive.Pack;

import java.util.List;

public class Canineoid extends Race
{
    // set up the race
    public Canineoid(Main main)
    {
        super("Canineoid", ChatColor.GRAY, Material.BONE, List.of(new Pack(main), new ExtremeDiet_Meat(main)),null);
    }
}
