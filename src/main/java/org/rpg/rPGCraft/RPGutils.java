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
    // attribute

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

    // spatial

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

    // traits

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

    // namespacedKeys

    public static void RemoveNamespacedKey(Entity entity, NamespacedKey namespacedKey)
    {
        if (entity.getPersistentDataContainer().has(namespacedKey))
        {
            entity.getPersistentDataContainer().remove(namespacedKey);
        }
    }

    public static void SetNamespacedKeyValue(Entity entity, NamespacedKey namespacedKey, boolean bool)
    {
        if (!entity.getPersistentDataContainer().has(namespacedKey))
        {
            entity.getPersistentDataContainer().set(namespacedKey, PersistentDataType.BOOLEAN, bool);
        }
    }

    public static void SetNamespacedKeyValue(Entity entity, NamespacedKey namespacedKey, int value)
    {
        if (!entity.getPersistentDataContainer().has(namespacedKey))
        {
            entity.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, value);
        }
    }

    public static void AddToNamespacedKey(Entity entity, NamespacedKey namespacedKey, int baseLevel, int amountToBeAdded)
    {
        if (entity.getPersistentDataContainer().has(namespacedKey))
        {
            entity.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, entity.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER) + amountToBeAdded);
        }
        else
        {
            entity.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, baseLevel + amountToBeAdded);
        }
    }

}
