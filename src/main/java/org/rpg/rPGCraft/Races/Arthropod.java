package org.rpg.rPGCraft.Races;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Races.Subraces.Canineoid;
import org.rpg.rPGCraft.Races.Subraces.Feloid;
import org.rpg.rPGCraft.Races.Subraces.Foxoid;
import org.rpg.rPGCraft.Races.Subraces.Insectoid;
import org.rpg.rPGCraft.Traits.Passive.AnimalAgility;
import org.rpg.rPGCraft.Traits.Passive.Arthropod_trait;
import org.rpg.rPGCraft.Traits.Passive.Fur;
import org.rpg.rPGCraft.Traits.Passive.Size_Change_Small;

import java.util.List;

public class Arthropod extends Race
{
    // name of the race
    public Arthropod(Main main)
    {
        super("Arthropod", ChatColor.DARK_GREEN, Material.SPIDER_EYE, List.of(new Size_Change_Small(main), new Arthropod_trait(main)),List.of(new Insectoid(main)));
    }
}
