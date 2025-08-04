package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.Diet.AbnormalDiet_Death;
import org.rpg.rPGCraft.Traits.Passive.NightVision;
import org.rpg.rPGCraft.Traits.Passive.SupernaturalGrowth;
import org.rpg.rPGCraft.Traits.Passive.ToxicSpores;

import java.util.List;

public class Mushoid extends Race
{
    // set up the race
    public Mushoid(Main main)
    {
        super("Mushoid", ChatColor.RED, Material.RED_MUSHROOM, List.of(new NightVision(main), new AbnormalDiet_Death(main), new ToxicSpores(main),new SupernaturalGrowth(main)),null);
    }
}
