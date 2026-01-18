package org.rpg.rPGCraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.rpg.rPGCraft.CustomItemComponents.CustomItem;
import org.rpg.rPGCraft.CustomItemComponents.ItemAttribute;
import org.rpg.rPGCraft.CustomItemComponents.ItemEnchantment;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;
import org.rpg.rPGCraft.Traits.Trait;
import org.rpg.rPGCraft.TypeAdapters.AttributeTypeAdapter;
import org.rpg.rPGCraft.TypeAdapters.EnchantmentTypeAdapter;
import org.rpg.rPGCraft.TypeAdapters.TraitTypeAdapter;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemManager
{
    Main main;

    private final YamlConfiguration modifyVanillaFoods;

    // TODO move to an item types file
    private final List<Material> meatTypes = List.of(Material.COOKED_RABBIT, Material.RABBIT, Material.COD, Material.COOKED_COD, Material.SALMON, Material.COOKED_SALMON,
            Material.TROPICAL_FISH, Material.PUFFERFISH, Material.RABBIT_STEW, Material.BEEF, Material.COOKED_BEEF, Material.PORKCHOP, Material.COOKED_PORKCHOP, Material.MUTTON, Material.COOKED_MUTTON,
            Material.CHICKEN, Material.COOKED_CHICKEN, Material.ROTTEN_FLESH);

    private final List<Material> swordTypes = List.of(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD);
    private final List<Material> axeTypes = List.of(Material.WOODEN_AXE,Material.STONE_AXE,Material.IRON_AXE,Material.GOLDEN_AXE,Material.DIAMOND_AXE,Material.NETHERITE_AXE);
    private final List<Material> bowTypes = List.of(Material.BOW,Material.CROSSBOW);
    private final List<Material> otherTypes = List.of(Material.TRIDENT,Material.MACE);
    private final List<Material> weaponTypes = Stream.of(swordTypes, axeTypes, bowTypes, otherTypes).flatMap(Collection::stream).collect(Collectors.toList());

    public ItemManager()
    {
        this.main = Main.GetInstance();

        File pluginFolder = main.getDataFolder();
        if (!pluginFolder.exists())
        {
            pluginFolder.mkdirs();
        }

        // Vanilla Foods
        File vanillaFoods = new File(main.getDataFolder(), "VanillaFoods.yml");
        if (!vanillaFoods.exists())
        {
            main.saveResource("VanillaFoods.yml", false);
        }

        modifyVanillaFoods = YamlConfiguration.loadConfiguration(vanillaFoods);

        // Custom Items
        main.saveResource("CustomItems.json", true);



        /*
        List<CustomItem> customItemList = List.of(new CustomItem("test item with enchacntment", List.of(), Material.ARROW, 125125, "test_item", List.of(), List.of(), List.of(new ItemEnchantment(Enchantment.EFFICIENCY, 2)), null));

        File customItems = new File(main.getDataFolder(), "CustomItems.json");
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeHierarchyAdapter(Trait.class, new TraitTypeAdapter());
            builder.registerTypeHierarchyAdapter(Attribute.class, new AttributeTypeAdapter());
            builder.registerTypeHierarchyAdapter(Enchantment.class, new EnchantmentTypeAdapter());
            Gson gson = builder.create();
            Writer writer = new FileWriter(customItems);

            gson.toJson(customItemList, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         //*/

    }

    /// TODO turn these into json/yml files
    public List<Material> GetSwordTypes()
    {
        return swordTypes;
    }

    public List<Material> GetMeatTypes()
    {
        return meatTypes;
    }

    public List<Material> GetAxeTypes()
    {
        return axeTypes;
    }

    public List<Material> GetBowTypes()
    {
        return bowTypes;
    }

    public List<Material> GetOtherTypes()
    {
        return otherTypes;
    }

    public List<Material> GetWeaponTypes()
    {
        return weaponTypes;
    }

    public int GetVanillaFoodNutrition(Material material)
    {
        return modifyVanillaFoods.getInt("Food." + (material.toString().toUpperCase()) + ".FoodLevel");
    }

    public float GetVanillaFoodSaturation(Material material)
    {
        return (float) modifyVanillaFoods.getDouble("Food." + (material.toString().toUpperCase()) + ".Saturation");
    }

    public boolean IsVanillaFood(Material material)
    {

         if (modifyVanillaFoods.get("Food." + (material.toString().toUpperCase())) != null)
         {
             return true;
         }
         else
         {
             return false;
         }
    }

    public ItemStack MakePotionItemStack(ItemStack itemStack, Color color, PotionType basePotionType, List<PotionEffect> potionEffects)
    {
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();

        if (color != null)
        {
            potionMeta.setColor(color);
        }

        if (basePotionType != null)
        {
            potionMeta.setBasePotionType(basePotionType);
        }

        for (PotionEffect potionEffect : potionEffects)
        {
            potionMeta.addCustomEffect(potionEffect, false);
        }

        itemStack.setItemMeta(potionMeta);
        return itemStack;
    }

}
