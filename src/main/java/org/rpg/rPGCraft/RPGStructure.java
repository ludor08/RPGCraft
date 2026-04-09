package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.structure.Structure;

import java.io.File;
import java.io.IOException;

public class RPGStructure
{
    private final String name_id;

    public RPGStructure(String name_id)
    {
        this.name_id = name_id;
    }

    public String GetNameID()
    {
        return name_id;
    }

    public void OnPlace(Location location)
    {

    }
}
