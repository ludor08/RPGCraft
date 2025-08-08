package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.Rage;
import org.rpg.rPGCraft.Traits.Passive.Question_Axer;
import org.rpg.rPGCraft.Traits.Passive.Vitality.Vitality_1;
import org.rpg.rPGCraft.Traits.Passive.Vitality.Vitality_2;

import java.util.List;

public class Sage extends PlayableClass
{
    // name of the race
    public Sage(Main main)
    {
        super("Sage", ChatColor.LIGHT_PURPLE, Material.STICK, List.of(ChatColor.AQUA + "Uses spells and brews potions to heal, buff, and debuff."), new TraitTree(List.of(
                new Node(new Vector2d(4,0), List.of(new Vitality_1(main), new Vitality_2(main)), "000")
        )));
    }
}
