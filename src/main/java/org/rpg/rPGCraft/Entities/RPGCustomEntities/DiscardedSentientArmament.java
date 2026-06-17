package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;

public class DiscardedSentientArmament extends RPGCustomEntity
{
    public DiscardedSentientArmament()
    {
        super(EntityType.VILLAGER, "Discarded Sentient Armament", "discarded_sentient_armament", false, 50, 200, true, new BaseLegendaryComponent(), EntityStates.DISCARDED_SENTIENT_ARMAMENT_IDLE.GetEntityState(), null);
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        ItemStack item = new ItemStack(Material.REDSTONE);
        ItemMeta itemMeta = item.getItemMeta();

        // add the custom model data
        itemMeta.setCustomModelData(1);

        // set the item meta
        item.setItemMeta(itemMeta);

        Villager villager = (Villager) entity;
        villager.setCustomName(GetNameWithLevel(villager, GetBaseName()));

        villager.setInvisible(true);

        villager.getAttribute(Attribute.MOVEMENT_SPEED).addModifier(new AttributeModifier(MyNamespaces.CUSTOM_ENTITY_ATTRIBUTE.GetNamespacedKey(), -0.25, AttributeModifier.Operation.ADD_NUMBER));
        //villager.getAttribute(Attribute.MAX_HEALTH).addModifier(new AttributeModifier(NamespaceDefinitions.GetCustomEntityAttributeKey(), 1.5, AttributeModifier.Operation.ADD_SCALAR));
        //villager.setHealth(zombie.getAttribute(Attribute.MAX_HEALTH).getValue());

        //zombie.getAttribute(Attribute.ATTACK_DAMAGE).addModifier(new AttributeModifier(NamespaceDefinitions.GetCustomEntityAttributeKey(), 2, AttributeModifier.Operation.ADD_SCALAR));
        return entity;
    }
}
