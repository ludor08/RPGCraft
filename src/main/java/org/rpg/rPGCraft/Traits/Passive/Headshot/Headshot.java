package org.rpg.rPGCraft.Traits.Passive.Headshot;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Headshot extends Trait
{
    private final NamespacedKey headshotDamageModKey = new NamespacedKey(main, "headshot_damage_mod");
    float headshotDamageMod = 1.15f;

    public Headshot(Main main) {
        // add the name and lore
        super("Headshot", "headshot", ChatColor.AQUA, Material.PLAYER_HEAD, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Projectiles that hit the general head area deals 1.15x damage. (if headshots feel inconsistent with",
                ChatColor.AQUA.toString() + "     certain mobs, please let me know)"
        ));
    }

    @Override
    public void OnShotProjectileHit(ProjectileHitEvent e)
    {
        if (e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity livingEntity)
        {
            double distance = RPGutils.getDistance(e.getEntity().getLocation(), livingEntity.getEyeLocation());

            double generalHeadSize = Math.max(e.getHitEntity().getBoundingBox().getWidthX(), e.getHitEntity().getBoundingBox().getWidthZ());

            if (distance < generalHeadSize*1.5)
            {
                ((Player)(e.getEntity().getShooter())).playSound((Player) e.getEntity().getShooter(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);

                if (e.getEntity() instanceof Arrow arrow)
                {
                    arrow.setDamage(arrow.getDamage()*headshotDamageMod);
                }
                else if (e.getEntity() instanceof Trident trident)
                {
                    trident.setDamage(trident.getDamage()*headshotDamageMod);
                }
                else if (e.getEntity() instanceof SpectralArrow spectralArrow)
                {
                    spectralArrow.setDamage(spectralArrow.getDamage()*headshotDamageMod);
                }
            }
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(headshotDamageModKey))
        {
            player.getPersistentDataContainer().set(headshotDamageModKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(headshotDamageModKey, PersistentDataType.FLOAT) - headshotDamageMod);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(headshotDamageModKey))
        {
            player.getPersistentDataContainer().set(headshotDamageModKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(headshotDamageModKey, PersistentDataType.FLOAT) + headshotDamageMod);
        }
        else
        {
            player.getPersistentDataContainer().set(headshotDamageModKey, PersistentDataType.FLOAT, headshotDamageMod);
        }
    }
}
