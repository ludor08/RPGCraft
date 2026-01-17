package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class BetterSmokeBomb extends Trait
{
    NamespacedKey durationKey = new NamespacedKey(Main.GetInstance(), "smoke_bomb_duration");
    int addedDuration = 100;

    public BetterSmokeBomb()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Better Smoke Bomb", "better smoke bomb", Material.GUNPOWDER, false, List.of(
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


