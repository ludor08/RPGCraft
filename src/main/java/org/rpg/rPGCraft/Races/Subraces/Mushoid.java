package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.AbnormalDiet_Death;
import org.rpg.rPGCraft.Traits.Passive.NightVision;
import org.rpg.rPGCraft.Traits.Passive.SupernaturalGrowth;
import org.rpg.rPGCraft.Traits.Passive.ToxicSpores;

import java.util.List;

public class Mushoid extends Race
{
    // set up the race
    public Mushoid()
    {
        super(ChatColor.RED + ChatColor.BOLD.toString() + "M" + ChatColor.translateAlternateColorCodes('&', "&8b4513") + "u"+ ChatColor.RED + "s" + ChatColor.translateAlternateColorCodes('&', "&8b4513") + "h" + ChatColor.RED + "o" + ChatColor.translateAlternateColorCodes('&', "&8b4513") + "i" + ChatColor.RED + "d", Material.RED_MUSHROOM, List.of(new NightVision(), new AbnormalDiet_Death(), new ToxicSpores(),new SupernaturalGrowth()),null);
    }
}
