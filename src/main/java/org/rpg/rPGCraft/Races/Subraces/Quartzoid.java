package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.Piezoelectric;

import java.util.List;

public class Quartzoid extends Race
{
    // set up the race
    public Quartzoid()
    {
        super(ChatColor.WHITE + ChatColor.BOLD.toString() + "Quartzoid", Material.QUARTZ, List.of(new Piezoelectric()),null);
    }
}
