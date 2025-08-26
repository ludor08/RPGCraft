package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.*;
import org.rpg.rPGCraft.Traits.Passive.BackStab;
import org.rpg.rPGCraft.Traits.Passive.Combo;
import org.rpg.rPGCraft.Traits.Passive.Headshot.BetterHeadshots;
import org.rpg.rPGCraft.Traits.Passive.Headshot.Headshot;
import org.rpg.rPGCraft.Traits.Passive.Ricochet;

import java.util.List;

public class Rogue extends PlayableClass
{
    // name of the race
    public Rogue(Main main)
    {
        super("Rogue", ChatColor.DARK_RED, Material.IRON_SWORD, List.of(ChatColor.AQUA + "Uses close and long range weapons, is mainly focused on combos. Uses limited armor."), new TraitTree(List.of(
                new Node(new Vector2d(4,0), List.of(new Combo(main)), "000"),
                new Node(new Vector2d(4,1), List.of(new Dash(main)), "000"),
                new Node(new Vector2d(4,2), List.of(new BackStab(main)), "000"),
                new Node(new Vector2d(4,3), List.of(new SpinAttack(main)), "000"),
                new Node(new Vector2d(4,4), List.of(new SmokeBomb(main)), "000")
        )));
    }
}
