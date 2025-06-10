package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.AnimalAgility;
import org.rpg.rPGCraft.Traits.Fur;

import java.util.List;

public class Feloid extends Race
{
    // name of the race
    public Feloid()
    {
        super("Feloid", ChatColor.WHITE, Material.COD, List.of(new Fur(), new AnimalAgility()),null);
    }
}
