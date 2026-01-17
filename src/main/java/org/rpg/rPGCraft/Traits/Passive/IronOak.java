package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class IronOak extends Trait
{
    private final NamespacedKey otherDamageKey = new NamespacedKey(Main.GetInstance(), "flash_of_oak_other_damage");
    float otherDamageMod = 0.15f;

    public IronOak()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Iron Oak", "iron oak", Material.OAK_LOG, false, List.of(
                ChatColor.AQUA.toString() + "   - Makes Flash Of Oak negate 15% more damage from all non-fire damage sources."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(otherDamageKey))
        {
            player.getPersistentDataContainer().set(otherDamageKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(otherDamageKey, PersistentDataType.FLOAT) - otherDamageMod);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(otherDamageKey))
        {
            player.getPersistentDataContainer().set(otherDamageKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(otherDamageKey, PersistentDataType.FLOAT) + otherDamageMod);
        }
        else
        {
            player.getPersistentDataContainer().set(otherDamageKey, PersistentDataType.FLOAT, otherDamageMod);
        }
    }
}
