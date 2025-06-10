package org.rpg.rPGCraft;

import java.util.Arrays;
import java.util.List;

public class Utils
{
    public static List<String> AssembleLoreFromString(String lore)
    {
        // turn the string lore into a List
        return Arrays.stream(lore.split("\n")).toList();
    }
}
