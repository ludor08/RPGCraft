package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.damage.DamageSource;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.CustomItemComponents.CustomItem;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.Main;

import java.util.List;

public class ZombiePeasant extends RPGCustomEntity
{
    public ZombiePeasant()
    {
        super(EntityType.ZOMBIE, "Zombie Peasant", "zombie_peasant", false, 5, 0, true, new BaseLegendaryComponent(), null, null);
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        Zombie zombie = (Zombie) entity;

        zombie.setCustomName(GetBaseName());
        EntityManager.SetEntityLevel(entity, GetLevel(), ShouldShowLevel());

        zombie.getEquipment().setItem(EquipmentSlot.HEAD, new ItemStack(Material.LEATHER_HELMET));
        zombie.getEquipment().setItem(EquipmentSlot.HAND, new ItemStack(Material.WOODEN_HOE));

        return entity;
    }

    @Override
    public List<ItemStack> GetDrops(LivingEntity entity, List<ItemStack> unmodifiedDrops, DamageSource damageSource)
    {
        return List.of();
    }
}
