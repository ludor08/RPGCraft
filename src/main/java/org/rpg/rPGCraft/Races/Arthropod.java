package org.rpg.rPGCraft.Races;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Races.Subraces.*;
import org.rpg.rPGCraft.Traits.Passive.AnimalAgility;
import org.rpg.rPGCraft.Traits.Passive.Arthropod_trait;
import org.rpg.rPGCraft.Traits.Passive.Fur;
import org.rpg.rPGCraft.Traits.Passive.Size_Change_Small;

import java.util.List;

public class Arthropod extends Race
{
    // name of the race
    public Arthropod()
    {
        super(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "Arthropod", Material.SPIDER_EYE, List.of(new Size_Change_Small(), new Arthropod_trait()),List.of(new Insectoid(), new Arachnoid(), new Crabnoid()));
    }
}
