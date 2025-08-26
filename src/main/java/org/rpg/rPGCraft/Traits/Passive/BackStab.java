package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class BackStab extends Trait
{
    public BackStab(Main main) {
        // add the name and lore
        super("Back Stab", "back stab", ChatColor.AQUA, Material.IRON_SWORD, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Does 30% more damage when back stabbing an entity."
        ));
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
        {
            // get the entity's rotation
            Vector3d rotation = new Vector3d(-Math.cos(Math.toRadians(0)) * Math.sin(Math.toRadians(e.getEntity().getYaw())), -Math.sin(Math.toRadians(0)), Math.cos(Math.toRadians(0)) * Math.cos(Math.toRadians(e.getEntity().getYaw())));

            // get the Location at the middle of the back
            Location middleBackLocation = new Location(e.getEntity().getWorld(), e.getEntity().getLocation().getX(), e.getEntity().getLocation().getY() + (e.getEntity().getHeight()/2), e.getEntity().getLocation().getZ());

            // get the entities behind the damaged entity
            List<Entity> entities = RPGutils.RecastForEntities(10, rotation.mul(-1), middleBackLocation, false, e.getEntity(), Particle.CRIT, 5);

            // if the damager is behind the damaged
            if (entities.contains(e.getDamager()))
            {
                e.setDamage(e.getDamage()*1.3);
            }
        }
    }
}
