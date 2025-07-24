package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.AbnormalDiet_Berries;
import org.rpg.rPGCraft.Traits.Passive.Pounce;

import java.util.List;

public class Foxoid extends Race
{
    // set up the race
    public Foxoid(Main main)
    {
        super("Foxoid", ChatColor.GOLD, Material.SWEET_BERRIES, List.of(new Pounce(main), new AbnormalDiet_Berries(main)),null);
    }
}
