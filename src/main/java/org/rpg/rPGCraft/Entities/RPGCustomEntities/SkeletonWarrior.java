package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.Entities.RPGEntity;
import org.rpg.rPGCraft.Main;

public class SkeletonWarrior extends RPGCustomEntity
{
    public SkeletonWarrior()
    {
        super(EntityType.SKELETON, "Skeleton Warrior", "skeleton_warrior", true, -1, -1, true, new BaseLegendaryComponent(), null);
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        Skeleton skeleton = (Skeleton) entity;
        skeleton.setCustomName(GetBaseName());

        skeleton.getEquipment().setItem(EquipmentSlot.HAND, new ItemStack(Material.STONE_SWORD));
        skeleton.getEquipment().setItem(EquipmentSlot.OFF_HAND, new ItemStack(Material.SHIELD));

        return entity;
    }
}
