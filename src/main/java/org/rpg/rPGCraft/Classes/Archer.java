package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.GrapplingHookArrow;
import org.rpg.rPGCraft.Traits.Active.SteadyAim;
import org.rpg.rPGCraft.Traits.Passive.Headshot.BetterHeadshots;
import org.rpg.rPGCraft.Traits.Passive.Headshot.Headshot;
import org.rpg.rPGCraft.Traits.Passive.Ricochet;

import java.util.List;

public class Archer extends PlayableClass
{
    // name of the race
    public Archer(Main main)
    {
        super("Archer", ChatColor.BLUE, Material.BOW, List.of(ChatColor.AQUA + "Uses long range weapons of all kinds."), new TraitTree(List.of(
                new Node(new Vector2d(4,0), List.of(new Headshot(main)), "000"),
                new Node(new Vector2d(4,1), List.of(new GrapplingHookArrow(main)), "000"),
                new Node(new Vector2d(4,2), List.of(new BetterHeadshots(main)), "000"),
                new Node(new Vector2d(4,3), List.of(new Ricochet(main)), "000"),
                new Node(new Vector2d(4,4), List.of(new SteadyAim(main)), "000")
        )));
    }
}
