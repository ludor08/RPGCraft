package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class AntiGravityArrow extends Trait
{
    NamespacedKey antiGravityKey = new NamespacedKey(Main.GetInstance(), "anti_gravity");

    public AntiGravityArrow() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Anti-Gravity Arrow", "anti gravity arrow", Material.ARROW, true, List.of(
                ChatColor.AQUA.toString() + "   - Projectiles have gravity.",
                ChatColor.AQUA.toString() + "   - Projectiles launch at 50% of the normal speed."
        ));
    }

    @Override
    public void OnLaunchProjectile(ProjectileLaunchEvent e)
    {
        e.getEntity().setGravity(false);

        e.getEntity().getPersistentDataContainer().set(antiGravityKey, PersistentDataType.BOOLEAN, true);
    }

    @Override
    public void OnTick(Player player)
    {
        for (Entity entity : player.getWorld().getEntities())
        {
            if (entity instanceof Projectile projectile)
            {
                if (projectile.getShooter() == player)
                {
                    if (projectile.getPersistentDataContainer().has(antiGravityKey))
                    {
                        RPGparticles.SpawnParticle(player.getWorld(), 50, projectile.getLocation(), new Vector3d(0,0,0), Particle.ENCHANT, 0);
                    }
                }
            }
        }
    }
}
