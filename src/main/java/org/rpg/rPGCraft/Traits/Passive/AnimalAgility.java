package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class AnimalAgility extends Trait
{
    private AttributeModifier speedMod;

    public AnimalAgility()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Animal Agility", "animal agility", Material.SUGAR, false, List.of(
                ChatColor.AQUA.toString() + "   - Gain more walking speed."
        ));

        speedMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "animal_agility"), 0.1d, AttributeModifier.Operation.ADD_NUMBER);

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
