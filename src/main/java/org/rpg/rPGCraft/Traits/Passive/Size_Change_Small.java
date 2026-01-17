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

public class Size_Change_Small extends Trait
{
    private AttributeModifier sizeMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "small"), -0.25d, AttributeModifier.Operation.ADD_NUMBER);;

    public Size_Change_Small() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Size Change:Small", "size change:small", Material.IRON_NUGGET, false, List.of(
                ChatColor.AQUA.toString() + "   - Makes the player a little smaller (0.5 blocks)."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getAttribute(Attribute.SCALE).addModifier(sizeMod);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getAttribute(Attribute.SCALE).removeModifier(sizeMod);
    }
}
