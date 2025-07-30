package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.*;

import java.util.List;

public class Crabnoid extends Race
{
    // set up the race
    public Crabnoid(Main main)
    {
        super("Crabnoid", ChatColor.YELLOW, Material.GOLDEN_HELMET, List.of(new Pincers(main), new Exoskeleton(main), new Amphibious(main), new SandCrawler(main)),null);
    }
}
