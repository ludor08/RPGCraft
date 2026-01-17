package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class BetterCombo extends Trait
{
    NamespacedKey comboDamageScalerKey = new NamespacedKey(Main.GetInstance(), "combo_damage_scaler");
    int comboDamageScalerMod = 1;

    public BetterCombo()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Better Combo", "better combo", Material.CHAIN, false, List.of(
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


