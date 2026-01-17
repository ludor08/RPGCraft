package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Traits.CostModifierTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class PoisonKunai extends CostModifierTrait
{
    NamespacedKey poisonKunaiKey = new NamespacedKey(Main.GetInstance(), "kunai_poison");
    NamespacedKey poisonLevelKey = new NamespacedKey(Main.GetInstance(), "kunai_poison_level");
    NamespacedKey poisonDurationKey = new NamespacedKey(Main.GetInstance(), "kunai_poison_duration");

    int basePoisonLevel = 2;
    int basePoisonDuration = 100;

    public PoisonKunai() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Poison Kunai", "poison kunai", 15, "kunai", Material.STONE_SWORD, List.of(
                ChatColor.AQUA.toString() + "   - Your kunais now apply poison on hit,",
                ChatColor.AQUA.toString() + "     and cost 15 more mana."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, poisonKunaiKey);
        RPGutils.AddToNamespacedKey(player, poisonLevelKey, 0, -basePoisonLevel);
        RPGutils.AddToNamespacedKey(player, poisonDurationKey, 0, -basePoisonDuration);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, poisonKunaiKey, true);
        RPGutils.AddToNamespacedKey(player, poisonLevelKey, 0, basePoisonLevel);
        RPGutils.AddToNamespacedKey(player, poisonDurationKey, 0, basePoisonDuration);
    }
}
