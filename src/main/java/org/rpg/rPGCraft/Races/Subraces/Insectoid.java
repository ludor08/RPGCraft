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
    public Insectoid()
    {
        super(ChatColor.GRAY + ChatColor.BOLD.toString() + "Insectoid", Material.ARMADILLO_SCUTE, List.of(new Size_Change_Tiny(), new Wings(), new Squashable()),null);
    }
}
