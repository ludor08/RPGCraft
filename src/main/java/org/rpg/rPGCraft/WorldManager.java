package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.rpg.rPGCraft.WorldProperties.*;

import java.io.File;
import java.util.HashMap;

public class WorldManager
{
    private HashMap<World, WorldProperties> worldHashMap;
    private final File structuresFolder;

    private void AddWorldToMap(WorldProperties worldProperties)
    {
        WorldCreator creator;

        if (worldProperties.GetWorldCreator() != null)
        {
            creator = worldProperties.GetWorldCreator();
        } else
        {
            creator = new WorldCreator(worldProperties.GetNameID());
        }

        World world = Bukkit.createWorld(creator);

        worldProperties.SetWorld(world);
        
        worldHashMap.put(world, worldProperties);
    }

    public void InitializeHashMap()
    {
        worldHashMap = new HashMap<>();

        AddWorldToMap(new OverWorldProperties());
        AddWorldToMap(new NetherWorldProperties());
        AddWorldToMap(new EndWorldProperties());
        AddWorldToMap(new BossRoomWorldProperties());
    }

    public World GetBossWorld()
    {
        return Bukkit.getWorld("world_boss_room");
    }

    public File GetStructuresFolder()
    {
        return structuresFolder;
    }

    public WorldManager()
    {
        // Load structures file
        structuresFolder = new File(Main.GetInstance().getDataFolder(), "Structures");

        if (!structuresFolder.exists())
        {
            structuresFolder.mkdir();
        }

        InitializeHashMap();
    }

    public void OnTick()
    {
        for (World world : worldHashMap.keySet())
        {
            worldHashMap.get(world).OnTick();
        }
    }
}
