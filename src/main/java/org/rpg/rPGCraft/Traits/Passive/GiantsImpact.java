package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class GiantsImpact extends Trait
{
    private final NamespacedKey giantsImpactKey = new NamespacedKey(Main.GetInstance(), "giants_impact");

    public GiantsImpact() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Giants Impact", "giants impact", Material.ANVIL, false, List.of(
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
