package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class IncineratingImpact extends Trait
{
    NamespacedKey incineratingImpactKey = new NamespacedKey(Main.GetInstance(), "incinerating_impact");

    public IncineratingImpact() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Incinerating Impact", "incinerating impact", Material.FIRE_CHARGE, false, List.of(
                ChatColor.AQUA.toString() + "   - Makes Giants Leap create an impact that lights enemies alight."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, incineratingImpactKey);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, incineratingImpactKey, true);
    }
}
