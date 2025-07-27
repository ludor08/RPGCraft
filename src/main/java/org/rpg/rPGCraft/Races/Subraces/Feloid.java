package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.AbnormalDiet_Raw_Fish;
import org.rpg.rPGCraft.Traits.Passive.Claws;
import org.rpg.rPGCraft.Traits.Passive.FelineAgility;
import org.rpg.rPGCraft.Traits.Passive.NineLives;

import java.util.List;

public class Feloid extends Race
{
    // set up the race
    public Feloid(Main main)
    {
        super("Feloid", ChatColor.WHITE, Material.COD, List.of(new AbnormalDiet_Raw_Fish(main), new Claws(main), new FelineAgility(main), new NineLives(main)),null);
    }
}
