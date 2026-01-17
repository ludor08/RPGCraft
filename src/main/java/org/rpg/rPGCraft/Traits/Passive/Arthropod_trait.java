package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class Arthropod_trait extends Trait
{

    public Arthropod_trait() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Arthropod", "arthropod trait", Material.FERMENTED_SPIDER_EYE, false, List.of(
                ChatColor.AQUA.toString() + "   - Takes two damage per level of Bane of Arthropods."
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        DamageCause damageCause = e.getCause();

        // the damage is coming from an entity
        if ((damageCause.equals(DamageCause.ENTITY_SWEEP_ATTACK) || damageCause.equals(DamageCause.ENTITY_ATTACK) || damageCause.equals(DamageCause.ENTITY_EXPLOSION)))
        {
            ItemStack weapon = ((LivingEntity)e.getDamageSource().getCausingEntity()).getEquipment().getItem(EquipmentSlot.HAND);

            // if the weapon has Bane of Arthropods
            if (weapon.getType() != Material.AIR && weapon.getItemMeta().getEnchants().containsKey(Enchantment.BANE_OF_ARTHROPODS))
            {
                e.setDamage(e.getDamage()+((weapon.getItemMeta().getEnchants().get(Enchantment.BANE_OF_ARTHROPODS).byteValue()+1)*2));
            }
        }
    }
}
