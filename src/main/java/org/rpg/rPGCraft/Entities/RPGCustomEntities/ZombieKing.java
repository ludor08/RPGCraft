package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.LegendaryComponents.ZombieKingLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.NamespaceDefinitions;

public class ZombieKing extends RPGCustomEntity
{
    public ZombieKing()
    {
        super(EntityType.ZOMBIE, "Zombie King", "zombie_king", false, 75, 1000, true, new ZombieKingLegendaryComponent(), null);
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        Zombie zombie = (Zombie) entity;
        zombie.setCustomName(GetBaseName());

        zombie.getAttribute(Attribute.MAX_HEALTH).addModifier(new AttributeModifier(NamespaceDefinitions.GetCustomEntityAttributeKey(), 1.5, AttributeModifier.Operation.ADD_SCALAR));
        zombie.setHealth(zombie.getAttribute(Attribute.MAX_HEALTH).getValue());
        return entity;
    }
}
