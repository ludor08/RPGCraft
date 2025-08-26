package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.joml.Vector3d;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class SpinAttack extends ActiveTrait
{
    public SpinAttack(Main main) {
        // add the name and lore
        super("Spin Attack", "spin attack", 45, ChatColor.RED, Material.STONE_SWORD, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Spin around and hit and entities within three blocks."
        ));


    }

    @Override
    public String GetInputSequence()
    {
        return "101";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        // get nearby entities
        List<Entity> nearbyEntities = player.getLocation().add(0,player.getHeight()/2,0).getNearbyEntities(3, (player.getHeight()/2)*1.25, 3).stream().toList();

        // go through all the nearby entities
        for (Entity entity : nearbyEntities)
        {
            // if this entity is the player
            if (entity == player)
            {
                continue;
            }

            // get the distance between the player and the entity
            double distance = RPGutils.getDistance(player.getLocation().add(0,player.getHeight()/2,0), entity.getLocation());

            // if the entity is less than three blocks away
            if (distance <= 3)
            {
                player.attack(entity);
            }
        }
    }
}
