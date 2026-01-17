package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class Wings extends Trait
{

    final float flapPower = 0.5f;

    public Wings() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Wings", "wings", Material.ELYTRA, false, List.of(
                ChatColor.AQUA.toString() + "   - Sneak to flap wings and get a small burst of velocity up",
                ChatColor.AQUA.toString() + "   - No fall damage."
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            e.setCancelled(true);
        }
    }

    @Override
    public void OnToggleSneak(PlayerToggleSneakEvent e)
    {
        if (e.getPlayer().isSneaking() && !e.getPlayer().isFlying())
        {
            Vector3d direction = new Vector3d(-Math.cos(Math.toRadians(e.getPlayer().getPitch())) * Math.sin(Math.toRadians(e.getPlayer().getYaw())), -Math.sin(Math.toRadians(e.getPlayer().getPitch())), Math.cos(Math.toRadians(e.getPlayer().getPitch())) * Math.cos(Math.toRadians(e.getPlayer().getYaw())));

            e.getPlayer().setVelocity(new Vector((direction.x*flapPower), flapPower, (direction.z*flapPower)));
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_BEE_POLLINATE, 0.5f,0.5f);
        }
    }
}
