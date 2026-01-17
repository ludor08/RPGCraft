package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class SneakAttack extends Trait
{
    float damageMod = 1.5f;

    public SneakAttack() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Sneak Attack", "sneak attack", Material.REDSTONE, false, List.of(
                ChatColor.AQUA.toString() + "   - Attacking while invisible and crouching will consume invisibility, but deal 50% more."
        ));
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
