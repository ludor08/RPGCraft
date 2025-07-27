package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.AbnormalDiet_Berries;
import org.rpg.rPGCraft.Traits.Passive.Extreme_Diet_Meat;
import org.rpg.rPGCraft.Traits.Passive.Pack;
import org.rpg.rPGCraft.Traits.Passive.Pounce;

import java.util.List;

public class Canineoid extends Race
{
    // set up the race
    public Canineoid(Main main)
    {
        super("Canineoid", ChatColor.GRAY, Material.BONE, List.of(new Pack(main), new Extreme_Diet_Meat(main)),null);
    }
}
