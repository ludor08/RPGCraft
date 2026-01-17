package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class BloodyRetreat extends Trait
{
    public BloodyRetreat() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Bloody Retreat", "bloody retreat", Material.REDSTONE, false, List.of(
                ChatColor.AQUA.toString() + "   - Upon hitting an entity with a projectile, gain a small speed boost."
        ));
    }

    @Override
    public void OnShotProjectileHit(ProjectileHitEvent e)
    {
        if (e.getEntity().getShooter() instanceof LivingEntity living)
        {
            living.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1, true,false));
        }
    }
}
