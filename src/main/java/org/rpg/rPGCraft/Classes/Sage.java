package org.rpg.rPGCraft.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Node;
import org.rpg.rPGCraft.PlayableClass;
import org.rpg.rPGCraft.TraitTree;
import org.rpg.rPGCraft.Traits.Active.*;
import org.rpg.rPGCraft.Traits.Passive.*;
import org.rpg.rPGCraft.Traits.Passive.Boosted.Healing.BoostedHealing_speed;
import org.rpg.rPGCraft.Traits.Passive.EmpoweredMixture.EmpoweredMixture_1;
import org.rpg.rPGCraft.Traits.Passive.EnhancedMixture.EnhancedMixture_1;
import org.rpg.rPGCraft.Traits.Passive.EnhancedMixture.EnhancedMixture_2;
import org.rpg.rPGCraft.Traits.Passive.GreaterCapacity.GreaterCapacity_1;
import org.rpg.rPGCraft.Traits.Passive.ManaRegainSpeed.ManaRegainSpeed;

import java.util.List;

public class Sage extends PlayableClass
{
    // name of the race
    public Sage(Main main)
    {
        super("Sage", ChatColor.LIGHT_PURPLE, Material.STICK, List.of(ChatColor.AQUA + "Uses spells and brews potions to heal, buff, and debuff."), new TraitTree(List.of(
                new Node(new Vector2d(2,5), List.of(new GreaterCapacity_1(main)), "000"),
                new Node(new Vector2d(2,6), List.of(new ConjureLightning(main)), "000"),
                new Node(new Vector2d(4,5), List.of(new PowerOfTheOakQueen(main)), "000"),
                new Node(new Vector2d(5,5), List.of(new EmpoweredMixture_1(main)), "001"),
                new Node(new Vector2d(3,3), List.of(new WeakeningSiphon(main)), "000"),
                new Node(new Vector2d(2,4), List.of(new BoostedHealing_speed(main)), "000"),
                new Node(new Vector2d(2,2), List.of(new GreaterMinorHealing(main)), "000"),
                new Node(new Vector2d(3,4), List.of(new RebukeOfTheFlame(main)), "000"),
                new Node(new Vector2d(4,4), List.of(new PotentRebuke(main)), "000"),
                new Node(new Vector2d(3,1), List.of(new GreaterCapacity_1(main)), "001"),
                new Node(new Vector2d(5,1), List.of(new EnhancedMixture_1(main), new EnhancedMixture_2(main)), "000"),
                new Node(new Vector2d(4,0), List.of(new ManaRegainSpeed(main)), "000"),
                new Node(new Vector2d(4,1), List.of(new MendMinorWounds(main)), "000"),
                new Node(new Vector2d(6,2), List.of(new EmpoweredMixture_1(main)), "000"),
                new Node(new Vector2d(5,2), List.of(new BreathOfTheDragons(main)), "000"),
                new Node(new Vector2d(6,3), List.of(new ReflectingBreath(main)), "000"),
                new Node(new Vector2d(1,2), List.of(new FlashOfOak(main)), "000"),
                new Node(new Vector2d(1,1), List.of(new IronOak(main)), "000"),
                new Node(new Vector2d(3,2), List.of(new AuraOfSiphoning(main)), "000")
        )));
    }
}
