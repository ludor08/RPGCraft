package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.EntityManager;

import java.util.List;

public class ZombieKingFootman extends RPGCustomEntity
{
    public ZombieKingFootman()
    {
        super(EntityType.ZOMBIE, "Zombie Footman", "zombie_footman", false, 10, 0, true, new BaseLegendaryComponent(), null, null);
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        Zombie zombie = (Zombie) entity;

        zombie.setCustomName(GetBaseName());
        EntityManager.SetEntityLevel(entity, GetLevel(), ShouldShowLevel());

        zombie.getEquipment().setItem(EquipmentSlot.CHEST, new ItemStack(Material.IRON_CHESTPLATE));
        zombie.getEquipment().setItem(EquipmentSlot.FEET, new ItemStack(Material.IRON_BOOTS));
        zombie.getEquipment().setItem(EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD));

        zombie.getAttribute(Attribute.MAX_HEALTH).addModifier(new AttributeModifier(MyNamespaces.CUSTOM_ENTITY_ATTRIBUTE.GetNamespacedKey(), 0.5, AttributeModifier.Operation.ADD_SCALAR));
        zombie.setHealth(zombie.getAttribute(Attribute.MAX_HEALTH).getValue());

        return entity;
    }

    @Override
    public List<ItemStack> GetDrops(LivingEntity entity, List<ItemStack> unmodifiedDrops, DamageSource damageSource)
    {
        return List.of();
    }
}
