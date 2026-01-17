package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class GreenAndShiny extends Trait
{
    public GreenAndShiny() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Green and Shiny", "green and shiny", Material.EMERALD_ORE, true, List.of(
                ChatColor.AQUA.toString() + "   - Gains the hero of the village effect."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 50, 0));
    }
}
