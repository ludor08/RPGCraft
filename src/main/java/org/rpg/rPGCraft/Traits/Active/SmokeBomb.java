package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class SmokeBomb extends ActiveTrait
{
    public SmokeBomb(Main main) {
        // add the name and lore
        super("Smoke Bomb", "smoke bomb", 75, ChatColor.DARK_GRAY, Material.GUNPOWDER, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Throw down a smoke bomb, and disappear for 10 seconds."
        ));


    }

    @Override
    public String GetInputSequence()
    {
        return "010";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        // disappear the player
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,200,1,true,false,true));

        // spawn the smoke
        player.getWorld().spawnParticle(Particle.SMOKE, player.getLocation(), 200);
    }
}
