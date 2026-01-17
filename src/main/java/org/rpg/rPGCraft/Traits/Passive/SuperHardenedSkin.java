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
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class SuperHardenedSkin extends Trait
{
    private final AttributeModifier armorMod;
    private final AttributeModifier armorToughnessMod;

    public SuperHardenedSkin()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Super Hardened Skin", "super hardened skin", Material.SUGAR, false, List.of(
                ChatColor.AQUA.toString() + "   - Takes three more damage from pickaxes, and one per level of efficiency if its on a pickaxe.",
                ChatColor.AQUA.toString() + "   - Gains six base defense and two armor toughness."
        ));

        armorMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "super_hardened_skin_armor"), 6, AttributeModifier.Operation.ADD_NUMBER);
        armorToughnessMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "super_hardened_skin_armor_toughness"), 2, AttributeModifier.Operation.ADD_NUMBER);

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
