package org.rpg.rPGCraft.Entities.LegendaryComponents;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.rpg.rPGCraft.Entities.LegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGEntity;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.NamespaceDefinitions;

import java.util.List;

public class BaseLegendaryComponent extends LegendaryComponent
{
    public BaseLegendaryComponent()
    {
        super(
                0.01f,
                RPGEntity.ConstructAttributeHashMap(List.of(Attribute.MAX_HEALTH, Attribute.ATTACK_DAMAGE), List.of(new AttributeModifier(NamespaceDefinitions.GetLegendaryMobAttributeKey(), 4d, AttributeModifier.Operation.ADD_SCALAR), new AttributeModifier(NamespaceDefinitions.GetLegendaryMobAttributeKey(), 4d, AttributeModifier.Operation.ADD_SCALAR))),
                4,
                4
        );
    }
}
