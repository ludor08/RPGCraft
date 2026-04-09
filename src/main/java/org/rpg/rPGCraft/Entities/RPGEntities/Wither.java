package org.rpg.rPGCraft.Entities.RPGEntities;

import org.bukkit.entity.EntityType;
import org.rpg.rPGCraft.Entities.LegendaryComponents.WitherLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGEntity;

public class Wither extends RPGEntity
{
    public Wither()
    {
        super(EntityType.WITHER, 100, 1400, true, new WitherLegendaryComponent(), null);
    }
}