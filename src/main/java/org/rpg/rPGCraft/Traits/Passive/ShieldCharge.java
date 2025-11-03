package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class ShieldCharge extends Trait
{
    private final NamespacedKey shieldChargeDamageModKey = new NamespacedKey(main, "shield_charge_damage_mod");

    public ShieldCharge(Main main) {
        // add the name and lore
        super("Shield Charge", "shield charge", ChatColor.AQUA, Material.ANVIL, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Shield Charge deals two more deal, if using a shield."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, shieldChargeDamageModKey);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, shieldChargeDamageModKey, 2);
    }
}
