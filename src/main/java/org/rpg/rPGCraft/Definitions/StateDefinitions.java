package org.rpg.rPGCraft.Definitions;

import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.Entities.EntityStates.ZombieKingSummonState;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.Entities.RPGEntity;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.NamespaceDefinitions;

import java.util.HashMap;

public class StateDefinitions
{
    private static HashMap<String, EntityState> stateIdMap;

    private static void AddStateToMap(EntityState state)
    {
        stateIdMap.put(state.GetStateID(), state);
    }

    public static void Initialize()
    {
        stateIdMap = new HashMap<String, EntityState>();

        AddStateToMap(new ZombieKingSummonState());
    }

    public static EntityState GetStateByID(String id)
    {
        return stateIdMap.get(id);
    }
}
