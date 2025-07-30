package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Wings extends Trait
{

    final float flapPower = 0.5f;

    public Wings(Main main) {
        // add the name and lore
        super("Wings", "wings", ChatColor.AQUA, Material.ELYTRA, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Sneak to flap wings and get a small burst of velocity up",
                ChatColor.AQUA.toString() + "   - No fall damage."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {

    }

    @Override
    public void OnTick(Player player) {

    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {

    }

    @Override
    public void OnRespawnBuffs(PlayerRespawnEvent e)
    {

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
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {

    }

    @Override
    public void OnFoodLevelChange(FoodLevelChangeEvent e)
    {

    }

    @Override
    public void OnSneak(PlayerToggleSneakEvent e)
    {
        if (!e.getPlayer().isSneaking() && !e.getPlayer().isFlying())
        {
            Vector3d currentVelocity = e.getPlayer().getVelocity().toVector3d();

            e.getPlayer().setVelocity(new Vector(currentVelocity.x, flapPower, currentVelocity.z));
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_BEE_POLLINATE, 0.5f,0.5f);
        }
    }

    @Override
    public void OnJump(PlayerJumpEvent e)
    {

    }
}
