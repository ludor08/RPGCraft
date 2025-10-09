package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class WindShot extends Trait
{
    private final NamespacedKey neededShotsKey = new NamespacedKey(main, "wind_shot_needed_shots");
    private final int neededShots = 3;

    private final NamespacedKey shotsKey = new NamespacedKey(main, "wind_shot_shots");

    public WindShot(Main main) {
        // add the name and lore
        super("Wind Shot", "wind shot", ChatColor.AQUA, Material.WIND_CHARGE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Every third shot fires two times faster."
        ));
    }

    @Override
    public void OnLaunchProjectile(ProjectileLaunchEvent e)
    {
        Player player = ((Player)e.getEntity().getShooter());

        player.getPersistentDataContainer().set(shotsKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(shotsKey, PersistentDataType.INTEGER) + 1);

        if (player.getPersistentDataContainer().get(shotsKey, PersistentDataType.INTEGER) >= player.getPersistentDataContainer().get(neededShotsKey, PersistentDataType.INTEGER))
        {
            e.getEntity().setVelocity(new Vector(e.getEntity().getVelocity().getX()*2,e.getEntity().getVelocity().getY()*2,e.getEntity().getVelocity().getZ()*2));

            player.getPersistentDataContainer().set(shotsKey, PersistentDataType.INTEGER, 0);

            if (player.getPersistentDataContainer().has(new NamespacedKey(main, "high_power_wind_shot")))
            {
                List<Entity> entities = RPGraycast.RecastForEntities(100,RPGutils.getFacingDirection(player), player.getEyeLocation(),true,player,null,0,new Vector3d(3,3,3));

                for (Entity entity : entities)
                {
                    entity.setVelocity(entity.getVelocity().add(e.getEntity().getVelocity()));
                }
            }
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(neededShotsKey))
        {
            player.getPersistentDataContainer().set(neededShotsKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(neededShotsKey, PersistentDataType.INTEGER) - neededShots);
        }

        if (player.getPersistentDataContainer().has(shotsKey))
        {
            player.getPersistentDataContainer().remove(shotsKey);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(neededShotsKey))
        {
            player.getPersistentDataContainer().set(neededShotsKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(neededShotsKey, PersistentDataType.INTEGER) + neededShots);
        }
        else
        {
            player.getPersistentDataContainer().set(neededShotsKey, PersistentDataType.INTEGER, neededShots);
        }

        player.getPersistentDataContainer().set(shotsKey, PersistentDataType.INTEGER, 0);
    }
}
