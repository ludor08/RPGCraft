package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class DiamondSkin extends Trait
{
    private final AttributeModifier armorMod;
    private final AttributeModifier armorToughnessMod;

    public DiamondSkin(Main main)
    {
        // add the name and lore
        super("Diamond Skin", "diamond skin", ChatColor.AQUA, Material.DIAMOND_CHESTPLATE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Super Hardened Skin give two more base defense amd one more armor toughness"
        ));

        armorMod = new AttributeModifier(new NamespacedKey(main, "super_hardened_skin_armor"), 2, AttributeModifier.Operation.ADD_NUMBER);
        armorToughnessMod = new AttributeModifier(new NamespacedKey(main, "super_hardened_skin_armor_toughness"), 1, AttributeModifier.Operation.ADD_NUMBER);

    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SafeAttributeAdd(Attribute.ARMOR,armorMod,player);
        RPGutils.SafeAttributeAdd(Attribute.ARMOR_TOUGHNESS,armorToughnessMod,player);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.SafeAttributeRemove(Attribute.ARMOR,armorMod,player);
        RPGutils.SafeAttributeRemove(Attribute.ARMOR_TOUGHNESS,armorToughnessMod,player);
    }
}
