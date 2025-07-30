package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.*;

import java.util.List;

public class Arachnoid extends Race
{
    // set up the race
    public Arachnoid(Main main)
    {
        super("Arachnoid", ChatColor.RED, Material.FERMENTED_SPIDER_EYE, List.of(new Fur(main), new ToxicBite(main), new ManyLegs(main)),null);
    }
}
