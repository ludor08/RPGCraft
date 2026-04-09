package org.rpg.rPGCraft.CustomItemComponents;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.NamespaceDefinitions;

public class ItemAttribute
{
    private final Attribute attribute;

    private final double amount;
    private final AttributeModifier.Operation operation;
    private final EquipmentSlot equipmentSlot;

    public ItemAttribute(Attribute attribute, double amount, AttributeModifier.Operation operation, EquipmentSlot equipmentSlot)
    {
        this.attribute = attribute;

        this.amount = amount;
        this.operation = operation;
        this.equipmentSlot = equipmentSlot;
    }

    public Attribute GetAttribute()
    {
        return attribute;
    }

    public AttributeModifier GetAttributeModifier()
    {
        return new AttributeModifier(NamespaceDefinitions.GetItemAttributeKey(), amount, operation, equipmentSlot.getGroup());
    }
}
