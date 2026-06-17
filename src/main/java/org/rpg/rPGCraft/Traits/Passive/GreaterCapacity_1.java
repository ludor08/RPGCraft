package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class GreaterCapacity_1 extends Trait
{
    private final int maxManaMod = 25;

    public GreaterCapacity_1() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Greater Capacity", "greater capacity 1", Material.DRAGON_BREATH, false, List.of(
                ChatColor.AQUA.toString() + "   - Gain +25 max mana."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(MyNamespaces.MANA_MAX.GetNamespacedKey()))
        {
            player.getPersistentDataContainer().set(MyNamespaces.MANA_MAX.GetNamespacedKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(MyNamespaces.MANA_MAX.GetNamespacedKey(), PersistentDataType.INTEGER) + maxManaMod);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(MyNamespaces.MANA_MAX.GetNamespacedKey()))
        {
            player.getPersistentDataContainer().set(MyNamespaces.MANA_MAX.GetNamespacedKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(MyNamespaces.MANA_MAX.GetNamespacedKey(), PersistentDataType.INTEGER) - maxManaMod);

            if (player.getPersistentDataContainer().get(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER) > player.getPersistentDataContainer().get(MyNamespaces.MANA_MAX.GetNamespacedKey(), PersistentDataType.INTEGER))
            {
                player.getPersistentDataContainer().set(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER,player.getPersistentDataContainer().get(MyNamespaces.MANA_MAX.GetNamespacedKey(), PersistentDataType.INTEGER));
            }
        }
    }
}
