package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;
import java.util.Objects;

public class CancelOffHandEntityInteraction extends Trait
{
    public CancelOffHandEntityInteraction() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Cancel Off Hand Entity Interaction", "cancel off hand entity interaction", Material.BARRIER, false, List.of(
                ChatColor.AQUA.toString() + "   - Cancel all off hand entity interactions."
        ));
    }

    @Override
    public void OnClickEntity(PlayerInteractEntityEvent e)
    {
        // if they're not using their main hand
        if (!Objects.equals(e.getHand(), EquipmentSlot.OFF_HAND))
        {
            return;
        }

        // cancel the event
        e.setCancelled(true);
    }
}
