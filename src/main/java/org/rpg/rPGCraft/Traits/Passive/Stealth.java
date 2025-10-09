package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Stealth extends Trait
{
    NamespacedKey invisibilityFromStealthKey = new NamespacedKey(main, "invisibility_from_stealth");

    NamespacedKey stealthTimeKey = new NamespacedKey(main, "stealth_time");

    NamespacedKey stealthTimeNeededKey = new NamespacedKey(main, "stealth_time");
    int baseStealthTimeNeeded = 50;

    public Stealth(Main main) {
        // add the name and lore
        super("Stealth", "stealth", ChatColor.AQUA, Material.LEATHER, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Standing still or crouching for five seconds well turn you invisible.",
                ChatColor.AQUA.toString() + "   - Moving while not crouching will break invisibility."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        if ((player.getVelocity().getX() == 0 && player.getVelocity().getZ() == 0) || player.isSneaking())
        {
            player.getPersistentDataContainer().set(stealthTimeKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(stealthTimeKey, PersistentDataType.INTEGER) + 1);

            if (player.getPersistentDataContainer().get(stealthTimeNeededKey, PersistentDataType.INTEGER) <= player.getPersistentDataContainer().get(stealthTimeKey, PersistentDataType.INTEGER) && !player.hasPotionEffect(PotionEffectType.INVISIBILITY))
            {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20, 0,true,false));
                player.getPersistentDataContainer().set(invisibilityFromStealthKey, PersistentDataType.BOOLEAN, true);
            }

        }
        else
        {
            if (player.getPersistentDataContainer().get(invisibilityFromStealthKey, PersistentDataType.BOOLEAN))
            {
                player.getPersistentDataContainer().set(stealthTimeKey, PersistentDataType.INTEGER, 0);
                player.getPersistentDataContainer().set(invisibilityFromStealthKey, PersistentDataType.BOOLEAN, false);
            }
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(stealthTimeKey))
        {
            player.getPersistentDataContainer().remove(stealthTimeKey);
        }

        if (player.getPersistentDataContainer().has(invisibilityFromStealthKey))
        {
            player.getPersistentDataContainer().remove(invisibilityFromStealthKey);
        }

        if (player.getPersistentDataContainer().has(stealthTimeNeededKey))
        {
            player.getPersistentDataContainer().set(stealthTimeNeededKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(stealthTimeNeededKey, PersistentDataType.INTEGER) - baseStealthTimeNeeded);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getPersistentDataContainer().set(invisibilityFromStealthKey, PersistentDataType.BOOLEAN, false);

        player.getPersistentDataContainer().set(stealthTimeKey, PersistentDataType.INTEGER, 0);

        if (player.getPersistentDataContainer().has(stealthTimeNeededKey))
        {
            player.getPersistentDataContainer().set(stealthTimeNeededKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(stealthTimeNeededKey, PersistentDataType.INTEGER) + baseStealthTimeNeeded);
        }
        else
        {
            player.getPersistentDataContainer().set(stealthTimeNeededKey, PersistentDataType.INTEGER, baseStealthTimeNeeded);
        }
    }
}
