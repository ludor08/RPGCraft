package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.GiantsLeap;
import org.rpg.rPGCraft.Traits.Active.OathOfRage;
import org.rpg.rPGCraft.Traits.Active.SpinAttack;
import org.rpg.rPGCraft.Traits.CostModifier.BloodlustRage;
import org.rpg.rPGCraft.Traits.Passive.*;
import org.rpg.rPGCraft.Traits.Passive.Vitality_1;

import java.util.List;

public class Berserker extends PlayableClass
{
    // name of the race
    public Berserker()
    {
        super("Berserker", ChatColor.RED, Material.IRON_AXE, List.of(ChatColor.AQUA + "Close range fight that can take a beating, and give it right back."), new TraitTree(List.of(
                // y 0
                new Node(new Vector2d(1,0), List.of(new OathOfRage()), "000"),
                new Node(new Vector2d(7,0), List.of(new GiantsLeap()), "000"),

                // y 1
                new Node(new Vector2d(1,1), List.of(new Vitality_1()), "000"),
                new Node(new Vector2d(2,1), List.of(new Charge()), "000"),
                new Node(new Vector2d(3,1), List.of(new BloodlustRage()), "000"),
                new Node(new Vector2d(4,1), List.of(new SpinAttack()), "000"),
                new Node(new Vector2d(5,1), List.of(new ThickSkin()), "000"),
                new Node(new Vector2d(6,1), List.of(new GiantsImpact()), "000"),
                new Node(new Vector2d(7,1), List.of(new Vitality_1()), "001"),

                // y 2
                new Node(new Vector2d(2,2), List.of(new ShieldCharge()), "000"),
                new Node(new Vector2d(4,2), List.of(new Cleve()), "000"),
                new Node(new Vector2d(6,2), List.of(new PowerfulSwings()), "000"),

                // y 3
                new Node(new Vector2d(1,3), List.of(new FlameCharge()), "000"),
                new Node(new Vector2d(2,3), List.of(new BullCharge()), "000"),
                new Node(new Vector2d(4,3), List.of(new PowerfulSwings()), "001"),
                new Node(new Vector2d(6,3), List.of(new IncineratingImpact()), "000"),
                new Node(new Vector2d(7,3), List.of(new CleavingImpact()), "000"),

                // y 4
                new Node(new Vector2d(4,4), List.of(new RejuvenatingRage()), "000"),

                // y 5
                new Node(new Vector2d(4,5), List.of(new ThickSkin()), "100"),
                new Node(new Vector2d(5,5), List.of(new LingeringRage()), "000")
        )));
    }
}
