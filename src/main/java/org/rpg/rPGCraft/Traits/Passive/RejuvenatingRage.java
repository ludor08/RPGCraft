package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class RejuvenatingRage extends Trait
{
    private final NamespacedKey rejuvenatingRageKey = new NamespacedKey(Main.GetInstance(), "rejuvenating_rage");

    public RejuvenatingRage() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Rejuvenating Rage", "rejuvenating rage", Material.ANVIL, false, List.of(
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
