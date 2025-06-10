package org.rpg.rPGCraft.Traits;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Trait;

import java.util.ArrayList;
import java.util.List;

import static org.rpg.rPGCraft.Utils.AssembleLoreFromString;

public class Fur extends Trait
{
    public Fur() {
        // add the name and lore
        super("Fur", false, null,AssembleLoreFromString(
                ChatColor.AQUA.toString() + "   - Takes less cold damage.\n" +
                ChatColor.AQUA.toString() + "   - Takes more damage from fire.\n"
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {

    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {

    }
}
