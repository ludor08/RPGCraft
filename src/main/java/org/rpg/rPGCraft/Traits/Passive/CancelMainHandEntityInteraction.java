package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;
import java.util.Objects;

public class CancelMainHandEntityInteraction extends Trait
{
    public CancelMainHandEntityInteraction() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Cancel Main Hand Entity Interaction", "cancel main hand entity interaction", Material.BARRIER, false, List.of(
                ChatColor.AQUA.toString() + "   - Cancel all main hand entity interactions."
        ));
    }

    @Override
    public void OnClickEntity(PlayerInteractEntityEvent e)
    {
        // if they're not using their main hand
        if (!Objects.equals(e.getHand(), EquipmentSlot.HAND))
        {
            return;
        }

        // cancel the event
        e.setCancelled(true);
    }
}
