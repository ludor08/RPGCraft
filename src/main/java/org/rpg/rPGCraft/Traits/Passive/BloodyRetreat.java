package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;
import java.util.Objects;

public class BloodyRetreat extends Trait
{
    public BloodyRetreat(Main main) {
        // add the name and lore
        super("Bloody Retreat", "bloody retreat", ChatColor.AQUA, Material.REDSTONE, false, main, List.of(
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
