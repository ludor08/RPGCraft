package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.ArrayList;
import java.util.List;

public class Dash extends ActiveTrait
{
    public Dash(Main main) {
        // add the name and lore
        super("Dash", "dash", 15, ChatColor.WHITE, Material.SUGAR, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Dash forward 10 blocks, hitting any entities you collide with."
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
        Vector3d direction = RPGutils.getFacingDirection(player);

        Location location = RPGutils.RecastUntilCollision(10,direction,player.getEyeLocation(), Particle.CRIT, 5);
        List<Entity> entities = RPGutils.RecastForEntities(10,direction,player.getEyeLocation(), true, player, null, 0,new Vector3d(0.5,0.5,0.5));

        List<Entity> feetEntities = RPGutils.RecastForEntities(10,direction,player.getLocation(), false, player, null, 0,new Vector3d(0.5,0.5,0.5));

        for (Entity entity : feetEntities)
        {
            if (!entities.contains(entity))
            {
                entities.add(entity);
            }
        }

        location.setPitch(player.getPitch());
        location.setYaw(player.getYaw());

        player.teleport(location);
        for (Entity entity : entities) player.attack(entity);
    }
}
