package org.rpg.rPGCraft.Races;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Races.Subraces.Feloid;
import org.rpg.rPGCraft.Traits.AnimalAgility;
import org.rpg.rPGCraft.Traits.Fur;

import java.util.List;

public class Furoid extends Race
{

    // name of the race
    public Furoid()
    {
        super("Furoid", ChatColor.GRAY, Material.BEEF, List.of(new Fur(), new AnimalAgility()),List.of(new Feloid()));
    }
}
