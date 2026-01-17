package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class Amphibious extends Trait
{
    AttributeModifier submergedMiningSpeedMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "amphibious_submerged_mining_speed"), 1, AttributeModifier.Operation.ADD_NUMBER);

    public Amphibious() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Amphibious", "amphibious", Material.POTION, true, List.of(
                ChatColor.AQUA.toString() + "   - Gains the conduit power effect.",
                ChatColor.AQUA.toString() + "   - Makes the player mine even faster underwater."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 50, 0));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SafeAttributeAdd(Attribute.SUBMERGED_MINING_SPEED, submergedMiningSpeedMod,player);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.SafeAttributeRemove(Attribute.SUBMERGED_MINING_SPEED, submergedMiningSpeedMod,player);
    }
}
