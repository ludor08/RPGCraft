package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;
import java.util.Objects;

public class CancelOffHandBlockInteraction extends Trait
{
    public CancelOffHandBlockInteraction() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Cancel Main Hand Block Interaction", "cancel off hand block interaction", Material.BARRIER, false, List.of(
                ChatColor.AQUA.toString() + "   - Cancel all off off hand block interactions."
        ));
    }

    @Override
    public void OnClick(PlayerInteractEvent e)
    {
        // if the action was not a left click
        if (!e.getAction().isRightClick())
        {
            return;
        }

        // if they're not using their offhand
        if (!Objects.equals(e.getHand(), EquipmentSlot.OFF_HAND))
        {
            return;
        }

        // cancel the event
        e.setCancelled(true);
    }
}
