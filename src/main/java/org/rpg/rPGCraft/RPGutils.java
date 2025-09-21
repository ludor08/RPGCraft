package org.rpg.rPGCraft;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class RPGutils
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
            if (IsBlockCollisionAt(new Location(location.getWorld(), position.x, position.y, position.z),isStoppedBySolidBlocks))
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
            if (IsBlockCollisionAt(new Location(location.getWorld(), position.x, position.y, position.z),isStoppedBySolidBlocks))
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
            if (IsBlockCollisionAt(new Location(location.getWorld(), position.x, position.y, position.z),isStoppedBySolidBlocks))
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
            if (IsBlockCollisionAt(new Location(location.getWorld(), position.x, position.y, position.z),true))
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
            if (IsBlockCollisionAt(new Location(location.getWorld(), position.x+direction.x, position.y+direction.y, position.z+direction.z),true))
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

    public static void SafeAttributeAdd(Attribute attribute, AttributeModifier attributeModifier, Player player)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            // remove the old attributeModifier
            player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

            // create a new AttributeModifier with the same key and the amount added together and add it
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), attributeModifierWithKey.getAmount() + attributeModifier.getAmount(), AttributeModifier.Operation.ADD_NUMBER));
        }
        else
        {
            // add the attribute modifier normally
            player.getAttribute(attribute).addModifier(attributeModifier);
        }
    }

    public static void SafeAttributeAdd(Attribute attribute, AttributeModifier attributeModifier, Player player, float max)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            // remove the old attributeModifier
            player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

            // create a new AttributeModifier with the same key and the amount added together and add it
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.min(attributeModifierWithKey.getAmount() + attributeModifier.getAmount(), max), AttributeModifier.Operation.ADD_NUMBER));
        }
        else
        {
            // add the attribute modifier normally
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.min(attributeModifier.getAmount(), max), AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    public static void SafeAttributeAdd(Attribute attribute, AttributeModifier attributeModifier, Player player, int max)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            // remove the old attributeModifier
            player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

            // create a new AttributeModifier with the same key and the amount added together and add it
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.min(attributeModifierWithKey.getAmount() + attributeModifier.getAmount(), max), AttributeModifier.Operation.ADD_NUMBER));
        }
        else
        {
            // add the attribute modifier normally
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.min(attributeModifier.getAmount(), max), AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    public static void SafeAttributeRemove(Attribute attribute, AttributeModifier attributeModifier, Player player)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            if (!attributeModifierWithKey.equals(attributeModifier))
            {
                // remove the old attributeModifier
                player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

                // create a new AttributeModifier with the same key and the amount added together and add it
                player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), attributeModifierWithKey.getAmount() - attributeModifier.getAmount(), AttributeModifier.Operation.ADD_NUMBER));
            }
            else
            {
                // add the attribute modifier normally
                player.getAttribute(attribute).removeModifier(attributeModifier);
            }
        }
    }

    public static void SafeAttributeRemove(Attribute attribute, AttributeModifier attributeModifier, Player player, float min)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            // remove the old attributeModifier
            player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

            // create a new AttributeModifier with the same key and the amount added together and add it
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.max(attributeModifierWithKey.getAmount() - attributeModifier.getAmount(), min), AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    public static void SafeAttributeRemove(Attribute attribute, AttributeModifier attributeModifier, Player player, int min)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            // remove the old attributeModifier
            player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

            // create a new AttributeModifier with the same key and the amount added together and add it
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.max(attributeModifierWithKey.getAmount() - attributeModifier.getAmount(), min), AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    public static double getDistance(Location location1, Location location2)
    {
        Vector3d baseDistances = new Vector3d(Math.abs(location1.getX() - location2.getX()), Math.abs(location1.getY() - location2.getY()), Math.abs(location1.getZ() - location2.getZ()));
        double realDistance = Math.sqrt((Math.pow(baseDistances.x,2) + Math.pow(baseDistances.z,2)) + Math.pow(baseDistances.y,2));
        return realDistance;
    }

    public static List<Entity> SortEntityListByDistance(List<Entity> entities, Location location)
    {
        List<Entity> sortedList = new ArrayList<>();

        for (Entity entity : entities)
        {
            // the distance of the entity from the location
            double distance = getDistance(entity.getLocation(),location);

            // if there is an item in the list
            if (!sortedList.isEmpty())
            {
                // go the sorted list
                for (int i = 0; i < sortedList.size(); i++)
                {
                    // if the new entity is closer than the old entity, add the new entity at the old entity's spot
                    if (distance < getDistance(sortedList.get(i).getLocation(),location))
                    {
                        sortedList.add(i, entity);
                        break;
                    }
                }

                // if entity hasn't been added yet, add it to the end
                if (!sortedList.contains(entity))
                {
                    sortedList.add(entity);
                }
            }
            else
            {
                sortedList.add(entity);
            }
        }
        return sortedList;
    }

    public static @NotNull Vector3d getDirection(Location startLocation, Location endLocation)
    {
        return new Vector3d((startLocation.getX() - endLocation.getX()), startLocation.getY() - endLocation.getY(), (startLocation.getZ() - endLocation.getZ())).normalize();
    }

    public static @NotNull Vector3d getFacingDirection(Entity entity) {
        return new Vector3d(-Math.cos(Math.toRadians(entity.getPitch())) * Math.sin(Math.toRadians(entity.getYaw())), -Math.sin(Math.toRadians(entity.getPitch())), Math.cos(Math.toRadians(entity.getPitch())) * Math.cos(Math.toRadians(entity.getYaw())));
    }

    public static Block GetBlockCollisionAt(Location location)
    {
        RayTraceResult result = location.getWorld().rayTraceBlocks(location, new Vector(0,0.0000000001,0),1, FluidCollisionMode.ALWAYS);

        if (result != null) return location.getWorld().rayTraceBlocks(location, new Vector(0,0.0000000001,0),1, FluidCollisionMode.ALWAYS).getHitBlock();
        else return null;
    }

    public static boolean IsBlockCollisionAt(Location location, boolean onlySolidBlockCollisions)
    {
        Block block = GetBlockCollisionAt(location);

        if (block != null)
        {
            if (block.isSolid())
            {
                return true;
            }
            else if (!onlySolidBlockCollisions)
            {
                return true;
            }
        }

        return false;
    }

    public static void HealWithTraits(Entity healer, LivingEntity target, int value, EntityRegainHealthEvent.RegainReason regainReason, Main main)
    {
        NamespacedKey boostedHealingDurationKey = new NamespacedKey(main,"boostedHealing_speed_duration");
        NamespacedKey boostedHealingPowerKey = new NamespacedKey(main,"boostedHealing_speed_power");

        int amountToBeHealed = value;

        if (healer.getPersistentDataContainer().has(boostedHealingDurationKey) && healer.getPersistentDataContainer().has(boostedHealingPowerKey))
        {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, healer.getPersistentDataContainer().get(boostedHealingDurationKey, PersistentDataType.INTEGER),healer.getPersistentDataContainer().get(boostedHealingPowerKey, PersistentDataType.INTEGER)));
        }

        target.heal(amountToBeHealed, regainReason);
    }

}
