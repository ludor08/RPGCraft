package org.rpg.rPGCraft.Traits.Passive;

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

public class Size_Change_Small extends Trait
{
    private AttributeModifier sizeMod;

    public Size_Change_Small(Main main) {
        // add the name and lore
        super("Size Change:Small", "size change:small", ChatColor.AQUA, Material.IRON_NUGGET, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes the player a little smaller (0.5 blocks)."
        ));

        sizeMod = new AttributeModifier(new NamespacedKey(main, "small"), -0.25d, AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getAttribute(Attribute.SCALE).addModifier(sizeMod);
    }

    @Override
    public void OnTick(Player player)
    {

    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getAttribute(Attribute.SCALE).removeModifier(sizeMod);
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
