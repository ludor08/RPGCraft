package org.rpg.rPGCraft.Entities;

import org.bukkit.Location;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.NamespaceDefinitions;

import java.util.List;

public abstract class RPGCustomEntity extends RPGEntity
{
    private final EntityType baseEntityType;
    private final String baseName;
    private final String name_id;
    private final boolean shouldHaveGearRandomized;
    private final int level;

    public RPGCustomEntity(EntityType baseEntityType, String baseName, String name_id, boolean shouldHaveGearRandomized, int level, int xpDropped, boolean shouldShowLevel, LegendaryComponent legendaryComponent, EntityState initialState)
    {
        super(baseEntityType, level, xpDropped, shouldShowLevel, legendaryComponent, initialState);
        this.baseEntityType = baseEntityType;
        this.baseName = baseName;
        this.name_id = name_id;
        this.shouldHaveGearRandomized = shouldHaveGearRandomized;
        this.level = level;
    }

    public EntityType GetBaseEntityType()
    {
        return baseEntityType;
    }

    public String GetNameID()
    {
        return name_id;
    }

    public String GetBaseName()
    {
        return baseName;
    }

    public boolean GetShouldHaveGearRandomized()
    {
        return shouldHaveGearRandomized;
    }

    public Entity SpawnCustomEntity(Location location)
    {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, baseEntityType, shouldHaveGearRandomized);
        entity.getPersistentDataContainer().set(NamespaceDefinitions.GetCustomMobKey(), PersistentDataType.STRING, name_id);
        entity.getPersistentDataContainer().set(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER, level);

        if (GetInitialState() != null)
        {
            entity.getPersistentDataContainer().set(NamespaceDefinitions.GetCurrentStateKey(), PersistentDataType.STRING, GetInitialState().GetStateID());
        }

        InitilizeCustomEntity(entity);
        return entity;
    }

    public Entity InitilizeCustomEntity(Entity entity)
    {
        entity.setCustomName(GetBaseName());
        return entity;
    }
}
