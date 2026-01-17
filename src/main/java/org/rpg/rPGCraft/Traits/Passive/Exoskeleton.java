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

public class Exoskeleton extends Trait
{
    private AttributeModifier armorMod;

    public Exoskeleton() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Exoskeleton", "exoskeleton", Material.CHAINMAIL_CHESTPLATE, false, List.of(
                ChatColor.AQUA.toString() + "   - Gain four extra base armor."
        ));

        armorMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "exoskeleton"), 4, AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SafeAttributeAdd(Attribute.ARMOR, armorMod, player);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.SafeAttributeRemove(Attribute.ARMOR, armorMod, player);
    }
}
