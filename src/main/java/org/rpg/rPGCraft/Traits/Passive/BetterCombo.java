package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class BetterCombo extends Trait
{
    NamespacedKey comboDamageScalerKey = new NamespacedKey(main, "combo_damage_scaler");
    int comboDamageScalerMod = 1;

    public BetterCombo(Main main)
    {
        // add the name and lore
        super("Better Combo", "better combo", ChatColor.AQUA, Material.CHAIN, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Double combo's damage modifier."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(comboDamageScalerKey))
        {
            player.getPersistentDataContainer().set(comboDamageScalerKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(comboDamageScalerKey, PersistentDataType.INTEGER) - comboDamageScalerMod);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(comboDamageScalerKey))
        {
            player.getPersistentDataContainer().set(comboDamageScalerKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(comboDamageScalerKey, PersistentDataType.INTEGER) + comboDamageScalerMod);
        }
        else
        {
            player.getPersistentDataContainer().set(comboDamageScalerKey, PersistentDataType.INTEGER, comboDamageScalerMod);
        }
    }
}


