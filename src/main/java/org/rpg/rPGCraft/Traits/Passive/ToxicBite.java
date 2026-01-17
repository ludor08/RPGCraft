package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class ToxicBite extends Trait
{
    public ToxicBite() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Toxic Bite", "toxic bite", Material.GHAST_TEAR, false, List.of(
                ChatColor.AQUA.toString() + "   - Hands give poison two for 5 seconds."
        ));
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        ItemStack weapon = ((Player) e.getDamager()).getInventory().getItem(EquipmentSlot.HAND);

        // if the player was using their hands
        if (weapon.getType().equals(Material.AIR))
        {
            // if the entity is still alive
            if (e.getEntity() instanceof LivingEntity living)
            {
                // give poison
                living.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1, true, true, true));
            }
        }
    }
}
