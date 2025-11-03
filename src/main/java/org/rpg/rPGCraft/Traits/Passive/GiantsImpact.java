package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class GiantsImpact extends Trait
{
    private final NamespacedKey giantsImpactKey = new NamespacedKey(main, "giants_impact");

    public GiantsImpact(Main main) {
        // add the name and lore
        super("Giants Impact", "giants impact", ChatColor.AQUA, Material.ANVIL, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Giants Leap create an impact that deals half the fall damage you would have taken."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, giantsImpactKey);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, giantsImpactKey, true);
    }
}
