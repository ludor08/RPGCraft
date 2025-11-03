package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class RejuvenatingRage extends Trait
{
    private final NamespacedKey rejuvenatingRageKey = new NamespacedKey(main, "rejuvenating_rage");

    public RejuvenatingRage(Main main) {
        // add the name and lore
        super("Rejuvenating Rage", "rejuvenating rage", ChatColor.AQUA, Material.ANVIL, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Oath Of Rage give the Regeneration effect."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, rejuvenatingRageKey);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, rejuvenatingRageKey, true);
    }
}
