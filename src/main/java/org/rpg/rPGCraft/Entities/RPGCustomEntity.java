package org.rpg.rPGCraft.Entities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.EntityManager;

public abstract class RPGCustomEntity extends RPGEntity
{
    private final EntityType baseEntityType;
    private final String baseName;
    private final String name_id;
    private final boolean shouldHaveGearRandomized;
    private final int level;
    private final Animation defaultAnimation;

    public RPGCustomEntity(EntityType baseEntityType, String baseName, String name_id, boolean shouldHaveGearRandomized, int level, int xpDropped, boolean shouldShowLevel, LegendaryComponent legendaryComponent, EntityState initialState, Animation defaultAnimation)
    {
        super(baseEntityType, level, xpDropped, shouldShowLevel, legendaryComponent, initialState);
        this.baseEntityType = baseEntityType;
        this.baseName = baseName;
        this.name_id = name_id;
        this.shouldHaveGearRandomized = shouldHaveGearRandomized;
        this.level = level;
        this.defaultAnimation = defaultAnimation;
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

    public Animation GetDefaultAnimation()
    {
        return defaultAnimation;
    }

    public boolean GetShouldHaveGearRandomized()
    {
        return shouldHaveGearRandomized;
    }

    public Entity SpawnCustomEntity(Location location)
    {
        Entity entity = location.getWorld().spawnEntity(location, baseEntityType, shouldHaveGearRandomized);
        entity.getPersistentDataContainer().set(MyNamespaces.CUSTOM_MOB.GetNamespacedKey(), PersistentDataType.STRING, name_id);
        entity.getPersistentDataContainer().set(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER, level);

        entity.setCustomName(GetBaseName());
        EntityManager.SetEntityLevel(entity, GetLevel(), ShouldShowLevel());

        if (defaultAnimation != null)
        {
            entity.getPersistentDataContainer().set(MyNamespaces.DEFAULT_ANIMATION.GetNamespacedKey(), PersistentDataType.STRING, defaultAnimation.GetNameID());
            EntityManager.AssignAnimation(entity, defaultAnimation);
        }

        if (GetInitialState() != null)
        {
            SetStateOfEntity(entity, GetInitialState());
        }

        InitilizeCustomEntity(entity);
        OnSummon(entity);
        return entity;
    }

    public Entity InitilizeCustomEntity(Entity entity)
    {
        entity.setCustomName(GetBaseName());
        return entity;
    }
}
