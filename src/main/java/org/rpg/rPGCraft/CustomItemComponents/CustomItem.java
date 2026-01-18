package org.rpg.rPGCraft.CustomItemComponents;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.Main;

import java.util.List;

public class CustomItem {
    private final String name;
    private final List<String> lore;

    private final Material material;

    private final int maxStackSize;

    private final String itemID;

    private final int customModelData;

    private final List<ItemEnhancement> itemEnhancements;

    private final List<ItemAttribute> itemAttributes;

    private final List<ItemEnchantment> itemEnchantments;

    private final String weaponType;

    public CustomItem(@NotNull String name, List<String> lore, @NotNull Material material, int maxStackSize, int customModelData, @NotNull String itemID, @NotNull List<ItemEnhancement> itemEnhancements, @NotNull List<ItemAttribute> itemAttributes, @NotNull List<ItemEnchantment> itemEnchantments, String weaponType)
    {
        this.name = name;
        this.lore = lore;

        this.material = material;
        this.maxStackSize = maxStackSize;

        this.itemID = itemID;
        this.customModelData = customModelData;

        this.itemEnhancements = itemEnhancements;
        this.itemAttributes = itemAttributes;
        this.itemEnchantments = itemEnchantments;
        this.weaponType = weaponType;
    }

    @NotNull
    public String GetName() { return name; }

    public List<String> GetLore() { return lore; }

    @NotNull
    public Material GetMaterial() { return material; }

    public int GetMaxStackSize() { return maxStackSize; }

    @NotNull
    public String GetItemID() { return itemID; }

    public int GetCustomModelData() { return customModelData; }

    @NotNull
    public List<ItemEnhancement> getEnchantments()
    {
        return itemEnhancements;
    }

    @NotNull
    public List<ItemAttribute> GetItemAttributes()
    {
        return itemAttributes;
    }

    @NotNull
    public List<ItemEnchantment> GetEnchantments()
    {
        return itemEnchantments;
    }

    public String GetWeaponType()
    {
        return weaponType;
    }

    public static ItemStack GetCustomItemStack(CustomItem customItem)
    {
        // generates the icon for this race
        ItemStack item = new ItemStack(customItem.GetMaterial());
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(customItem.GetName());

        // add the lore
        itemMeta.setLore(customItem.GetLore());

        // set the max stack size
        itemMeta.setMaxStackSize(customItem.GetMaxStackSize());

        // add the custom model data
        itemMeta.setCustomModelData(customItem.GetCustomModelData());

        // add the attributes
        for (ItemAttribute itemAttribute : customItem.GetItemAttributes())
        {
            itemMeta.addAttributeModifier(itemAttribute.GetAttribute(), itemAttribute.GetAttributeModifier());
        }

        // add the enchantments
        for (ItemEnchantment itemEnchantment : customItem.GetEnchantments())
        {
            itemMeta.addEnchant(itemEnchantment.GetEnchantment(), itemEnchantment.GetLevel(), true);
        }

        // set the custom weapon key
        if (customItem.GetWeaponType() != null)
        {
            itemMeta.getPersistentDataContainer().set(Main.GetInstance().GetWeaponTypeKey(), PersistentDataType.STRING, customItem.GetWeaponType());
        }

        // set the items key and value
        itemMeta.getPersistentDataContainer().set(Main.GetInstance().GetCustomItemKey(), PersistentDataType.STRING, customItem.GetItemID());

        // set the item meta
        item.setItemMeta(itemMeta);

        // return the icon
        return item;
    }

    public static ItemStack GetCustomItemStack(CustomItem customItem, int count)
    {
        ItemStack item = GetCustomItemStack(customItem);
        item.setAmount(count);

        return item;
    }

}
