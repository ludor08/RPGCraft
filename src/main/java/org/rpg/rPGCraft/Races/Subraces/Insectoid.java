package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.Size_Change_Tiny;
import org.rpg.rPGCraft.Traits.Passive.Squashable;
import org.rpg.rPGCraft.Traits.Passive.Wings;

import java.util.List;

public class Insectoid extends Race
{
    // set up the race
    public Insectoid(Main main)
    {
        super("Insectoid", ChatColor.GRAY, Material.ARMADILLO_SCUTE, List.of(new Size_Change_Tiny(main), new Wings(main), new Squashable(main)),null);
    }
}
