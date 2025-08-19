package org.rpg.rPGCraft.Traits.Passive.ManaRegainSpeed;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class ManaRegainSpeed extends Trait
{
    private final int manaRegainMod = 1;

    public ManaRegainSpeed(Main main) {
        // add the name and lore
        super("Mana Regain Speed", "mana regain speed 1", ChatColor.AQUA, Material.LAPIS_LAZULI, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Gain +1 mana regain speed."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(main.GetManaRechargeSpeedKey()))
        {
            player.getPersistentDataContainer().set(main.GetManaRechargeSpeedKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaRechargeSpeedKey(), PersistentDataType.INTEGER) + manaRegainMod);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(main.GetManaRechargeSpeedKey()))
        {
            player.getPersistentDataContainer().set(main.GetManaRechargeSpeedKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaRechargeSpeedKey(), PersistentDataType.INTEGER) - manaRegainMod);
        }
    }
}
