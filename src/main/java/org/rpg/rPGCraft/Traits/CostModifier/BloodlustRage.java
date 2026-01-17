package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Traits.CostModifierTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class BloodlustRage extends CostModifierTrait
{
    NamespacedKey bloodlustRageKey = new NamespacedKey(Main.GetInstance(), "bloodlust_rage");

    public BloodlustRage() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Bloodlust Rage", "bloodlust rage", 15, "oath of rage", Material.LEAD, List.of(
                ChatColor.AQUA.toString() + "   - Adds a 10% life stealing effect while raging.",
                ChatColor.AQUA.toString() + "   - Makes Oath of Rage cost 10 more mana."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, bloodlustRageKey);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, bloodlustRageKey, true);
    }
}
