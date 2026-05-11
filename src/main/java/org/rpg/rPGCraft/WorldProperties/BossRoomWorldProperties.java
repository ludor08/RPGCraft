package org.rpg.rPGCraft.WorldProperties;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class BossRoomWorldProperties extends WorldProperties
{
    public BossRoomWorldProperties()
    {
        super("world_boss_room", new WorldCreator("world_boss_room")
                .generateStructures(false)
                .type(WorldType.FLAT)
                .generatorSettings("{\"layers\": [{\"block\": \"minecraft:air\", \"height\": 1}], \"biome\": \"the_void\"}"));
    }

    public void OnTick()
    {

    }
}
