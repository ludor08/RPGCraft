package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class BullCharge extends Trait
{
    NamespacedKey speedModKey = new NamespacedKey(Main.GetInstance(), "charge_speed_mod");

    NamespacedKey chargeDamageKey = new NamespacedKey(Main.GetInstance(), "charge_damage");
    int chargeDamageMod = 2;

    public BullCharge() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Bull Charge", "bull charge", Material.WIND_CHARGE, false, List.of(
                ChatColor.AQUA.toString() + "   - Increases the speed mod from Charge and doubles Charge damage."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, speedModKey, 0, -0.5f);
        RPGutils.AddToNamespacedKey(player, chargeDamageKey, 0, -chargeDamageMod);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, speedModKey, 0, 0.5f);
        RPGutils.AddToNamespacedKey(player, chargeDamageKey, 0, chargeDamageMod);
    }
}
