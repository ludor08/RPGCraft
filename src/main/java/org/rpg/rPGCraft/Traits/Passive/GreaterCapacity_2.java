package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class GreaterCapacity_2 extends Trait
{
    private final int maxManaMod = 50;

    public GreaterCapacity_2() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Greater Capacity", "greater capacity 2", Material.DRAGON_BREATH, false, List.of(
                ChatColor.AQUA.toString() + "   - Gain +50 max mana."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(Main.GetInstance().GetManaMaxKey()))
        {
            player.getPersistentDataContainer().set(Main.GetInstance().GetManaMaxKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(Main.GetInstance().GetManaMaxKey(), PersistentDataType.INTEGER) + maxManaMod);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        Main main = Main.GetInstance();

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
