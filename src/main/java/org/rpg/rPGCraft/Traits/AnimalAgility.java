package org.rpg.rPGCraft.Traits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import static org.rpg.rPGCraft.Utils.AssembleLoreFromString;

public class AnimalAgility extends Trait
{
    private AttributeModifier speedMod;

    public AnimalAgility(Main main)
    {
        // add the name and lore
        super("Animal Agility", ChatColor.AQUA, Material.SUGAR, false,AssembleLoreFromString(
                ChatColor.AQUA.toString() + "   - Gains more walking speed.\n"
        ));

        speedMod = new AttributeModifier(main.GetRaceKey(), 0.1d, AttributeModifier.Operation.ADD_NUMBER);

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
