package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class GrapplingHookArrow extends ActiveTrait
{
    NamespacedKey grapplingSpeedReduction = new NamespacedKey(Main.GetInstance(), "grappling_hook_speed_reduction");
    NamespacedKey noFallDamageTime = new NamespacedKey(Main.GetInstance(), "grappling_hook_no_fall_damage_time");

    NamespacedKey utilityArrowKey = new NamespacedKey(Main.GetInstance(), "utility_arrow");

    public GrapplingHookArrow() {
        // add the name and lore
        super(ChatColor.RED + ChatColor.BOLD.toString() + "Grappling Hook Arrow", "grappling hook arrow", 40, Material.LEAD, true, List.of(
                ChatColor.AQUA.toString() + "   - Upon activating this trait while holding a bow or crossbow, you will fire",
                ChatColor.AQUA.toString() + "     an arrow that upon hitting a block or entity will launch towards what it hit."
        ));


    }

    @Override
    public String GetInputSequence()
    {
        return "000";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        if (player.getInventory().getItem(EquipmentSlot.HAND).getType().equals(Material.BOW) || player.getInventory().getItem(EquipmentSlot.HAND).getType().equals(Material.CROSSBOW))
        {
            Arrow arrow = player.launchProjectile(Arrow.class);
            arrow.getPersistentDataContainer().set(grapplingSpeedReduction, PersistentDataType.FLOAT, 2f);
            arrow.getPersistentDataContainer().set(utilityArrowKey, PersistentDataType.BOOLEAN, true);
        }
        else
        {
            player.sendMessage("You must be using a bow or crossbow to use this trait.");
            player.getPersistentDataContainer().set(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER) + GetCost());
        }
    }

    @Override
    public void OnShotProjectileHit(ProjectileHitEvent e)
    {
        if (e.getEntity().getPersistentDataContainer().has(grapplingSpeedReduction))
        {
            Player player = ((Player)e.getEntity().getShooter());

            if (player.getPersistentDataContainer().has(new NamespacedKey(Main.GetInstance(), "teleporting_grapple")))
            {
                Location teleLocation = e.getEntity().getLocation();
                teleLocation.setPitch(player.getPitch());
                teleLocation.setYaw(player.getYaw());

                player.teleport(teleLocation);

                RPGparticles.SpawnParticle(50, player.getLocation().add(0, player.getHeight()/2, 0), new Vector3d(player.getWidth()/2, player.getHeight()/2, player.getWidth()/2), Particle.ENCHANT, 0);
            }
            else
            {
                Vector3d direction = RPGutils.getDirection(e.getEntity().getLocation(), player.getLocation());
                direction.div(2);
                direction.mul(RPGutils.getDistance(player.getLocation(), e.getEntity().getLocation())/e.getEntity().getPersistentDataContainer().get(grapplingSpeedReduction, PersistentDataType.FLOAT));

                player.setVelocity(Vector.fromJOML(direction));
                player.getPersistentDataContainer().set(noFallDamageTime, PersistentDataType.INTEGER, 50);
            }

            e.setCancelled(true);
            e.getEntity().remove();
        }
    }



    @Override
    public void OnTick(Player player)
    {
        if (player.getPersistentDataContainer().has(noFallDamageTime))
        {
            player.getPersistentDataContainer().set(noFallDamageTime, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(noFallDamageTime, PersistentDataType.INTEGER)-1);

            if (player.getPersistentDataContainer().get(noFallDamageTime, PersistentDataType.INTEGER) <= 0)
            {
                player.getPersistentDataContainer().remove(noFallDamageTime);
            }
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(noFallDamageTime))
        {
            player.getPersistentDataContainer().remove(noFallDamageTime);
        }
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            if (e.getEntity().getPersistentDataContainer().has(noFallDamageTime))
            {
                e.setCancelled(true);
                e.getEntity().getPersistentDataContainer().remove(noFallDamageTime);
            }

        }
    }
}
