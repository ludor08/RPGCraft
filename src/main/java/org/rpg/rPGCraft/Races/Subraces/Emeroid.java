package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.DiamondSkin;
import org.rpg.rPGCraft.Traits.Passive.GreenAndShiny;

import java.util.List;

public class Emeroid extends Race
{
    // set up the race
    public Emeroid(Main main)
    {
        super("Emeroid", ChatColor.GREEN, Material.EMERALD, List.of(new GreenAndShiny(main)),null);
    }
}
