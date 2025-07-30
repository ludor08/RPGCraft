package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class ManyLegs extends Trait
{
    final float climbSpeed = 0.25f;

    public ManyLegs(Main main) {
        // add the name and lore
        super("Many Legs", "many legs", ChatColor.AQUA, Material.LEATHER_HORSE_ARMOR, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Sneak next to walls to climb them"
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        float offset = 0.25f;

        if (player.isSneaking())
        {
            for (int x = -1; x < 2; x++)
            {
                for (int z = -1; z < 2; z++)
                {
                    if (Math.abs(x) == Math.abs(z))
                    {
                        continue;
                    }

                    // if there is something other than air on one side
                    Material material = player.getWorld().getBlockAt(player.getLocation().add(new Vector(offset*x, 0, offset*z))).getType();

                    if (!material.equals(Material.AIR) && !material.equals(Material.WATER) && !material.equals(Material.KELP_PLANT) && !material.equals(Material.SEAGRASS) && !material.equals(Material.TALL_SEAGRASS))
                    {
                        Vector3d currentVelocity = player.getVelocity().toVector3d();

                        player.setVelocity(new Vector(currentVelocity.x, climbSpeed, currentVelocity.z));
                    }
                }
            }


        }
    }
}
