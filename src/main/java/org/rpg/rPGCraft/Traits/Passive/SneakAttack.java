package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class SneakAttack extends Trait
{
    float damageMod = 1.5f;

    public SneakAttack(Main main) {
        // add the name and lore
        super("Sneak Attack", "sneak attack", ChatColor.AQUA, Material.REDSTONE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Attacking while invisible and crouching will consume invisibility, but deal 150% more."
        ));

        this.main = main;
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof LivingEntity living && living.hasPotionEffect(PotionEffectType.INVISIBILITY))
        {
            if (e.getEntity().isSneaking())
            {
                living.removePotionEffect(PotionEffectType.INVISIBILITY);
                e.setDamage(e.getDamage()*damageMod);
            }
        }
    }
}
