package org.rpg.rPGCraft.Traits;

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
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Vitality extends Trait
{
    private AttributeModifier healthMod;

    public Vitality(Main main) {
        // add the name and lore
        super("Vitality", ChatColor.AQUA, Material.APPLE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Gain one extra heart."
        ));

        healthMod = new AttributeModifier(new NamespacedKey(main, "vitality"), 2, AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getAttribute(Attribute.MAX_HEALTH).addModifier(healthMod);
        player.setHealth(player.getHealth()+healthMod.getAmount());
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getAttribute(Attribute.MAX_HEALTH).removeModifier(healthMod);
        player.setHealth(player.getHealth()-healthMod.getAmount());
    }

    @Override
    public void OnRespawnBuffs(PlayerRespawnEvent e)
    {

    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {

    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
    }

    @Override
    public void OnFoodLevelChange(FoodLevelChangeEvent e)
    {

    }
}
