package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.CostModifierTrait;

import java.util.List;

public class PowerfulKunai extends CostModifierTrait
{
    NamespacedKey kunaiDamageKey = new NamespacedKey(Main.GetInstance(), "kunai_damage");

    int baseKunaiDamage = 6;

    public PowerfulKunai() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Powerful Kunai", "powerful kunai", 15, "kunai", Material.STONE_SWORD, List.of(
                ChatColor.AQUA.toString() + "   - Your kunais now deal six extra damage on hit,",
                ChatColor.AQUA.toString() + "     and cost 15 more mana."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, kunaiDamageKey, 0, -baseKunaiDamage);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, kunaiDamageKey, 0, baseKunaiDamage);
    }
}
