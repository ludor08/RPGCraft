package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.Entities.EntityStates.ZombieKingSummonState;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.NamespaceDefinitions;

public class ZombieKingSummonEntity extends RPGCustomEntity
{
    public ZombieKingSummonEntity()
    {
        super(EntityType.ITEM_DISPLAY, "Zombie King Summon", "zombie_king_summon", false, 0, 0, false, new BaseLegendaryComponent(), new ZombieKingSummonState());
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        ItemDisplay itemDisplay = (ItemDisplay) entity;
        itemDisplay.setItemStack(new ItemStack(Material.RED_STAINED_GLASS));
        return itemDisplay;
    }
}
