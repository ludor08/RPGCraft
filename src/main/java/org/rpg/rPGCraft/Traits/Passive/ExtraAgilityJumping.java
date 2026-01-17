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

public class ExtraAgilityJumping extends Trait
{
    private AttributeModifier jumpMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "extra_agility_jumping"), 0.1d, AttributeModifier.Operation.ADD_NUMBER);

    public ExtraAgilityJumping()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Extra Agility:Jumping", "extra agility jumping", Material.SUGAR, false, List.of(
                ChatColor.AQUA.toString() + "   - Gain more jump strength."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SafeAttributeAdd(Attribute.JUMP_STRENGTH, jumpMod, player);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.SafeAttributeRemove(Attribute.JUMP_STRENGTH, jumpMod, player);
    }
}
