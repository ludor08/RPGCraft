package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.*;
import org.rpg.rPGCraft.Traits.Passive.*;
import org.rpg.rPGCraft.Traits.Passive.BoostedHealing_speed;
import org.rpg.rPGCraft.Traits.Passive.EmpoweredMixture_1;
import org.rpg.rPGCraft.Traits.Passive.EnhancedMixture_1;
import org.rpg.rPGCraft.Traits.Passive.EnhancedMixture_2;
import org.rpg.rPGCraft.Traits.Passive.GreaterCapacity_1;
import org.rpg.rPGCraft.Traits.Passive.ManaRegainSpeed;

import java.util.List;

public class Sage extends PlayableClass
{
    // name of the race
    public Sage()
    {
        super("Sage", ChatColor.LIGHT_PURPLE, Material.STICK, List.of(ChatColor.AQUA + "Uses spells and brews potions to heal, buff, and debuff."), new TraitTree(List.of(
                new Node(new Vector2d(2,5), List.of(new GreaterCapacity_1()), "000"),
                new Node(new Vector2d(2,6), List.of(new ConjureLightning()), "000"),
                new Node(new Vector2d(4,5), List.of(new PowerOfTheOakQueen()), "000"),
                new Node(new Vector2d(5,5), List.of(new EmpoweredMixture_1()), "001"),
                new Node(new Vector2d(3,3), List.of(new WeakeningSiphon()), "000"),
                new Node(new Vector2d(2,4), List.of(new BoostedHealing_speed()), "000"),
                new Node(new Vector2d(2,2), List.of(new GreaterMinorHealing()), "000"),
                new Node(new Vector2d(3,4), List.of(new RebukeOfTheFlame()), "000"),
                new Node(new Vector2d(4,4), List.of(new PotentRebuke()), "000"),
                new Node(new Vector2d(3,1), List.of(new GreaterCapacity_1()), "001"),
                new Node(new Vector2d(5,1), List.of(new EnhancedMixture_1(), new EnhancedMixture_2()), "000"),
                new Node(new Vector2d(4,0), List.of(new ManaRegainSpeed()), "000"),
                new Node(new Vector2d(4,1), List.of(new MendMinorWounds()), "000"),
                new Node(new Vector2d(6,2), List.of(new EmpoweredMixture_1()), "000"),
                new Node(new Vector2d(5,2), List.of(new BreathOfTheDragons()), "000"),
                new Node(new Vector2d(6,3), List.of(new ReflectingBreath()), "000"),
                new Node(new Vector2d(1,2), List.of(new FlashOfOak()), "000"),
                new Node(new Vector2d(1,1), List.of(new IronOak()), "000"),
                new Node(new Vector2d(3,2), List.of(new AuraOfSiphoning()), "000")
        )));
    }
}
