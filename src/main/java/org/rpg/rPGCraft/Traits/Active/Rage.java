package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Rage extends ActiveTrait
{

    public Rage(Main main) {
        // add the name and lore
        super("Signature Ability : " + ChatColor.DARK_RED + ChatColor.BOLD + "Rage", "rage", 60, ChatColor.AQUA, Material.REDSTONE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Gain resistance and strength one for 30 seconds"
        ));
    }

    @Override
    public String GetInputSequence()
    {
        return "000";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 600, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 600, 0));

        for (int i = 0; i < 20; i++)
        {
            Vector3d offset = new Vector3d(Math.cos((Math.PI*2)/((double) i /20)), 0, Math.sin((Math.PI*2)/((double) i /20)));
            Location location = new Location(player.getWorld(), player.getLocation().getX() + offset.x, player.getLocation().getY() + offset.y, player.getLocation().getZ() + offset.z);

            player.getWorld().spawnParticle(Particle.RAID_OMEN, location, 10, 0,0,0,2);
        }
    }
}
