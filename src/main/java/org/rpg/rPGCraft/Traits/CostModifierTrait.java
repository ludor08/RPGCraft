package org.rpg.rPGCraft.Traits;

import org.bukkit.Material;

import java.util.List;

public abstract class CostModifierTrait extends Trait
{
    private final int costModifier;
    private final String modifiedTraitID;

    public CostModifierTrait(String name, String name_id, int costModifier, String modifiedTraitID, Material iconMaterial, List<String> lore)
    {
        super(name, name_id, iconMaterial, false, lore);

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
