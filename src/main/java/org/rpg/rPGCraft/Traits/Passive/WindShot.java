package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class WindShot extends Trait
{
    private final NamespacedKey neededShotsKey = new NamespacedKey(Main.GetInstance(), "wind_shot_needed_shots");
    private final int neededShots = 3;

    private final NamespacedKey shotsKey = new NamespacedKey(Main.GetInstance(), "wind_shot_shots");

    public WindShot() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Wind Shot", "wind shot", Material.WIND_CHARGE, false, List.of(
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
            // spawn the particle for wind level one
            Location locForWindLevelOne = player.getEyeLocation().add(RPGutils.getFacingDirection(player).x, RPGutils.getFacingDirection(player).y, RPGutils.getFacingDirection(player).z);

            RPGparticles.SpawnParticleDoughnut(player.getWorld(), 5, locForWindLevelOne, new Vector3d(0,0,0), Particle.CRIT, 0, 0.75f, 20, RPGutils.getFacingDirection(player));

            // shoot the arrow
            e.getEntity().setVelocity(new Vector(e.getEntity().getVelocity().getX()*2,e.getEntity().getVelocity().getY()*2,e.getEntity().getVelocity().getZ()*2));

            player.getPersistentDataContainer().set(shotsKey, PersistentDataType.INTEGER, 0);

            if (player.getPersistentDataContainer().has(new NamespacedKey(Main.GetInstance(), "high_power_wind_shot")))
            {
                List<Entity> entities = RPGraycast.RecastForEntities(100,RPGutils.getFacingDirection(player), player.getEyeLocation(),true,player,null,0,new Vector3d(3,3,3));

                // spawn the particle for wind level one
                Location locForWindLevelTwo = player.getEyeLocation().add(RPGutils.getFacingDirection(player).x*3, RPGutils.getFacingDirection(player).y*3, RPGutils.getFacingDirection(player).z*3);

                RPGparticles.SpawnParticleDoughnut(player.getWorld(), 5, locForWindLevelTwo, new Vector3d(0,0,0), Particle.CRIT, 0, 1.475f, 40, RPGutils.getFacingDirection(player));

                for (Entity entity : entities)
                {
                    entity.setVelocity(entity.getVelocity().add(e.getEntity().getVelocity().divide(new Vector(4,4,4))));
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
