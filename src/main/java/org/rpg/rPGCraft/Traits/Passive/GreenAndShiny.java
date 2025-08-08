package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class GreenAndShiny extends Trait
{
    public GreenAndShiny(Main main) {
        // add the name and lore
        super("Green and Shiny", "green and shiny", ChatColor.AQUA, Material.EMERALD_ORE, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Gains the hero of the village effect."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 50, 0));
    }
}
