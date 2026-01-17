package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class Vitality_2 extends Trait
{
    private AttributeModifier healthMod;

    public Vitality_2() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Vitality", "vitality 2", Material.APPLE, false, List.of(
                ChatColor.AQUA.toString() + "   - Gain one and a half extra hearts."
        ));

        healthMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "vitality"), 3, AttributeModifier.Operation.ADD_NUMBER);
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
