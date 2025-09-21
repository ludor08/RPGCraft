package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class HighPowerWindShot extends Trait
{
    private final NamespacedKey highPowerKey = new NamespacedKey(main, "high_power_wind_shot");

    public HighPowerWindShot(Main main) {
        // add the name and lore
        super("High Power Wind Shot", "high power wind shot", ChatColor.AQUA, Material.WIND_CHARGE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Wind Shot pushback entities."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(highPowerKey))
        {
            player.getPersistentDataContainer().remove(highPowerKey);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getPersistentDataContainer().set(highPowerKey, PersistentDataType.BOOLEAN, true);
    }
}
