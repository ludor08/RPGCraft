package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.NamespaceDefinitions;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;
import java.util.Objects;

public class Ricochet extends Trait
{
    float range = 5f;
    int maxRicochets = 1;

    public Ricochet() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Ricochet", "ricochet", Material.BLACK_DYE, false, List.of(
                ChatColor.AQUA.toString() + "   - Projectiles that hit blocks will rebound towards the nearest entity within five blocks.",
                ChatColor.AQUA.toString() + "   - Costs 10 mana."
        ));
    }

    @Override
    public void OnLaunchProjectile(ProjectileLaunchEvent e)
    {
        e.getEntity().getPersistentDataContainer().set(new NamespacedKey(Main.GetInstance(),"ricochet_max_ricochets"), PersistentDataType.INTEGER, maxRicochets);
    }

    @Override
    public void OnShotProjectileHit(ProjectileHitEvent e)
    {
        if (e.getEntity().getPersistentDataContainer().has(new NamespacedKey(Main.GetInstance(),"ricochet_max_ricochets")) && e.getEntity().getPersistentDataContainer().get(new NamespacedKey(Main.GetInstance(),"ricochet_max_ricochets"), PersistentDataType.INTEGER) > 0)
        {
            if (e.getHitBlock() != null)
            {
                // if the player has at least 10 mana
                if (((Player)e.getEntity().getShooter()).getPersistentDataContainer().get(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER) >= 10)
                {
                    // get the nearby entities
                    List<Entity> closeEntities = RPGutils.SortEntityListByDistance(e.getEntity().getNearbyEntities(range,range,range),e.getEntity().getLocation());

                    for (Entity target : closeEntities)
                    {
                        // the way that the arrow needs to move
                        Vector3d offset = new Vector3d(Math.floor(e.getEntity().getX())-e.getHitBlock().getX(), Math.floor(e.getEntity().getY())-e.getHitBlock().getY(),Math.floor(e.getEntity().getZ()-e.getHitBlock().getZ()));

                        // get the direction
                        Vector3d direction = RPGutils.getDirection(new Location(target.getWorld(), target.getX(), target.getY()+(target.getHeight()*0.7), target.getZ()),  e.getEntity().getLocation());

                        if (target.getSpawnCategory() == SpawnCategory.MISC && !(target instanceof Player))
                        {
                            continue;
                        }

                        if (target == e.getEntity().getShooter())
                        {
                            continue;
                        }

                        if (!Objects.equals(RPGraycast.RecastForEntity(20, direction, e.getEntity().getLocation(), true, e.getEntity(), null, 0), target))
                        {
                            continue;
                        }

                        e.setCancelled(true);

                        e.getEntity().getPersistentDataContainer().set(new NamespacedKey(Main.GetInstance(),"ricochet_max_ricochets"), PersistentDataType.INTEGER, e.getEntity().getPersistentDataContainer().get(new NamespacedKey(Main.GetInstance(),"ricochet_max_ricochets"), PersistentDataType.INTEGER)-1);
                        e.getEntity().teleport(new Location(e.getEntity().getWorld(),e.getEntity().getLocation().getX()+offset.x, e.getEntity().getLocation().getY()+offset.y, e.getEntity().getLocation().getZ()+offset.z));

                        Bukkit.getScheduler().runTaskLater(Main.GetInstance(), () -> {
                            e.getEntity().setVelocity(Vector.fromJOML(direction.mul(5)));
                        }, 2);

                        // take away the mana
                        ((Player)e.getEntity().getShooter()).getPersistentDataContainer().set(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER, ((Player)e.getEntity().getShooter()).getPersistentDataContainer().get(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER)-10);

                        break;
                    }
                }
            }
            else
            {
                e.getEntity().getPersistentDataContainer().set(new NamespacedKey(Main.GetInstance(),"ricochet_max_ricochets"), PersistentDataType.INTEGER,e.getEntity().getPersistentDataContainer().get(new NamespacedKey(Main.GetInstance(),"ricochet_max_ricochets"), PersistentDataType.INTEGER)-1);
            }
        }

    }
}
