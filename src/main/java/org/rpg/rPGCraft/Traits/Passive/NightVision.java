package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class NightVision extends Trait
{
    public NightVision() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Night Vision", "night vision", Material.ENDER_EYE, true, List.of(
                ChatColor.AQUA.toString() + "   - Gains the night vision effect."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 210, 0));
    }
}
