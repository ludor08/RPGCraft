package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;

public class ZombieKingToxicCloudEntity extends RPGCustomEntity
{
    public ZombieKingToxicCloudEntity()
    {
        super(EntityType.ITEM_DISPLAY, "Toxic Cloud", "toxic_cloud", false, 0, 0, false, new BaseLegendaryComponent(), EntityStates.TOXIN_CLOUD_STATE.GetEntityState(), null);
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        ItemDisplay itemDisplay = (ItemDisplay) entity;

        return itemDisplay;
    }
}
