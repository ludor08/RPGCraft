package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.*;
import org.rpg.rPGCraft.Traits.CostModifier.PoisonKunai;
import org.rpg.rPGCraft.Traits.CostModifier.SecretTechnique;
import org.rpg.rPGCraft.Traits.Passive.*;
import org.rpg.rPGCraft.Traits.Passive.Headshot.BetterHeadshots;
import org.rpg.rPGCraft.Traits.Passive.Headshot.Headshot;

import java.util.List;

public class Rogue extends PlayableClass
{
    // name of the race
    public Rogue(Main main)
    {
        super("Rogue", ChatColor.DARK_RED, Material.IRON_SWORD, List.of(ChatColor.AQUA + "Uses close and long range weapons, is mainly focused on combos."), new TraitTree(List.of(
                new Node(new Vector2d(4,0), List.of(new Combo(main)), "000"),
                new Node(new Vector2d(4,1), List.of(new Dash(main)), "000"),
                new Node(new Vector2d(3,1), List.of(new LungingDash(main)), "000"),
                new Node(new Vector2d(2,2), List.of(new Dodge(main)), "000"),
                new Node(new Vector2d(3,2), List.of(new BackStab(main)), "000"),
                new Node(new Vector2d(3,3), List.of(new Kunai(main)), "000"),
                new Node(new Vector2d(3,4), List.of(new BetterBackStab(main)), "000"),
                new Node(new Vector2d(5,1), List.of(new SmokeBomb(main)), "000"),
                new Node(new Vector2d(5,2), List.of(new SneakAttack(main)), "000"),
                new Node(new Vector2d(5,3), List.of(new Lacerate(main)), "000"),
                new Node(new Vector2d(6,2), List.of(new BetterSmokeBomb(main)), "000"),
                new Node(new Vector2d(3,5), List.of(new PoisonKunai(main)), "000"),
                new Node(new Vector2d(3,6), List.of(new Dodge(main)), "001"),
                new Node(new Vector2d(2,6), List.of(new AssassinsMixture(main)), "000"),
                new Node(new Vector2d(5,4), List.of(new Stealth(main)), "000"),
                new Node(new Vector2d(5,5), List.of(new Dodge(main)), "002"),
                new Node(new Vector2d(5,6), List.of(new ExtraAgilityRunning(main)), "000"),
                new Node(new Vector2d(6,6), List.of(new SecretTechnique(main)), "000"),
                new Node(new Vector2d(3,7), List.of(new BetterCombo(main)), "000")
        )));
    }
}
