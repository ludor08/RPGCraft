package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;

public class ZombieBrute extends RPGCustomEntity
{
    public ZombieBrute()
    {
        super(EntityType.ZOMBIE, "Zombie Brute", "zombie_brute", false, 15, 15, true, new BaseLegendaryComponent(), null, null);
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        Zombie zombie = (Zombie) entity;
        zombie.setCustomName(GetBaseName());

        zombie.getAttribute(Attribute.SCALE).addModifier(new AttributeModifier(MyNamespaces.CUSTOM_ENTITY_ATTRIBUTE.GetNamespacedKey(), 2, AttributeModifier.Operation.ADD_SCALAR));
        zombie.getAttribute(Attribute.MAX_HEALTH).addModifier(new AttributeModifier(MyNamespaces.CUSTOM_ENTITY_ATTRIBUTE.GetNamespacedKey(), 1.5, AttributeModifier.Operation.ADD_SCALAR));
        zombie.setHealth(zombie.getAttribute(Attribute.MAX_HEALTH).getValue());

        zombie.getAttribute(Attribute.ATTACK_DAMAGE).addModifier(new AttributeModifier(MyNamespaces.CUSTOM_ENTITY_ATTRIBUTE.GetNamespacedKey(), 2, AttributeModifier.Operation.ADD_SCALAR));
        return entity;
    }
}
