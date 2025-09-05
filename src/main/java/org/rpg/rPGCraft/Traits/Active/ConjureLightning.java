package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class ConjureLightning extends ActiveTrait
{
    public ConjureLightning(Main main) {
        // add the name and lore
        super("Conjure Lightning", "conjure lightning", 150, ChatColor.DARK_GRAY, Material.LIGHTNING_ROD, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Strike where you're looking with lightning, then, start a lightning storm."
        ));


    }

    @Override
    public String GetInputSequence()
    {
        return "100";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        // make it thundering for 5 minutes
        player.getWorld().setThundering(true);
        player.getWorld().setThunderDuration(5800);

        // spawn the lightning
        player.getWorld().spawn(RPGutils.RecastForAnything(100,RPGutils.getFacingDirection(player),player.getEyeLocation(),null, 0), LightningStrike.class);
        player.getWorld().spawn(RPGutils.RecastForAnything(100,RPGutils.getFacingDirection(player),player.getEyeLocation(),null, 0), LightningStrike.class);
        player.getWorld().spawn(RPGutils.RecastForAnything(100,RPGutils.getFacingDirection(player),player.getEyeLocation(),null, 0), LightningStrike.class);
    }
}
