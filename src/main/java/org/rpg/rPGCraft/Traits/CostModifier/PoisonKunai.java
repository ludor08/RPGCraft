package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.CostModifierTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class PoisonKunai extends CostModifierTrait
{
    NamespacedKey poisonKunaiKey = new NamespacedKey(main, "kunai_poison");
    NamespacedKey poisonLevelKey = new NamespacedKey(main, "kunai_poison_level");
    NamespacedKey poisonDurationKey = new NamespacedKey(main, "kunai_poison_duration");

    int basePoisonLevel = 2;
    int basePoisonDuration = 100;

    public PoisonKunai(Main main) {
        // add the name and lore
        super("Poison Kunai", "poison kunai", 15, "kunai", ChatColor.AQUA, Material.STONE_SWORD, main, List.of(
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
