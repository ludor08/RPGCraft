package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class AssassinsMixture extends Trait
{
    NamespacedKey poisonKunaiKey = new NamespacedKey(main, "kunai_poison");
    NamespacedKey poisonLevelKey = new NamespacedKey(main, "kunai_poison_level");
    NamespacedKey poisonDurationKey = new NamespacedKey(main, "kunai_poison_duration");

    int poisonLevelMod = 1;
    int poisonDurationMod = 50;

    public AssassinsMixture(Main main) {
        // add the name and lore
        super("Assassins Mixture", "assassins mixture", ChatColor.AQUA, Material.STONE_SWORD, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Increase the duration and level of Poison Kunai by 50%"
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, poisonKunaiKey);
        RPGutils.AddToNamespacedKey(player, poisonLevelKey, 0, -poisonLevelMod);
        RPGutils.AddToNamespacedKey(player, poisonDurationKey, 0, -poisonDurationMod);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, poisonKunaiKey, true);
        RPGutils.AddToNamespacedKey(player, poisonLevelKey, 0, poisonLevelMod);
        RPGutils.AddToNamespacedKey(player, poisonDurationKey, 0, poisonDurationMod);
    }
}
