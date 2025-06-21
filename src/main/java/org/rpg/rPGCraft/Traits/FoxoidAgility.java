package org.rpg.rPGCraft.Traits;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import static org.rpg.rPGCraft.Utils.AssembleLoreFromString;

public class FoxoidAgility extends Trait
{
    private AttributeModifier jumpMod;

    public FoxoidAgility(Main main)
    {
        // add the name and lore
        super("Foxoid Agility", false, null,AssembleLoreFromString(
                ChatColor.AQUA.toString() + "   - Gains more jump strength.\n"
        ));

        jumpMod = new AttributeModifier(main.GetRaceKey(), 0.2d, AttributeModifier.Operation.ADD_NUMBER);

    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.sendMessage("jump " + player.getAttribute(Attribute.JUMP_STRENGTH).getBaseValue());
        player.getAttribute(Attribute.JUMP_STRENGTH).addModifier(jumpMod);
        player.sendMessage("jump " + player.getAttribute(Attribute.JUMP_STRENGTH).getBaseValue());
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getAttribute(Attribute.JUMP_STRENGTH).removeModifier(jumpMod);
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
    public void OnEat(FoodLevelChangeEvent e)
    {

    }
}
