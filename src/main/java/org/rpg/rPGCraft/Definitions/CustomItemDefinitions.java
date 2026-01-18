package org.rpg.rPGCraft.Definitions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.rpg.rPGCraft.CustomItemComponents.CustomItem;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;
import org.rpg.rPGCraft.TypeAdapters.AttributeTypeAdapter;
import org.rpg.rPGCraft.TypeAdapters.EnchantmentTypeAdapter;
import org.rpg.rPGCraft.TypeAdapters.TraitTypeAdapter;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CustomItemDefinitions
{
    private static HashMap<String, CustomItem> CustomItemIdMap;

    private static void AddCustomItemToMap(CustomItem customItem)
    {
        CustomItemIdMap.put(customItem.GetItemID(), customItem);
    }

    public static void Initialize()
    {
        Main main = Main.GetInstance();
        CustomItemIdMap = new HashMap<String, CustomItem>();

        File customItems = new File(main.getDataFolder(), "CustomItems.json");

        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeHierarchyAdapter(Trait.class, new TraitTypeAdapter());
            builder.registerTypeAdapter(Trait.class, new TraitTypeAdapter());
            builder.registerTypeHierarchyAdapter(Attribute.class, new AttributeTypeAdapter());
            builder.registerTypeHierarchyAdapter(Enchantment.class, new EnchantmentTypeAdapter());
            Gson gson = builder.create();
            Reader reader = new FileReader(customItems);

            List<CustomItem> customItemList;

            customItemList = (List<CustomItem>) gson.fromJson(reader, new TypeToken<Collection<CustomItem>>(){});
            for (CustomItem customItem : customItemList)
            {
                AddCustomItemToMap(customItem);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CustomItem GetCustomItemByID(String name_id)
    {
        if (CustomItemIdMap.containsKey(name_id))
        {
            return CustomItemIdMap.get(name_id);
        }
        else
        {
            Main.GetInstance().getLogger().warning("key \"" + name_id + "\" is not contained in customItemIdMap.");
            return null;
        }
    }

    public static HashMap<String, CustomItem> GetCustomItemIdMap()
    {
        return CustomItemIdMap;
    }
}
