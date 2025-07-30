package org.rpg.rPGCraft.Traits.Passive;

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
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class AnimalAgility extends Trait
{
    private AttributeModifier speedMod;

    public AnimalAgility(Main main)
    {
        // add the name and lore
        super("Animal Agility", "animal agility", ChatColor.AQUA, Material.SUGAR, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Gain more walking speed."
        ));

        speedMod = new AttributeModifier(new NamespacedKey(main, "animal_agility"), 0.1d, AttributeModifier.Operation.ADD_NUMBER);

    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getAttribute(Attribute.MOVEMENT_SPEED).addModifier(speedMod);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getAttribute(Attribute.MOVEMENT_SPEED).removeModifier(speedMod);
    }
}
