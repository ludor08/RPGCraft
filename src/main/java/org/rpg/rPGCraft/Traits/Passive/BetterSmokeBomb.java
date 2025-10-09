package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class BetterSmokeBomb extends Trait
{
    NamespacedKey durationKey = new NamespacedKey(main, "smoke_bomb_duration");
    int addedDuration = 100;

    public BetterSmokeBomb(Main main)
    {
        // add the name and lore
        super("Better Smoke Bomb", "better smoke bomb", ChatColor.AQUA, Material.GUNPOWDER, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Adds five more seconds to the duration of smoke bomb."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(durationKey))
        {
            player.getPersistentDataContainer().set(durationKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(durationKey, PersistentDataType.INTEGER) - addedDuration);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(durationKey))
        {
            player.getPersistentDataContainer().set(durationKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(durationKey, PersistentDataType.INTEGER) + addedDuration);
        }
        else
        {
            player.getPersistentDataContainer().set(durationKey, PersistentDataType.INTEGER, addedDuration);
        }
    }
}


