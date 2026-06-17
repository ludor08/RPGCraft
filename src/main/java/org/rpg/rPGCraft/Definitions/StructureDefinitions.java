package org.rpg.rPGCraft.Definitions;

import org.bukkit.Bukkit;
import org.bukkit.structure.StructureManager;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGStructure;
import org.rpg.rPGCraft.Structures.ZombieKingArena;
import org.rpg.rPGCraft.Structures.TestStructure;

import java.io.File;
import java.util.HashMap;

public class StructureDefinitions
{
    private static HashMap<String, RPGStructure> RPGStructureIdMap;

    private static void AddRPGStructureToMap(RPGStructure rpgStructure)
    {
        RPGStructureIdMap.put(rpgStructure.GetNameID(), rpgStructure);
        Main.GetInstance().saveResource("structures/" + rpgStructure.GetNameID() + ".nbt", true);
    }

    public static void Initialize()
    {
        RPGStructureIdMap = new HashMap<String, RPGStructure>();

        AddRPGStructureToMap(new TestStructure());
        AddRPGStructureToMap(new ZombieKingArena());
    }

    public static boolean HasDefinitionForRPGStructure(String name_id)
    {
        return RPGStructureIdMap.containsKey(name_id);
    }

    public static RPGStructure GetRPGStructureByID(String name_id)
    {
        if (RPGStructureIdMap.containsKey(name_id))
        {
            return RPGStructureIdMap.get(name_id);
        }
        else
        {
            Main.GetInstance().getLogger().warning("Structure \"" + name_id + "\" is not contained in structureIdMap.");
            return null;
        }
    }

    public static File GetStructureFileByID(String name_id)
    {
        File structuresFolder = Main.GetInstance().worldManager.GetStructuresFolder();
        File structureFile = new File(structuresFolder, name_id + ".nbt");

        if (structureFile.exists())
        {
            structureFile.setReadable(true);
            return structureFile;
        }
        else
        {
            Bukkit.getLogger().warning("No loaded structure file with ID \"" + name_id + "\" could be found.");
        }

        return null;
    }
}
