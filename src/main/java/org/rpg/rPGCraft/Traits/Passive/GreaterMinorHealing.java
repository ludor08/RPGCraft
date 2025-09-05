package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class GreaterMinorHealing extends Trait
{
    private final NamespacedKey healAmountKey = new NamespacedKey(main, "mend_minor_wounds_amount");
    int extraHealAmount = 2;

    public GreaterMinorHealing(Main main) {
        // add the name and lore
        super("Greater Minor Healing", "greater minor healing", ChatColor.AQUA, Material.LIME_DYE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Mend Minor Wounds heal two extra health."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(healAmountKey))
        {
            player.getPersistentDataContainer().set(healAmountKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(healAmountKey, PersistentDataType.INTEGER) - extraHealAmount);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(healAmountKey))
        {
            player.getPersistentDataContainer().set(healAmountKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(healAmountKey, PersistentDataType.INTEGER) + extraHealAmount);
        }
        else
        {
            player.getPersistentDataContainer().set(healAmountKey, PersistentDataType.INTEGER, extraHealAmount);
        }
    }
}
