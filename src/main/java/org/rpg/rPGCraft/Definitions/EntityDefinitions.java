package org.rpg.rPGCraft.Definitions;

import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Entities.RPGCustomEntities.*;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.Entities.RPGEntities.CaveSpider;
import org.rpg.rPGCraft.Entities.RPGEntities.EnderDragon;
import org.rpg.rPGCraft.Entities.RPGEntities.Wither;
import org.rpg.rPGCraft.Entities.RPGEntity;
import org.rpg.rPGCraft.Main;

import java.util.HashMap;

public class EntityDefinitions
{
    private static HashMap<String, RPGEntity> entityIdMap;

    private static void AddEntityToMap(RPGEntity entity)
    {
        if (entity instanceof RPGCustomEntity customEntity)
        {
            entityIdMap.put(customEntity.GetNameID(), customEntity);
        }
        else
        {
            entityIdMap.put(entity.GetBaseEntityType().toString(), entity);
        }
    }

    public static void Initialize()
    {
        entityIdMap = new HashMap<String, RPGEntity>();

        // normal entities
        AddEntityToMap(new CaveSpider());
        AddEntityToMap(new Wither());
        AddEntityToMap(new EnderDragon());

        // Custom entities
        AddEntityToMap(new SkeletonWarrior());
        AddEntityToMap(new ZombieBrute());
        AddEntityToMap(new ZombieKing());
        AddEntityToMap(new ZombieKingVoidBombEntity());
        AddEntityToMap(new ZombieKingSummonEntity());
        AddEntityToMap(new DiscardedSentientArmament());
        AddEntityToMap(new ZombiePeasant());
        AddEntityToMap(new ZombieKingFootman());
        AddEntityToMap(new ZombieKingToxicCloudEntity());
    }

    public static RPGEntity GetRPGEntityByEntity(Entity entity)
    {
        if (entity.getPersistentDataContainer().has(MyNamespaces.CUSTOM_MOB.GetNamespacedKey(), PersistentDataType.STRING))
        {
            if (entityIdMap.containsKey(entity.getPersistentDataContainer().get(MyNamespaces.CUSTOM_MOB.GetNamespacedKey(), PersistentDataType.STRING)))
            {
                return entityIdMap.get(entity.getPersistentDataContainer().get(MyNamespaces.CUSTOM_MOB.GetNamespacedKey(), PersistentDataType.STRING));
            }
            else
            {
                Main.GetInstance().getLogger().warning("Custom entity \"" + entity.getPersistentDataContainer().get(MyNamespaces.CUSTOM_MOB.GetNamespacedKey(), PersistentDataType.STRING) + "\" is not contained in entityIdMap.");
            }
        }

        if (entityIdMap.containsKey(entity.getType().toString()))
        {
            return entityIdMap.get(entity.getType().toString());
        }
        else
        {
            Main.GetInstance().getLogger().warning("Entity \"" + entity.toString() + "\" is not contained in entityIdMap.");
            return null;
        }
    }

    public static boolean HasDefinitionForEntity(Entity entity)
    {
        if (entity.getPersistentDataContainer().has(MyNamespaces.CUSTOM_MOB.GetNamespacedKey(), PersistentDataType.STRING))
        {
            if (entityIdMap.containsKey(entity.getPersistentDataContainer().get(MyNamespaces.CUSTOM_MOB.GetNamespacedKey(), PersistentDataType.STRING)))
            {
                return true;
            }
        }

        return entityIdMap.containsKey(entity.getType().toString());
    }

    public static RPGEntity GetRPGEntityByID(String name_id)
    {
        if (entityIdMap.containsKey(name_id))
        {
            return entityIdMap.get(name_id);
        }
        else
        {
            Main.GetInstance().getLogger().warning("Entity \"" + name_id + "\" is not contained in entityIdMap.");
            return null;
        }
    }

    public static boolean HasDefinitionWithID(String name_id)
    {
        return entityIdMap.containsKey(name_id);
    }

    public static HashMap<String, RPGEntity> GetEntityIdMap()
    {
        return entityIdMap;
    }
}
