package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.rpg.rPGCraft.RPGutils.Recast;

public class WeAreOne extends Trait
{
    public WeAreOne(Main main) {
        // add the name and lore
        super("We Are One", "we are one", ChatColor.AQUA, Material.SCULK_CATALYST, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Sneak left check with an empty hand when standing on any skulk blocks to teleport to other skulk blocks."
        ));
    }

    @Override
    public void OnClick(PlayerInteractEvent e)
    {
        // if the action was not a left click
        if (!e.getAction().isLeftClick())
        {
            return;
        }

        // if they are not sneaking
        if (!e.getPlayer().isSneaking())
        {
            return;
        }

        // if they're not using their main hand or their main hand is empty
        if (!Objects.equals(e.getHand(), EquipmentSlot.HAND) || e.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getType() != Material.AIR)
        {
            return;
        }

        // what the block they're looking at is
        List<Material> sculkBlocks = Arrays.asList(new Material[]{Material.SCULK_CATALYST,Material.SCULK,Material.SCULK_VEIN,Material.SCULK_SENSOR,Material.SCULK_SHRIEKER,Material.CALIBRATED_SCULK_SENSOR});

        Player player = e.getPlayer();

        Vector3d direction = new Vector3d(-Math.cos(Math.toRadians(player.getPitch())) * Math.sin(Math.toRadians(player.getYaw())), -Math.sin(Math.toRadians(player.getPitch())), Math.cos(Math.toRadians(player.getPitch())) * Math.cos(Math.toRadians(player.getYaw())));
        Location lookingAt = RPGutils.Recast(100, direction, e.getPlayer().getEyeLocation(), true, null, 0);

        // if the block is a sculk
        if (sculkBlocks.contains(lookingAt.getBlock().getType()))
        {
            // if they are standing on a sculk block
            if (sculkBlocks.contains(player.getLocation().getBlock().getType()) || sculkBlocks.contains(player.getLocation().add(0,-1,0).getBlock().getType()))
            {
                // start the teleportation
                lookingAt = lookingAt.getBlock().getLocation();
                lookingAt.setYaw(player.getYaw());
                lookingAt.setPitch(player.getPitch());

                lookingAt.add(Vector.fromJOML(new Vector3d(0.5,lookingAt.getBlock().getBoundingBox().getHeight(),0.5)));

                player.teleport(lookingAt);
            }
        }
    }
}
