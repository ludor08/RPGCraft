package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class BetterBackStab extends Trait
{
    NamespacedKey backStabDamageScalerKey = new NamespacedKey(Main.GetInstance(), "back_stab_damage_scaler");
    float backStabDamageScalerMod = 0.15f;

    public BetterBackStab()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Better Back Stab", "better back stab", Material.IRON_SWORD, false, List.of(
                ChatColor.AQUA.toString() + "   - Add 15% to back stab's damage modifier."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(backStabDamageScalerKey))
        {
            player.getPersistentDataContainer().set(backStabDamageScalerKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(backStabDamageScalerKey, PersistentDataType.FLOAT) - backStabDamageScalerMod);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(backStabDamageScalerKey))
        {
            player.getPersistentDataContainer().set(backStabDamageScalerKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(backStabDamageScalerKey, PersistentDataType.FLOAT) + backStabDamageScalerMod);
        }
        else
        {
            player.getPersistentDataContainer().set(backStabDamageScalerKey, PersistentDataType.FLOAT, backStabDamageScalerMod);
        }
    }
}


