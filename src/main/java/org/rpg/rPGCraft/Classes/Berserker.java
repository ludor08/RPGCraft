package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Vitality;

import java.util.List;

public class Berserker extends PlayableClass
{
    // name of the race
    public Berserker(Main main)
    {
        super("Berserker", ChatColor.RED, Material.IRON_AXE, List.of(ChatColor.AQUA + "Close range fight that can take a beating, and give it right back"), new TraitTree(List.of(
                new Node(new Vector2d(0,2), new Vitality(main)
                ))));
    }
}
