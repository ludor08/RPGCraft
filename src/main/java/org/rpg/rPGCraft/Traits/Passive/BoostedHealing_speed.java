package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class BoostedHealing_speed extends Trait
{
    int boostedHealingDuration = 100;
    int boostedHealingPower = 0;

    public BoostedHealing_speed()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Boosted Healing:Speed", "boosted healing speed", Material.SUGAR, false, List.of(
                ChatColor.AQUA.toString() + "   - Makes all healing traits give speed one for five seconds, to the target of said healing."
        ));

    }

    @Override
    public int OnHealWithTrait(Entity healer, LivingEntity target, int cleanValue, int modifiedValue, EntityRegainHealthEvent.RegainReason regainReason)
    {
        target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, boostedHealingDuration, boostedHealingPower));
        return modifiedValue;
    }
}
