package org.rpg.rPGCraft;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemManager
{
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

    public ItemManager(Main main)
    {
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
    }

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
}
