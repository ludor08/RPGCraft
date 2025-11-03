package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.rpg.rPGCraft.CostModifierTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class BloodlustRage extends CostModifierTrait
{
    NamespacedKey bloodlustRageKey = new NamespacedKey(main, "bloodlust_rage");

    public BloodlustRage(Main main) {
        // add the name and lore
        super("Bloodlust Rage", "bloodlust rage", 15, "oath of rage", ChatColor.AQUA, Material.LEAD, main, List.of(
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
