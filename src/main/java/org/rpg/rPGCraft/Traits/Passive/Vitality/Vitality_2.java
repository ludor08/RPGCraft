package org.rpg.rPGCraft.Traits.Passive.Vitality;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Vitality_2 extends Trait
{
    private AttributeModifier healthMod;

    public Vitality_2(Main main) {
        // add the name and lore
        super("Vitality", "vitality 2", ChatColor.AQUA, Material.APPLE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Gain one and a half extra hearts."
        ));

        healthMod = new AttributeModifier(new NamespacedKey(main, "vitality"), 3, AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SafeAttributeAdd(Attribute.MAX_HEALTH, healthMod, player);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.SafeAttributeRemove(Attribute.MAX_HEALTH, healthMod, player);
        if (player.getMaxHealth() < player.getHealth()) player.setHealth(player.getHealth()-healthMod.getAmount());
    }
}
