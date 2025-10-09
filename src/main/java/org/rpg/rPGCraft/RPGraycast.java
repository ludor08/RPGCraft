package org.rpg.rPGCraft;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class RPGraycast
{
    public static Entity RecastForEntity(int numberOfChecks, Vector3d direction, Location location, boolean isStoppedBySolidBlocks, Entity shooter, Particle particle, int numberOfParticles)
    {
        return RecastForEntity(numberOfChecks,direction,location,isStoppedBySolidBlocks,shooter,particle,numberOfParticles, new Vector3d(0.5,0.5,0.5));
    }

    public static Entity RecastForEntity(int numberOfChecks, Vector3d direction, Location location, boolean isStoppedBySolidBlocks, Entity shooter, Particle particle, int numberOfParticles, Vector3d targetBoxSize)
    {
        Vector3d position = new Vector3d(location.getX(), location.getY(), location.getZ());

        for (int i = 0; i < numberOfChecks; i++)
        {
            // update the position
            position.add(direction); // may not be actually updating the variable

            // check if the ray has hit an entity
            List<Entity> nearbyEntities = new ArrayList<>(location.getWorld().getNearbyEntities(new Location(location.getWorld(), position.x, position.y, position.z), targetBoxSize.x, targetBoxSize.y, targetBoxSize.z).stream().toList());

            if (nearbyEntities.contains(shooter))
            {
                nearbyEntities.remove(shooter);
            }

            if (!nearbyEntities.isEmpty())
            {
                // return the first entity in nearbyEntities
                return nearbyEntities.getFirst();
            }

            // check if the block at said position is solid and the recast is stopped by solid blocks
            if (RPGutils.IsBlockCollisionAt(new Location(location.getWorld(), position.x, position.y, position.z),isStoppedBySolidBlocks))
            {
                // break out of the loop
                break;
            }

            // if this is supposed to spawn particles
            if (particle != null)
            {
                // spawn the particle
                location.getWorld().spawnParticle(particle, new Location(location.getWorld(), position.x, position.y, position.z), numberOfParticles);
            }
        }

        return null;
    }

    public static List<Entity> RecastForEntities(int numberOfChecks, Vector3d direction, Location location, boolean isStoppedBySolidBlocks, Entity shooter, Particle particle, int numberOfParticles, Vector3d collisionBox)
    {
        Vector3d position = new Vector3d(location.getX(), location.getY(), location.getZ());
        List<Entity> entities = new ArrayList<>();

        for (int i = 0; i < numberOfChecks; i++)
        {
            // update the position
            position.add(direction);

            // check if the ray has hit an entity
            List<Entity> nearbyEntities = new ArrayList<>(location.getWorld().getNearbyEntities(new Location(location.getWorld(), position.x, position.y, position.z), collisionBox.x, collisionBox.y, collisionBox.z).stream().toList());

            if (nearbyEntities.contains(shooter))
            {
                nearbyEntities.remove(shooter);
            }

            if (!nearbyEntities.isEmpty())
            {
                // return the first entity in nearbyEntities
                entities.addAll(nearbyEntities);
            }

            // check if the block at said position is solid and the recast is stopped by solid blocks
            if (RPGutils.IsBlockCollisionAt(new Location(location.getWorld(), position.x, position.y, position.z),isStoppedBySolidBlocks))
            {
                // break out of the loop
                break;
            }

            // if this is supposed to spawn particles
            if (particle != null)
            {
                // spawn the particle
                location.getWorld().spawnParticle(particle, new Location(location.getWorld(), position.x, position.y, position.z), numberOfParticles);
            }
        }

        return entities;
    }

    public static Location Recast(int numberOfChecks, Vector3d direction, Location location, boolean isStoppedBySolidBlocks, Particle particle, int numberOfParticles)
    {
        Vector3d position = new Vector3d(location.getX(), location.getY(), location.getZ());

        for (int i = 0; i < numberOfChecks; i++)
        {
            // update the position
            position.add(direction);

            // check if the block at said position is solid and the recast is stopped by solid blocks
            if (RPGutils.IsBlockCollisionAt(new Location(location.getWorld(), position.x, position.y, position.z),isStoppedBySolidBlocks))
            {
                // break out of the loop
                break;
            }

            // if this is supposed to spawn particles
            if (particle != null)
            {
                // spawn the particle
                location.getWorld().spawnParticle(particle, new Location(location.getWorld(), position.x, position.y, position.z), numberOfParticles);
            }
        }

        return new Location(location.getWorld(), position.x, position.y, position.z);
    }

    public static Location RecastForAnything(int numberOfChecks, Vector3d direction, Location location, Particle particle, int numberOfParticles)
    {
        Vector3d position = new Vector3d(location.getX(), location.getY(), location.getZ());

        for (int i = 0; i < numberOfChecks; i++)
        {
            // update the position
            position.add(direction);

            // check if the block at said position is solid and the recast is stopped by solid blocks
            if (RPGutils.IsBlockCollisionAt(new Location(location.getWorld(), position.x, position.y, position.z),true))
            {
                // break out of the loop
                break;
            }

            // check if there are any entities at the given location
            if (!location.getWorld().getNearbyEntities(new Location(location.getWorld(), position.x, position.y, position.z),0.5,0.5,0.5).isEmpty())
            {
                break;
            }

            // if this is supposed to spawn particles
            if (particle != null)
            {
                // spawn the particle
                location.getWorld().spawnParticle(particle, new Location(location.getWorld(), position.x, position.y, position.z), numberOfParticles);
            }
        }

        return new Location(location.getWorld(), position.x, position.y, position.z);
    }

    public static Location RecastForAnyBlock(int numberOfChecks, Vector3d direction, Location location, Particle particle, int numberOfParticles)
    {
        Vector3d position = new Vector3d(location.getX(), location.getY(), location.getZ());

        for (int i = 0; i < numberOfChecks; i++)
        {
            // update the position
            position.add(direction);

            // check if the block at said position is solid and the recast is stopped by solid blocks
            if (new Location(location.getWorld(), position.x, position.y, position.z).getBlock().getType() != Material.AIR)
            {
                // break out of the loop
                break;
            }

            // if this is supposed to spawn particles
            if (particle != null)
            {
                // spawn the particle
                location.getWorld().spawnParticle(particle, new Location(location.getWorld(), position.x, position.y, position.z), numberOfParticles);
            }
        }

        return new Location(location.getWorld(), position.x, position.y, position.z);
    }

    public static Location RecastUntilCollision(int numberOfChecks, Vector3d direction, Location location, Particle particle, int numberOfParticles)
    {
        Vector3d position = new Vector3d(location.getX(), location.getY(), location.getZ());

        for (int i = 0; i < numberOfChecks; i++)
        {
            // check if the block at said position is solid and the recast is stopped by solid blocks
            if (RPGutils.IsBlockCollisionAt(new Location(location.getWorld(), position.x+direction.x, position.y+direction.y, position.z+direction.z),true))
            {
                // break out of the loop
                break;
            }

            // update the position
            position.add(direction);

            // if this is supposed to spawn particles
            if (particle != null)
            {
                // spawn the particle
                location.getWorld().spawnParticle(particle, new Location(location.getWorld(), position.x, position.y, position.z), numberOfParticles);
            }
        }

        return new Location(location.getWorld(), position.x, position.y, position.z);
    }
}
