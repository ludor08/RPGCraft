package org.rpg.rPGCraft.WorldProperties;

import org.bukkit.World;
import org.bukkit.WorldCreator;

public abstract class WorldProperties
{
    private String name_id;
    private WorldCreator creator;
    private World world;

    public WorldProperties(String name_id, WorldCreator creator)
    {
        this.name_id = name_id;
        this.creator = creator;
    }

    public void SetWorld(World world)
    {
        this.world = world;
    }

    public World GetWorld()
    {
        return world;
    }

    public String GetNameID()
    {
        return name_id;
    }

    public WorldCreator GetWorldCreator()
    {
        return creator;
    }

    public abstract void OnTick();
}
