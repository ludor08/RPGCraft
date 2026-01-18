package org.rpg.rPGCraft.CustomItemComponents;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.rpg.rPGCraft.Main;

public class ItemEnchantment
{
    private final Enchantment enchantment;
    private final int level;

    public ItemEnchantment(Enchantment enchantment, int level)
    {
        this.enchantment = enchantment;

        this.level = level;
    }

    public Enchantment GetEnchantment()
    {
        return enchantment;
    }

    public int GetLevel()
    {
        return level;
    }
}
