package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Claws extends Trait
{
    Main main;

    public Claws(Main main) {
        // add the name and lore
        super("Claws", "claws", ChatColor.AQUA, Material.GHAST_TEAR, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Does 3x the damage with hands."
        ));

        this.main = main;
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        float DAMAGE_MOD = 3f;

        ItemStack weapon = ((Player) e.getDamager()).getInventory().getItem(EquipmentSlot.HAND);

        // if the player was using their hands
        if (weapon.getType().equals(Material.AIR) && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
        {
            // do DAMAGE_MOD times more damage
            e.setDamage(e.getDamage()*DAMAGE_MOD);
        }
    }
}
