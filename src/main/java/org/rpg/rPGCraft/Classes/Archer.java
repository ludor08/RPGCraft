package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.GrapplingHookArrow;
import org.rpg.rPGCraft.Traits.Active.PinShot;
import org.rpg.rPGCraft.Traits.Active.SteadyAim;
import org.rpg.rPGCraft.Traits.CostModifier.CheaperGrapplingHook;
import org.rpg.rPGCraft.Traits.CostModifier.CheaperSteadyAim;
import org.rpg.rPGCraft.Traits.CostModifier.EvenCheaperGrapplingHook;
import org.rpg.rPGCraft.Traits.Passive.*;
import org.rpg.rPGCraft.Traits.Passive.GreaterCapacity.GreaterCapacity_1;
import org.rpg.rPGCraft.Traits.Passive.Headshot.BetterHeadshots;
import org.rpg.rPGCraft.Traits.Passive.Headshot.Headshot;
import org.rpg.rPGCraft.Traits.Passive.Vitality.Vitality_1;

import java.util.List;

public class Archer extends PlayableClass
{
    // name of the race
    public Archer(Main main)
    {
        super("Archer", ChatColor.BLUE, Material.BOW, List.of(ChatColor.AQUA + "Uses long range weapons of all kinds."), new TraitTree(List.of(
                new Node(new Vector2d(4,0), List.of(new Headshot(main)), "000"),
                new Node(new Vector2d(3,2), List.of(new GrapplingHookArrow(main)), "000"),
                new Node(new Vector2d(3,3), List.of(new CheaperGrapplingHook(main)), "000"),
                new Node(new Vector2d(4,1), List.of(new BetterHeadshots(main)), "000"),
                new Node(new Vector2d(3,1), List.of(new Ricochet(main)), "000"),
                new Node(new Vector2d(5,1), List.of(new SteadyAim(main)), "000"),
                new Node(new Vector2d(2,4), List.of(new PowerShot(main)), "000"),
                new Node(new Vector2d(2,5), List.of(new TeleportingGrapple(main)), "000"),
                new Node(new Vector2d(3,5), List.of(new PinShot(main)), "000"),
                new Node(new Vector2d(5,2), List.of(new LaserShot(main)), "000"),
                new Node(new Vector2d(5,3), List.of(new PowerShot(main)), "001"),
                new Node(new Vector2d(2,3), List.of(new WindShot(main)), "000"),
                new Node(new Vector2d(2,6), List.of(new HighPowerWindShot(main)), "000"),
                new Node(new Vector2d(6,3), List.of(new CheaperSteadyAim(main)), "000"),
                new Node(new Vector2d(6,4), List.of(new EvenCheaperGrapplingHook(main)), "000"),
                new Node(new Vector2d(6,5), List.of(new CorrectiveWindResistance(main)), "000"),
                new Node(new Vector2d(4,5), List.of(new GreaterCapacity_1(main)), "000"),
                new Node(new Vector2d(3,5), List.of(new BloodyRetreat(main)), "000"),
                new Node(new Vector2d(5,5), List.of(new Vitality_1(main)), "000"),
                new Node(new Vector2d(6,6), List.of(new AntiGravityArrow(main)), "000")
        )));
    }
}
