package org.rpg.rPGCraft.Traits.Passive.Headshot;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class BetterHeadshots extends Trait
{
    private final NamespacedKey headshotDamageModKey = new NamespacedKey(main, "headshot_damage_mod");
    float headshotDamageMod = 0.10f;

    public BetterHeadshots(Main main) {
        // add the name and lore
        super("Better Headshots", "better headshots", ChatColor.AQUA, Material.SKELETON_SKULL, false, main, List.of(
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
