package org.rpg.rPGCraft.Entities.RPGEntities;

import org.bukkit.damage.DamageSource;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.CustomItemComponents.CustomItem;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGEntity;
import org.rpg.rPGCraft.Main;

import java.util.List;

public class CaveSpider extends RPGEntity
{
    public CaveSpider()
    {
        super(EntityType.CAVE_SPIDER, -1, -1, true, new BaseLegendaryComponent(), null);
    }

    @Override
    public List<ItemStack> GetDrops(LivingEntity entity, List<ItemStack> unmodifiedDrops, DamageSource damageSource)
    {
        LivingEntity killer = entity.getKiller();
        if (killer == null) return unmodifiedDrops;

        List<ItemStack> newLoot = new java.util.ArrayList<>(unmodifiedDrops);

        double chance = 0.15;

        int looting = killer.getEquipment().getItem(EquipmentSlot.HAND).getEnchantmentLevel(Enchantment.LOOTING);
        chance += looting * 0.01;

        if (Main.GetInstance().GetRandom().nextFloat() <= chance)
        {
            newLoot.add(CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("poison_gland")));
        }

        return newLoot;
    }
}