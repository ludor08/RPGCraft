package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class ExtraAgilityRunning extends Trait
{
    private AttributeModifier speedMod = new AttributeModifier(new NamespacedKey(main, "extra_agility_running"), 0.1d, AttributeModifier.Operation.ADD_NUMBER);

    public ExtraAgilityRunning(Main main)
    {
        // add the name and lore
        super("Extra Agility:Running", "extra agility running", ChatColor.AQUA, Material.SUGAR, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Gain more walking speed."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SafeAttributeAdd(Attribute.MOVEMENT_SPEED, speedMod, player);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.SafeAttributeRemove(Attribute.MOVEMENT_SPEED, speedMod, player);
    }
}
