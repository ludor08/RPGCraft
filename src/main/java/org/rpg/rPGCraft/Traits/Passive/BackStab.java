package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class BackStab extends Trait
{
    NamespacedKey backStabDamageScalerKey = new NamespacedKey(Main.GetInstance(), "back_stab_damage_scaler");
    float baseBackStabDamageScalerMod = 1.2f;

    public BackStab() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Back Stab", "back stab", Material.IRON_SWORD, false, List.of(
                ChatColor.AQUA.toString() + "   - Does 20% more damage when back stabbing an entity."
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
            List<Entity> entities = RPGraycast.RecastForEntities(10, rotation.mul(-1), middleBackLocation, false, e.getEntity(), Particle.CRIT, 5,new Vector3d(0.5,0.5,0.5));

            // if the damager is behind the damaged
            if (entities.contains(e.getDamager()))
            {
                e.setDamage(e.getDamage()*e.getDamager().getPersistentDataContainer().get(backStabDamageScalerKey, PersistentDataType.FLOAT));
            }
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(backStabDamageScalerKey))
        {
            player.getPersistentDataContainer().set(backStabDamageScalerKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(backStabDamageScalerKey, PersistentDataType.FLOAT) - baseBackStabDamageScalerMod);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(backStabDamageScalerKey))
        {
            player.getPersistentDataContainer().set(backStabDamageScalerKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(backStabDamageScalerKey, PersistentDataType.FLOAT) + baseBackStabDamageScalerMod);
        }
        else
        {
            player.getPersistentDataContainer().set(backStabDamageScalerKey, PersistentDataType.FLOAT, baseBackStabDamageScalerMod);
        }
    }
}
