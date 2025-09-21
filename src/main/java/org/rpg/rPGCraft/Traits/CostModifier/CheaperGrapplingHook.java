package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.CostModifierTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class CheaperGrapplingHook extends CostModifierTrait
{
    public CheaperGrapplingHook(Main main) {
        // add the name and lore
        super("Cheaper Grappling Hook", "cheaper grappling hook", -10, "grappling hook arrow", ChatColor.AQUA, Material.LEAD, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Grappling Hook 10 mana cheaper."
        ));
    }
}
