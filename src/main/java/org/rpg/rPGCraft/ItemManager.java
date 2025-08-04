package org.rpg.rPGCraft;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ItemManager
{
    private final YamlConfiguration modifyVanillaFoods;

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

    public int GetVanillaFoodNutrition(Material material)
    {
        return modifyVanillaFoods.getInt("Food." + (material.toString().toUpperCase()) + ".FoodLevel");
    }

    public float GetVanillaFoodSaturation(Material material)
    {
        return (float) modifyVanillaFoods.getDouble("Food." + (material.toString().toUpperCase()) + ".Saturation");
    }
}
