package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.FoxoidAgility;
import org.rpg.rPGCraft.Traits.Pounce;

import java.util.List;

public class Foxoid extends Race
{
    // name of the race
    public Foxoid(Main main)
    {
        super("Foxoid", ChatColor.GOLD, Material.SWEET_BERRIES, List.of(new FoxoidAgility(main), new Pounce()),null);
    }
}
