package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class Mithridatism extends Trait
{
    public Mithridatism() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Mithridatism", "mithridatim", Material.POTION, true, List.of(
                ChatColor.AQUA.toString() + "   - Gives you immunity to the poison effect."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        if (player.hasPotionEffect(PotionEffectType.POISON))
        {
            player.removePotionEffect(PotionEffectType.POISON);
        }
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.POISON))
        {
            e.setCancelled(true);
        }
    }
}
