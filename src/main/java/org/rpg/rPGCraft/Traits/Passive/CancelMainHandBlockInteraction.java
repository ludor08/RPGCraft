package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CancelMainHandBlockInteraction extends Trait
{
    public CancelMainHandBlockInteraction() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Cancel Main Hand Block Interaction", "cancel main hand block interaction", Material.BARRIER, false, List.of(
                ChatColor.AQUA.toString() + "   - Cancel all main hand block interactions."
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

        // if they're not using their main hand
        if (!Objects.equals(e.getHand(), EquipmentSlot.HAND))
        {
            return;
        }

        // cancel the event
        e.setCancelled(true);
    }
}
