package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class AssassinsMixture extends Trait
{
    NamespacedKey poisonKunaiKey = new NamespacedKey(Main.GetInstance(), "kunai_poison");
    NamespacedKey poisonLevelKey = new NamespacedKey(Main.GetInstance(), "kunai_poison_level");
    NamespacedKey poisonDurationKey = new NamespacedKey(Main.GetInstance(), "kunai_poison_duration");

    int poisonLevelMod = 1;
    int poisonDurationMod = 50;

    public AssassinsMixture() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Assassins Mixture", "assassins mixture", Material.STONE_SWORD, false, List.of(
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
