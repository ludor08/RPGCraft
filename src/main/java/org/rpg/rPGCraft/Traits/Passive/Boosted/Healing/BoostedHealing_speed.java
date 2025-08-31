package org.rpg.rPGCraft.Traits.Passive.Boosted.Healing;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class BoostedHealing_speed extends Trait
{
    private final NamespacedKey boostedHealingDurationKey = new NamespacedKey(main,"boostedHealing_speed_duration");
    private final NamespacedKey boostedHealingPowerKey = new NamespacedKey(main,"boostedHealing_speed_power");

    int boostedHealingDuration = 100;
    int boostedHealingPower = 0;

    public BoostedHealing_speed(Main main)
    {
        // add the name and lore
        super("Boosted Healing:Speed", "boosted healing speed", ChatColor.AQUA, Material.SUGAR, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes all healing traits give speed one for five seconds."
        ));

    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(boostedHealingDurationKey))
        {
            player.getPersistentDataContainer().set(boostedHealingDurationKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(boostedHealingDurationKey, PersistentDataType.INTEGER) - boostedHealingDuration);

            if (player.getPersistentDataContainer().get(boostedHealingDurationKey, PersistentDataType.INTEGER) <= 0)
            {
                player.getPersistentDataContainer().remove(boostedHealingDurationKey);
            }
        }

        if (player.getPersistentDataContainer().has(boostedHealingPowerKey))
        {
            player.getPersistentDataContainer().set(boostedHealingPowerKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(boostedHealingPowerKey, PersistentDataType.INTEGER) - boostedHealingPower);

            if (player.getPersistentDataContainer().get(boostedHealingPowerKey, PersistentDataType.INTEGER) <= 0)
            {
                player.getPersistentDataContainer().remove(boostedHealingPowerKey);
            }
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(boostedHealingDurationKey))
        {
            player.getPersistentDataContainer().set(boostedHealingDurationKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(boostedHealingDurationKey, PersistentDataType.INTEGER) + boostedHealingDuration);
        }
        else
        {
            player.getPersistentDataContainer().set(boostedHealingDurationKey, PersistentDataType.INTEGER, boostedHealingDuration);
        }

        if (player.getPersistentDataContainer().has(boostedHealingPowerKey))
        {
            player.getPersistentDataContainer().set(boostedHealingPowerKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(boostedHealingPowerKey, PersistentDataType.INTEGER) + boostedHealingPower);
        }
        else
        {
            player.getPersistentDataContainer().set(boostedHealingPowerKey, PersistentDataType.INTEGER, boostedHealingPower);
        }
    }
}
