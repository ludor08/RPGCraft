package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class FlashOfOak extends Trait
{
    private final NamespacedKey otherDamageKey = new NamespacedKey(main, "flash_of_oak_other_damage");
    float otherDamageMod = 0.15f;

    public FlashOfOak(Main main)
    {
        // add the name and lore
        super("Flash Of Oak", "flash of oak", ChatColor.AQUA, Material.OAK_LOG, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Takes 50% more damage from fire.",
                ChatColor.AQUA.toString() + "   - Takes 15% less damage from all other damage sources."
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        DamageCause damageCause = e.getCause();

        float HOT_DAMAGE_MOD = 1.5f;

        // the damage is coming from fire
        if (damageCause.equals(DamageCause.CAMPFIRE)
            || damageCause.equals(DamageCause.FIRE)
            || damageCause.equals(DamageCause.FIRE_TICK)
            || damageCause.equals(DamageCause.LAVA))
        {
            e.setDamage(e.getDamage() * HOT_DAMAGE_MOD);
        }
        else
        {
            e.setDamage(e.getDamage() * 1 - e.getEntity().getPersistentDataContainer().get(otherDamageKey, PersistentDataType.FLOAT));
        }
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
