package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class ReflectingBreath extends Trait
{
    private final NamespacedKey breathKey = new NamespacedKey(main, "breath_of_the_dragons");

    public ReflectingBreath(Main main) {
        // add the name and lore
        super("Reflecting Breath", "reflecting breath", ChatColor.AQUA, Material.POTION, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Breath Of The Dragons reflect incoming projectiles."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        for (Entity entity : player.getWorld().getEntities())
        {
            if (entity instanceof Projectile breath && breath.getShooter() == player)
            {
                if (!breath.getPersistentDataContainer().has(breathKey))
                {
                    continue;
                }

                for (Entity entityProjectile : breath.getNearbyEntities(3,3,3))
                {
                    if (entityProjectile instanceof Projectile projectile)
                    {
                        projectile.setVelocity(new Vector(projectile.getVelocity().getX()*-1,projectile.getVelocity().getY(),projectile.getVelocity().getZ()*-1));
                    }
                }
            }
        }
    }
}
