package org.rpg.rPGCraft.Races;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Races.Subraces.Canineoid;
import org.rpg.rPGCraft.Races.Subraces.Feloid;
import org.rpg.rPGCraft.Races.Subraces.Foxoid;
import org.rpg.rPGCraft.Traits.Passive.AnimalAgility;
import org.rpg.rPGCraft.Traits.Passive.Fur;

import java.util.List;

public class Furoid extends Race
{
    // name of the race
    public Furoid()
    {
        super(ChatColor.GRAY + ChatColor.BOLD.toString() + "Furoid", Material.BEEF, List.of(new Fur(), new AnimalAgility()),List.of(new Foxoid(), new Feloid(), new Canineoid()));
    }
}
