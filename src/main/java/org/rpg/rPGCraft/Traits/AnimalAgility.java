package org.rpg.rPGCraft.Traits;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Trait;

import static org.rpg.rPGCraft.Utils.AssembleLoreFromString;

public class AnimalAgility extends Trait
{
    public AnimalAgility()
    {
        // add the name and lore
        super("Animal Agility", false, null,AssembleLoreFromString(
                ChatColor.AQUA.toString() + "   - Gains more walking speed.\n"
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(2);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1);
    }
}
