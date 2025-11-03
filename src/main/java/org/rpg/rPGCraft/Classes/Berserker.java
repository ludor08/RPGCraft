package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.GiantsLeap;
import org.rpg.rPGCraft.Traits.Active.OathOfRage;
import org.rpg.rPGCraft.Traits.Active.SpinAttack;
import org.rpg.rPGCraft.Traits.CostModifier.BloodlustRage;
import org.rpg.rPGCraft.Traits.Passive.*;
import org.rpg.rPGCraft.Traits.Passive.Vitality.Vitality_1;

import java.util.List;

public class Berserker extends PlayableClass
{
    // name of the race
    public Berserker(Main main)
    {
        super("Berserker", ChatColor.RED, Material.IRON_AXE, List.of(ChatColor.AQUA + "Close range fight that can take a beating, and give it right back."), new TraitTree(List.of(
                // y 0
                new Node(new Vector2d(1,0), List.of(new OathOfRage(main)), "000"),
                new Node(new Vector2d(7,0), List.of(new GiantsLeap(main)), "000"),

                // y 1
                new Node(new Vector2d(1,1), List.of(new Vitality_1(main)), "000"),
                new Node(new Vector2d(2,1), List.of(new Charge(main)), "000"),
                new Node(new Vector2d(3,1), List.of(new BloodlustRage(main)), "000"),
                new Node(new Vector2d(4,1), List.of(new SpinAttack(main)), "000"),
                new Node(new Vector2d(5,1), List.of(new ThickSkin(main)), "000"),
                new Node(new Vector2d(6,1), List.of(new GiantsImpact(main)), "000"),
                new Node(new Vector2d(7,1), List.of(new Vitality_1(main)), "001"),

                // y 2
                new Node(new Vector2d(2,2), List.of(new ShieldCharge(main)), "000"),
                new Node(new Vector2d(4,2), List.of(new Cleve(main)), "000"),
                new Node(new Vector2d(6,2), List.of(new PowerfulSwings(main)), "000"),

                // y 3
                new Node(new Vector2d(1,3), List.of(new FlameCharge(main)), "000"),
                new Node(new Vector2d(2,3), List.of(new BullCharge(main)), "000"),
                new Node(new Vector2d(4,3), List.of(new PowerfulSwings(main)), "001"),
                new Node(new Vector2d(6,3), List.of(new IncineratingImpact(main)), "000"),
                new Node(new Vector2d(7,3), List.of(new CleavingImpact(main)), "000"),

                // y 4
                new Node(new Vector2d(4,4), List.of(new RejuvenatingRage(main)), "000"),

                // y 5
                new Node(new Vector2d(4,5), List.of(new ThickSkin(main)), "100"),
                new Node(new Vector2d(5,5), List.of(new LingeringRage(main)), "000")

                /*
                --new Node(new Vector2d(4,0), List.of(new Vitality_1(main)), "000"),
                --new Node(new Vector2d(4,1), List.of(new PowerfulSwings(main)), "000"),
                --new Node(new Vector2d(4,2), List.of(new OathOfRage(main)), "000"),
                --new Node(new Vector2d(3,1), List.of(new GiantsLeap(main)), "000"),
                --new Node(new Vector2d(2,1), List.of(new GiantsImpact(main)), "000"),
                --new Node(new Vector2d(2,2), List.of(new IncineratingImpact(main)), "000"),
                --new Node(new Vector2d(4,3), List.of(new Vitality_1(main)), "001"),
                --new Node(new Vector2d(3,3), List.of(new SpinAttack(main)), "000"),
                --new Node(new Vector2d(5,3), List.of(new Cleve(main)), "000"),
                --new Node(new Vector2d(2,4), List.of(new Charge(main)), "000"),
                --new Node(new Vector2d(2,5), List.of(new BullCharge(main)), "000"),
                --new Node(new Vector2d(3,4), List.of(new FlameCharge(main)), "000"),
                --new Node(new Vector2d(5,2), List.of(new ThickSkin(main)), "000"),
                --new Node(new Vector2d(5,4), List.of(new PowerfulSwings(main)), "001"),
                --new Node(new Vector2d(5,5), List.of(new RejuvenatingRage(main)), "000"),
                --new Node(new Vector2d(3,5), List.of(new BloodlustRage(main)), "000"),
                --new Node(new Vector2d(3,6), List.of(new ShieldCharge(main)), "000"),
                --new Node(new Vector2d(3,7), List.of(new ThickSkin(main)), "000"),
                --new Node(new Vector2d(2,3), List.of(new CleavingImpact(main)), "000"),
                --new Node(new Vector2d(3,8), List.of(new LingeringRage(main)), "000")
                */
        )));
    }
}
