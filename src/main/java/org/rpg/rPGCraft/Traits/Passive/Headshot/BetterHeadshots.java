package org.rpg.rPGCraft.Traits.Passive.Headshot;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class BetterHeadshots extends Trait
{
    float headshotDamageMod = 1.10f;

    public BetterHeadshots(Main main) {
        // add the name and lore
        super("Better Headshots", "better headshots", ChatColor.AQUA, Material.SKELETON_SKULL, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes headshots deal an additional 10% damage."
        ));
    }

    @Override
    public void OnShootProjectileHit(ProjectileHitEvent e)
    {
        if (e.getHitEntity() != null)
        {
            double distance = RPGutils.getDistance(e.getEntity().getLocation(), new Location(e.getHitEntity().getWorld(), e.getHitEntity().getX(), e.getHitEntity().getY()+(e.getHitEntity().getHeight()*0.9), e.getHitEntity().getZ()));

            double generalHeadSize = Math.max(e.getHitEntity().getBoundingBox().getWidthX(), e.getHitEntity().getBoundingBox().getWidthZ());

            if (distance < generalHeadSize*1.5)
            {
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
}
