package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class FlameCharge extends Trait
{
    NamespacedKey flameChargeKey = new NamespacedKey(main, "flame_charge");

    public FlameCharge(Main main) {
        // add the name and lore
        super("Flame Charge", "flame charge", ChatColor.AQUA, Material.BLAZE_POWDER, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Flame Charge light enemies a flame."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, flameChargeKey);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, flameChargeKey, true);
    }
}
