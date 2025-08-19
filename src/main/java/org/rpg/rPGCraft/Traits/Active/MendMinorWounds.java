package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class MendMinorWounds extends ActiveTrait
{
    public MendMinorWounds(Main main) {
        // add the name and lore
        super("Mend Minor Wounds", "mend minor wounds", 35, ChatColor.GREEN, Material.TURTLE_SCUTE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Shoots a beam that heals whichever entity it hits for three hearts."
        ));
    }

    @Override
    public String GetInputSequence()
    {
        return "111";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        Vector3d direction = new Vector3d(-Math.cos(Math.toRadians(player.getPitch())) * Math.sin(Math.toRadians(player.getYaw())), -Math.sin(Math.toRadians(player.getPitch())), Math.cos(Math.toRadians(player.getPitch())) * Math.cos(Math.toRadians(player.getYaw())));
        Entity lookingAt = RPGutils.EntityRecast(100, direction, player.getEyeLocation(), true, player, Particle.HAPPY_VILLAGER, 5);

        if (lookingAt instanceof LivingEntity livingLookingAt)
        {
            livingLookingAt.heal(6, EntityRegainHealthEvent.RegainReason.MAGIC);
        }
    }
}
