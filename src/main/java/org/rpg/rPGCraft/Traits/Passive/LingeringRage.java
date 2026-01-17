package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class LingeringRage extends Trait
{
    NamespacedKey rageDurationKey = new NamespacedKey(Main.GetInstance(), "rage_duration");

    public LingeringRage() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Lingering Rage", "lingering rage", Material.BLAZE_ROD, false, List.of(
                ChatColor.AQUA.toString() + "   - Make Oath of Rage last twice as long as base."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, rageDurationKey, 0, -300);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, rageDurationKey, 0, 300);
    }
}
