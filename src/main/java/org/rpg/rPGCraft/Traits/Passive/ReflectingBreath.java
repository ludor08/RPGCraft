package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class ReflectingBreath extends Trait
{
    private final NamespacedKey breathKey = new NamespacedKey(Main.GetInstance(), "breath_of_the_dragons");

    public ReflectingBreath() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Reflecting Breath", "reflecting breath", Material.POTION, true, List.of(
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
