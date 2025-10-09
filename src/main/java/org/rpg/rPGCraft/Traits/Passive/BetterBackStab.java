package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class BetterBackStab extends Trait
{
    NamespacedKey backStabDamageScalerKey = new NamespacedKey(main, "back_stab_damage_scaler");
    float backStabDamageScalerMod = 0.15f;

    public BetterBackStab(Main main)
    {
        // add the name and lore
        super("Better Back Stab", "better back stab", ChatColor.AQUA, Material.IRON_SWORD, false, main, List.of(
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


