package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;
import java.util.Objects;

public class PowerfulSwings extends Trait
{
    Main main;

    public PowerfulSwings(Main main) {
        // add the name and lore
        super("Powerful Swings", "powerful swings", ChatColor.AQUA, Material.IRON_AXE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Does 10% more damage with melee weapons."
        ));

        this.main = main;
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        float DAMAGE_MOD = 1.10f;

        // if the player was using an axe or its name contains the word "axe"
        if ((e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) && ((Player)e.getDamager()).getInventory().getItem(EquipmentSlot.HAND).getType() != Material.AIR)
        {
            // do AXE_DAMAGE_MOD times more damage
            e.setDamage(e.getDamage()*DAMAGE_MOD);
        }
    }
}
