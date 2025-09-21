package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;
import java.util.Objects;

public class CorrectiveWindResistance extends Trait
{
    float range = 10f;
    float accuracy = 2;
    
    public CorrectiveWindResistance(Main main) {
        // add the name and lore
        super("Corrective Wind Resistance", "corrective wind resistance", ChatColor.AQUA, Material.WIND_CHARGE, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Your projectiles will correct their aim towards the nearest nearby entity."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        for (Entity randomEntity : player.getWorld().getEntities())
        {
            if (randomEntity instanceof Projectile projectile && projectile.getShooter() == player)
            {
                // get the nearby entities
                List<Entity> closeEntities = RPGutils.SortEntityListByDistance(projectile.getNearbyEntities(range,range,range),projectile.getLocation());

                if (projectile.isOnGround())
                {
                    continue;
                }

                for (Entity target : closeEntities)
                {
                    if (projectile.isOnGround())
                    {
                        continue;
                    }

                    if (target instanceof LivingEntity livingTarget)
                    {
                        // get the current direction
                        Vector3d currentDirection = projectile.getVelocity().normalize().toVector3d();

                        // get the direction
                        Vector3d direction = RPGutils.getDirection(livingTarget.getEyeLocation(),  projectile.getLocation()).mul(Math.sqrt((Math.pow(currentDirection.x,2) + Math.pow(currentDirection.z,2)) + Math.pow(currentDirection.y,2)));

                        if (livingTarget.getSpawnCategory() == SpawnCategory.MISC && !(livingTarget instanceof Player))
                        {
                            continue;
                        }

                        if (livingTarget == projectile.getShooter())
                        {
                            continue;
                        }

                        if (!Objects.equals(RPGutils.RecastForEntity((int) range, direction, projectile.getLocation(), true, projectile, null, 0), livingTarget))
                        {
                            continue;
                        }

                        Vector3d correction = (direction.sub(currentDirection)).div(accuracy);
                        
                        projectile.setVelocity(Vector.fromJOML(currentDirection.add(correction)));
                        break;
                    }
                }
            }
        }
    }
}
