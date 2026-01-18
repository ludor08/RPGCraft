package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.AbnormalDiet_Souls;
import org.rpg.rPGCraft.Traits.Passive.WeAreOne;

import java.util.List;

public class Skulkoid extends Race
{
    // set up the race
    public Skulkoid()
    {
        super(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Skulkoid", Material.SCULK_VEIN, List.of(new AbnormalDiet_Souls(), new WeAreOne()),null);
    }
}
