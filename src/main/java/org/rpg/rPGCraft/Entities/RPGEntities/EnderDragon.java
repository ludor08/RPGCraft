package org.rpg.rPGCraft.Entities.RPGEntities;

import org.bukkit.entity.EntityType;
import org.rpg.rPGCraft.Entities.LegendaryComponents.EnderDragonLegendaryComponent;
import org.rpg.rPGCraft.Entities.LegendaryComponents.WitherLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGEntity;

public class EnderDragon extends RPGEntity
{
    public EnderDragon()
    {
        super(EntityType.ENDER_DRAGON, 100, 1400, true, new EnderDragonLegendaryComponent(), null);
    }
}