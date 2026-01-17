package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class BetterHeadshots extends Trait
{
    private final NamespacedKey headshotDamageModKey = new NamespacedKey(Main.GetInstance(), "headshot_damage_mod");
    float headshotDamageMod = 0.10f;

    public BetterHeadshots() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Better Headshots", "better headshots", Material.SKELETON_SKULL, false, List.of(
                ChatColor.AQUA.toString() + "   - Makes headshots deal an additional 10% damage."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(headshotDamageModKey))
        {
            player.getPersistentDataContainer().set(headshotDamageModKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(headshotDamageModKey, PersistentDataType.FLOAT) - headshotDamageMod);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(headshotDamageModKey))
        {
            player.getPersistentDataContainer().set(headshotDamageModKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(headshotDamageModKey, PersistentDataType.FLOAT) + headshotDamageMod);
        }
        else
        {
            player.getPersistentDataContainer().set(headshotDamageModKey, PersistentDataType.FLOAT, headshotDamageMod);
        }
    }
}
