package org.rpg.rPGCraft.Races.Subraces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.Traits.Passive.AbnormalDiet_Raw_Fish;
import org.rpg.rPGCraft.Traits.Passive.Claws;
import org.rpg.rPGCraft.Traits.Passive.FelineAgility;
import org.rpg.rPGCraft.Traits.Passive.NineLives;
import org.rpg.rPGCraft.Traits.Passive.Size_Change_Small;

import java.util.List;

public class Feloid extends Race
{
    // set up the race
    public Feloid()
    {
        super(ChatColor.WHITE + ChatColor.BOLD.toString() + "Feloid", Material.COD, List.of(new AbnormalDiet_Raw_Fish(), new Claws(), new FelineAgility(), new NineLives(), new Size_Change_Small()),null);
    }
}
