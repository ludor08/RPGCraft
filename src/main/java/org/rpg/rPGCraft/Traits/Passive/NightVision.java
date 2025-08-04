package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class NightVision extends Trait
{
    public NightVision(Main main) {
        // add the name and lore
        super("Night Vision", "night vision", ChatColor.AQUA, Material.ENDER_EYE, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Gains the night vision effect."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 210, 0));
    }
}
