package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class ShieldCharge extends Trait
{
    private final NamespacedKey shieldChargeDamageModKey = new NamespacedKey(Main.GetInstance(), "shield_charge_damage_mod");

    public ShieldCharge() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Shield Charge", "shield charge", Material.ANVIL, false, List.of(
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
