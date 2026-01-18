package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.DiamondSkin;
import org.rpg.rPGCraft.Traits.Passive.Fur;
import org.rpg.rPGCraft.Traits.Passive.ManyLegs;
import org.rpg.rPGCraft.Traits.Passive.ToxicBite;

import java.util.List;

public class Diamoid extends Race
{
    // set up the race
    public Diamoid()
    {
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Diamoid", Material.DIAMOND, List.of(new DiamondSkin()),null);
    }
}
