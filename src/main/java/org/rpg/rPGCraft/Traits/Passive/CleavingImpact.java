package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class CleavingImpact extends Trait
{
    NamespacedKey cleavingImpactKey = new NamespacedKey(Main.GetInstance(), "cleaving_impact");

    public CleavingImpact() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Cleaving Impact", "cleaving impact", Material.ANVIL, false, List.of(
                ChatColor.AQUA.toString() + "   - Makes Giants Leap create an impact that deals damage based on the weapon in your main hand."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, cleavingImpactKey);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, cleavingImpactKey, true);
    }
}
