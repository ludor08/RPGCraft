package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class FlameCharge extends Trait
{
    NamespacedKey flameChargeKey = new NamespacedKey(Main.GetInstance(), "flame_charge");

    public FlameCharge() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Flame Charge", "flame charge", Material.BLAZE_POWDER, false, List.of(
                ChatColor.AQUA.toString() + "   - Makes Flame Charge light enemies aflame."
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
