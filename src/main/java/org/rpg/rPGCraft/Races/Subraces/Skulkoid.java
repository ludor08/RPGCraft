package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.Diet.AbnormalDiet_Death;
import org.rpg.rPGCraft.Traits.Passive.Diet.AbnormalDiet_Souls;
import org.rpg.rPGCraft.Traits.Passive.NightVision;
import org.rpg.rPGCraft.Traits.Passive.OnesOwnKind;
import org.rpg.rPGCraft.Traits.Passive.ToxicSpores;
import org.rpg.rPGCraft.Traits.Passive.WeAreOne;

import java.util.List;

public class Skulkoid extends Race
{
    // set up the race
    public Skulkoid(Main main)
    {
        super("Skulkoid", ChatColor.DARK_PURPLE, Material.SCULK_VEIN, List.of(new AbnormalDiet_Souls(main), new WeAreOne(main), new OnesOwnKind(main)),null);
    }
}
