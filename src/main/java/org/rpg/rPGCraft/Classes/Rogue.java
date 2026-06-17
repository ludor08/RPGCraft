package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.*;
import org.rpg.rPGCraft.Traits.CostModifier.PoisonKunai;
import org.rpg.rPGCraft.Traits.CostModifier.PowerfulKunai;
import org.rpg.rPGCraft.Traits.CostModifier.SecretTechnique;
import org.rpg.rPGCraft.Traits.Passive.*;

import java.util.List;

public class Rogue extends PlayableClass
{
    // name of the race
    public Rogue()
    {
        super("Rogue", ChatColor.DARK_RED, Material.IRON_SWORD, List.of(ChatColor.AQUA + "Uses close and long range weapons, is primarily focused on combos."), new TraitTree(List.of(
                new Node(new Vector2d(4,0), List.of(new Combo()), "000"),
                new Node(new Vector2d(4,1), List.of(new Dash()), "000"),
                new Node(new Vector2d(3,1), List.of(new LungingDash()), "000"),
                new Node(new Vector2d(2,2), List.of(new Dodge()), "000"),
                new Node(new Vector2d(3,2), List.of(new BackStab()), "000"),
                new Node(new Vector2d(3,3), List.of(new Kunai()), "000"),
                new Node(new Vector2d(3,4), List.of(new BetterBackStab()), "000"),
                new Node(new Vector2d(5,1), List.of(new SmokeBomb()), "000"),
                new Node(new Vector2d(5,2), List.of(new SneakAttack()), "000"),
                new Node(new Vector2d(5,3), List.of(new Lacerate()), "000"),
                new Node(new Vector2d(6,2), List.of(new BetterSmokeBomb()), "000"),
                new Node(new Vector2d(3,5), List.of(new PoisonKunai()), "000"),
                new Node(new Vector2d(4,6), List.of(new PowerfulKunai()), "000"),
                new Node(new Vector2d(3,6), List.of(new Dodge()), "001"),
                new Node(new Vector2d(2,6), List.of(new AssassinsMixture()), "000"),
                new Node(new Vector2d(5,4), List.of(new Stealth()), "000"),
                new Node(new Vector2d(5,5), List.of(new Dodge()), "002"),
                new Node(new Vector2d(5,6), List.of(new ExtraAgilityRunning()), "000"),
                new Node(new Vector2d(6,6), List.of(new SecretTechnique()), "000"),
                new Node(new Vector2d(3,7), List.of(new BetterCombo()), "000")
                // ADD ONE MORE
        )));
    }
}
