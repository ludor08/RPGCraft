package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Amphibious extends Trait
{
    public Amphibious(Main main) {
        // add the name and lore
        super("Amphibious", "amphibious", ChatColor.AQUA, Material.POTION, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Gains the conduit power effect."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 50, 0));
    }
}
