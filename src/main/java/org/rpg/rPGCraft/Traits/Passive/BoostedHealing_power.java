package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class BoostedHealing_power extends Trait
{
    float multiplier = 1.5f;

    public BoostedHealing_power()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Boosted Healing:Power", "boosted healing power", Material.REDSTONE, false, List.of(
                ChatColor.AQUA.toString() + "   - Makes all healing traits 150% as effective."
        ));

    }

    @Override
    public int OnHealWithTrait(Entity healer, LivingEntity target, int cleanValue, int modifiedValue, EntityRegainHealthEvent.RegainReason regainReason)
    {
        return (int) Math.floor(modifiedValue * multiplier);
    }
}
