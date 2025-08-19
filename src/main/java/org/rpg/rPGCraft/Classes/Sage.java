package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.MendMinorWounds;
import org.rpg.rPGCraft.Traits.Passive.EmpoweredMixture.EmpoweredMixture_1;
import org.rpg.rPGCraft.Traits.Passive.EnhancedMixture.EnhancedMixture_1;
import org.rpg.rPGCraft.Traits.Passive.EnhancedMixture.EnhancedMixture_2;
import org.rpg.rPGCraft.Traits.Passive.GreaterCapacity.GreaterCapacity_1;
import org.rpg.rPGCraft.Traits.Passive.GreaterCapacity.GreaterCapacity_2;
import org.rpg.rPGCraft.Traits.Passive.ManaRegainSpeed.ManaRegainSpeed;

import java.util.List;

public class Sage extends PlayableClass
{
    // name of the race
    public Sage(Main main)
    {
        super("Sage", ChatColor.LIGHT_PURPLE, Material.STICK, List.of(ChatColor.AQUA + "Uses spells and brews potions to heal, buff, and debuff."), new TraitTree(List.of(
                new Node(new Vector2d(4,0), List.of(new GreaterCapacity_1(main), new GreaterCapacity_2(main)), "000"),
                new Node(new Vector2d(4,1), List.of(new EnhancedMixture_1(main), new EnhancedMixture_2(main)), "000"),
                new Node(new Vector2d(4,2), List.of(new ManaRegainSpeed(main)), "000"),
                new Node(new Vector2d(4,3), List.of(new MendMinorWounds(main)), "000"),
                new Node(new Vector2d(4,4), List.of(new EmpoweredMixture_1(main)), "000")
        )));
    }
}
