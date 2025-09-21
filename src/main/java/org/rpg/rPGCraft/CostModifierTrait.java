package org.rpg.rPGCraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.List;

public abstract class CostModifierTrait extends Trait
{
    private final int costModifier;
    private final String modifiedTraitID;

    public CostModifierTrait(String name, String name_id, int costModifier, String modifiedTraitID, ChatColor nameColor, Material iconMaterial, Main main, List<String> lore)
    {
        super(name, name_id, nameColor, iconMaterial, false, main, lore);

        this.costModifier = costModifier;
        this.modifiedTraitID = modifiedTraitID;
    }

    public String GetModifiedTraitID()
    {
        return modifiedTraitID;
    }

    public int GetCostModifier()
    {
        return costModifier;
    }
}
