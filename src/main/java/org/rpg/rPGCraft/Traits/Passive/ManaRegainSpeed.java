package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class ManaRegainSpeed extends Trait
{
    private final int manaRegainMod = 1;

    public ManaRegainSpeed() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Mana Regain Speed", "mana regain speed 1", Material.LAPIS_LAZULI, false, List.of(
                ChatColor.AQUA.toString() + "   - Gain +1 mana regain speed."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, Main.GetInstance().GetManaRechargeSpeedKey(), 1, manaRegainMod);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, Main.GetInstance().GetManaRechargeSpeedKey(), 1, -manaRegainMod);
    }
}
