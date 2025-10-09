package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.Rage;
import org.rpg.rPGCraft.Traits.Active.SpinAttack;
import org.rpg.rPGCraft.Traits.Passive.PowerfulSwings;
import org.rpg.rPGCraft.Traits.Passive.Vitality.Vitality_1;

import java.util.List;

public class Berserker extends PlayableClass
{
    // name of the race
    public Berserker(Main main)
    {
        super("Berserker", ChatColor.RED, Material.IRON_AXE, List.of(ChatColor.AQUA + "Close range fight that can take a beating, and give it right back."), new TraitTree(List.of(
                new Node(new Vector2d(4,0), List.of(new Vitality_1(main)), "000"),
                new Node(new Vector2d(4,1), List.of(new PowerfulSwings(main)), "000"),
                new Node(new Vector2d(4,2), List.of(new Rage(main)), "000"),
                new Node(new Vector2d(4,3), List.of(new Vitality_1(main)), "001"),
                new Node(new Vector2d(3,3), List.of(new SpinAttack(main)), "000"),
                new Node(new Vector2d(4,4), List.of(new PowerfulSwings(main)), "001")
        )));
    }
}
