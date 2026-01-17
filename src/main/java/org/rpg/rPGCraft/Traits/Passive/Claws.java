package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class Claws extends Trait
{
    public Claws() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Claws", "claws", Material.GHAST_TEAR, false, List.of(
                ChatColor.AQUA.toString() + "   - Does 3x the damage with hands."
        ));
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
