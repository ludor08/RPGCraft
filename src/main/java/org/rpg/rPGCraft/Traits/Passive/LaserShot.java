package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class LaserShot extends Trait
{
    private final NamespacedKey laserDamageKey = new NamespacedKey(main, "laser_shot_damage");
    private final double laserDamage = 5;

    public LaserShot(Main main) {
        // add the name and lore
        super("Laser Shot", "laser shot", ChatColor.AQUA, Material.BLAZE_ROD, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Replaces Steady Aim's empowered arrow with a laser that hits three times."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(laserDamageKey))
        {
            player.getPersistentDataContainer().set(laserDamageKey, PersistentDataType.DOUBLE, player.getPersistentDataContainer().get(laserDamageKey, PersistentDataType.DOUBLE) - laserDamage);

            if (player.getPersistentDataContainer().get(laserDamageKey, PersistentDataType.DOUBLE) <= 0)
            {
                player.getPersistentDataContainer().remove(laserDamageKey);
            }
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(laserDamageKey))
        {
            player.getPersistentDataContainer().set(laserDamageKey, PersistentDataType.DOUBLE, player.getPersistentDataContainer().get(laserDamageKey, PersistentDataType.DOUBLE) + laserDamage);
        }
        else
        {
            player.getPersistentDataContainer().set(laserDamageKey, PersistentDataType.DOUBLE, laserDamage);
        }
    }
}
