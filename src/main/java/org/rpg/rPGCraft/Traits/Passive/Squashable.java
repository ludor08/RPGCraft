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

public class Squashable extends Trait
{
    private final AttributeModifier maxHealthMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "squashable_max_health"), -10, AttributeModifier.Operation.ADD_NUMBER);

    public Squashable()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Squashable", "squashable", Material.REDSTONE, false, List.of(
                ChatColor.AQUA.toString() + "   - Sets your base max health to 10."
        ));

    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SafeAttributeAdd(Attribute.MAX_HEALTH, maxHealthMod,player);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.SafeAttributeRemove(Attribute.MAX_HEALTH, maxHealthMod,player);
    }
}
