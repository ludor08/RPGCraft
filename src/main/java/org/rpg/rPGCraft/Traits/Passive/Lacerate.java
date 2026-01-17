package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class Lacerate extends Trait
{
    NamespacedKey lacerate = new NamespacedKey(Main.GetInstance(), "lacerate");

    public Lacerate() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Lacerate", "lacerate", Material.REDSTONE, false, List.of(
                ChatColor.AQUA.toString() + "   - Every third combo leaves a deep wound in your opponent",
                ChatColor.AQUA.toString() + "     dealing 20% of your max health (up to 10 damage)."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, lacerate, true);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, lacerate);
    }
}
