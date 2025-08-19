package org.rpg.rPGCraft.Traits.Passive.GreaterCapacity;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class GreaterCapacity_2 extends Trait
{
    private final int maxManaMod = 50;

    public GreaterCapacity_2(Main main) {
        // add the name and lore
        super("Greater Capacity", "greater capacity 2", ChatColor.AQUA, Material.DRAGON_BREATH, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Gain +50 max mana."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(main.GetManaMaxKey()))
        {
            player.getPersistentDataContainer().set(main.GetManaMaxKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER) + maxManaMod);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(main.GetManaMaxKey()))
        {
            player.getPersistentDataContainer().set(main.GetManaMaxKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER) - maxManaMod);

            if (player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) > player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER))
            {
                player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER,player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER));
            }
        }
    }
}
