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
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class SuperHardenedSkin extends Trait
{
    private final AttributeModifier armorMod;
    private final AttributeModifier armorToughnessMod;

    public SuperHardenedSkin(Main main)
    {
        // add the name and lore
        super("Super Hardened Skin", "super hardened skin", ChatColor.AQUA, Material.SUGAR, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Takes three more damage from pickaxes, and one per level of efficiency if its on a pickaxe.",
                ChatColor.AQUA.toString() + "   - Gains six base defense and two armor toughness."
        ));

        armorMod = new AttributeModifier(new NamespacedKey(main, "super_hardened_skin_armor"), 6, AttributeModifier.Operation.ADD_NUMBER);
        armorToughnessMod = new AttributeModifier(new NamespacedKey(main, "super_hardened_skin_armor_toughness"), 2, AttributeModifier.Operation.ADD_NUMBER);

    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        SafeAttributeAdd(Attribute.ARMOR,armorMod,player);
        SafeAttributeAdd(Attribute.ARMOR_TOUGHNESS,armorToughnessMod,player);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        SafeAttributeRemove(Attribute.ARMOR,armorMod,player);
        SafeAttributeRemove(Attribute.ARMOR_TOUGHNESS,armorToughnessMod,player);
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        EntityDamageEvent.DamageCause damageCause = e.getCause();

        // the damage is coming from an entity
        if ((damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) || damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)))
        {
            ItemStack weapon = ((LivingEntity)e.getDamageSource().getCausingEntity()).getEquipment().getItem(EquipmentSlot.HAND);

            // if the weapon was a pickaxe
            if (weapon.getType() == Material.WOODEN_PICKAXE || weapon.getType() == Material.STONE_PICKAXE || weapon.getType() == Material.IRON_PICKAXE || weapon.getType() == Material.GOLDEN_PICKAXE || weapon.getType() == Material.DIAMOND_PICKAXE || weapon.getType() == Material.NETHERITE_PICKAXE)
            {
                // deal three more damage
                e.setDamage(e.getDamage()+3);

                // if the pickaxe has efficiency
                if (weapon.getItemMeta().getEnchants().containsKey(Enchantment.EFFICIENCY))
                {
                    e.setDamage(e.getDamage() + (weapon.getItemMeta().getEnchants().get(Enchantment.EFFICIENCY).byteValue() + 1));
                }
            }
        }
    }
}
