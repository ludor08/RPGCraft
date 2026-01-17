package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class LungingDash extends Trait
{
    NamespacedKey distanceKey = new NamespacedKey(Main.GetInstance(), "dash_distance");
    int distanceMod = 3;

    public LungingDash()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Lunging Dash", "lunging dash", Material.WOODEN_SWORD, false, List.of(
                ChatColor.AQUA.toString() + "   - Makes Dash travel three more blocks."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(distanceKey))
        {
            player.getPersistentDataContainer().set(distanceKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(distanceKey, PersistentDataType.INTEGER) - distanceMod);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(distanceKey))
        {
            player.getPersistentDataContainer().set(distanceKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(distanceKey, PersistentDataType.INTEGER) + distanceMod);
        }
        else
        {
            player.getPersistentDataContainer().set(distanceKey, PersistentDataType.INTEGER, distanceMod);
        }
    }
}


