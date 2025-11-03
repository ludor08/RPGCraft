package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class BullCharge extends Trait
{
    NamespacedKey speedModKey = new NamespacedKey(main, "charge_speed_mod");

    NamespacedKey chargeDamageKey = new NamespacedKey(main, "charge_damage");
    int chargeDamageMod = 2;

    public BullCharge(Main main) {
        // add the name and lore
        super("Bull Charge", "bull charge", ChatColor.AQUA, Material.WIND_CHARGE, false, main, List.of(
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
