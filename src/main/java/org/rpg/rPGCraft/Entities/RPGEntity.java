package org.rpg.rPGCraft.Entities;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.Definitions.EntityDefinitions;
import org.rpg.rPGCraft.Definitions.StateDefinitions;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.NamespaceDefinitions;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public abstract class RPGEntity
{
    private final EntityType baseEntityType;
    private final int level;
    private final int xpDropped;
    private final boolean shouldShowLevel;
    private final LegendaryComponent legendaryComponent;
    private final EntityState initialState;

    public RPGEntity(@NotNull EntityType baseEntityType, int level, int xpDropped, boolean shouldShowLevel, LegendaryComponent legendaryComponent, EntityState initialState)
    {
        this.baseEntityType = baseEntityType;
        this.level = level;
        this.xpDropped = xpDropped;
        this.shouldShowLevel = shouldShowLevel;
        this.legendaryComponent = legendaryComponent;
        this.initialState = initialState;
    }

    public static void SetStateOfEntity(@NotNull Entity entity, EntityState newState)
    {
        if (newState != null)
        {
            entity.getPersistentDataContainer().set(NamespaceDefinitions.GetCurrentStateKey(), PersistentDataType.STRING, newState.GetStateID());
        }
        else
        {
            entity.getPersistentDataContainer().remove(NamespaceDefinitions.GetCurrentStateKey());
        }
    }

    public EntityType GetBaseEntityType()
    {
        return baseEntityType;
    }

    public int GetLevel()
    {
        if (level != -1)
        {
            return level;
        }
        else
        {
            return (int) (baseEntityType.getDefaultAttributes().getAttribute(Attribute.MAX_HEALTH).getValue()  / 4);
        }
    }

    public EntityState GetInitialState()
    {
        return initialState;
    }

    public String GetNameWithLevel(Entity entity)
    {
        // set up the custom name
        String customName = "";

        // set the name to use as the entity name
        customName += entity.getName();

        // get the level
        int level = 0;

        if (entity.getPersistentDataContainer().has(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER)) level = entity.getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER);

        customName += " [lvl:" + level + "]";

        // if the entity is legendary
        if (entity.getPersistentDataContainer().get(NamespaceDefinitions.GetLegendaryMobKey(), PersistentDataType.BOOLEAN) == true)
        {
            // make it look legendary
            customName = legendaryComponent.GetLegendaryName(customName);
        }

        // return the custom name
        return customName;
    }

    public LegendaryComponent GetLegendaryComponent()
    {
        return legendaryComponent;
    }

    public int GetXpDropped()
    {
        return xpDropped;
    }

    public boolean ShouldShowLevel()
    {
        return shouldShowLevel;
    }

    public List<ItemStack> GetDrops(LivingEntity entity, List<ItemStack> unmodifiedDrops, DamageSource damageSource)
    {
        return unmodifiedDrops;
    }

    public static boolean HasState(Entity entity)
    {
        return entity.getPersistentDataContainer().has(NamespaceDefinitions.GetCurrentStateKey());
    }

    public static EntityState GetCurrentState(Entity entity)
    {
        if (entity.getPersistentDataContainer().has(NamespaceDefinitions.GetCurrentStateKey()))
        {
            return StateDefinitions.GetStateByID(entity.getPersistentDataContainer().get(NamespaceDefinitions.GetCurrentStateKey(), PersistentDataType.STRING));
        }

        return null;
    }

    public static HashMap<Attribute, AttributeModifier> ConstructAttributeHashMap(List<Attribute> keys, List<AttributeModifier> values)
    {
        HashMap<Attribute, AttributeModifier> hashMap = new HashMap<>();

        for (int i = 0; i < keys.size(); i++)
        {
            try
            {
                hashMap.put(keys.get(i), values.get(i));
            }
            catch (IndexOutOfBoundsException e)
            {
                throw e;
            }
        }

        return hashMap;
    }
}
