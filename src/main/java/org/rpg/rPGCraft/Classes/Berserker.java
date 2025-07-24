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

public class Berserker extends PlayableClass
{
    // name of the race
    public Berserker(Main main)
    {
        super("Berserker", ChatColor.RED, Material.IRON_AXE, List.of(ChatColor.AQUA + "Close range fight that can take a beating, and give it right back."), new TraitTree(List.of(
                new Node(new Vector2d(4,0), List.of(new Vitality_1(main), new Vitality_2(main))),
                new Node(new Vector2d(3,1), List.of(new Question_Axer(main))),
                new Node(new Vector2d(4,1), List.of(new Rage(main)))
        )));
    }
}
