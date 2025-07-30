package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class ToxicBite extends Trait
{
    public ToxicBite(Main main) {
        // add the name and lore
        super("Toxic Bite", "toxic bite", ChatColor.AQUA, Material.GHAST_TEAR, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Hands give poison two for 5 seconds."
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

    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        ItemStack weapon = ((Player) e.getDamager()).getInventory().getItem(EquipmentSlot.HAND);

        // if the player was using their hands
        if (weapon.getType().equals(Material.AIR))
        {
            // if the entity is still alive
            if (e.getDamager() instanceof LivingEntity living)
            {
                // give poison
                living.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1, true, true, true));
            }
        }
    }

    @Override
    public void OnFoodLevelChange(FoodLevelChangeEvent e)
    {

    }

    @Override
    public void OnSneak(PlayerToggleSneakEvent e)
    {

    }

    @Override
    public void OnJump(PlayerJumpEvent e)
    {

    }
}
