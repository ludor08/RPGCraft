package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class ConjureLightning extends ActiveTrait
{
    public ConjureLightning() {
        // add the name and lore
        super(ChatColor.DARK_GRAY + ChatColor.BOLD.toString() + "Conjure Lightning", "conjure lightning", 75, Material.LIGHTNING_ROD, false, List.of(
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
        player.getWorld().spawn(RPGraycast.RecastForAnything(100,RPGutils.getFacingDirection(player),player.getEyeLocation(),null, 0), LightningStrike.class);
        player.getWorld().spawn(RPGraycast.RecastForAnything(100,RPGutils.getFacingDirection(player),player.getEyeLocation(),null, 0), LightningStrike.class);
        player.getWorld().spawn(RPGraycast.RecastForAnything(100,RPGutils.getFacingDirection(player),player.getEyeLocation(),null, 0), LightningStrike.class);
    }
}
