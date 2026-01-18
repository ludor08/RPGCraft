package org.rpg.rPGCraft;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.joml.Vector3d;

import java.util.Random;

public class RPGparticles
{
    public static void SpawnBlockParticle(World world, int count, Location location, Vector3d area, BlockData blockData, float speed)
    {
        world.spawnParticle(Particle.BLOCK, location, count, area.x, area.y, area.z, speed, blockData);
    }

    public static void SpawnBlockParticle(Player player, int count, Location location, Vector3d area, BlockData blockData, float speed)
    {
        player.spawnParticle(Particle.BLOCK, location, count, area.x, area.y, area.z, speed, blockData);
    }

    public static void SpawnParticle(World world, int count, Location location, Vector3d area, Particle particle, float speed)
    {
        world.spawnParticle(particle, location, count, area.x, area.y, area.z, speed);
    }

    public static void SpawnParticle(Player player, int count, Location location, Vector3d area, Particle particle, float speed)
    {
        player.spawnParticle(particle, location, count, area.x, area.y, area.z, speed);
    }

    public static void SpawnParticleWithMotion(Player player, int count, Location location, Vector3d motion, Vector3d area, Particle particle, float speed)
    {
        Random random = new Random();

        for (int i = 0; i < count; i++)
        {
            Location loc = new Location(location.getWorld(), location.getX() + random.nextGaussian()*area.x, location.getY() + random.nextGaussian()*area.y, location.getZ() + random.nextGaussian()*area.z);

            RPGparticles.SpawnParticle(player, 0, loc, new Vector3d(motion.x, motion.y, motion.z), particle, speed);
        }
    }

    public static void SpawnParticleWithMotion(World world, int count, Location location, Vector3d motion, Vector3d area, Particle particle, float speed)
    {
        Random random = new Random();

        for (int i = 0; i < count; i++)
        {
            Location loc = new Location(location.getWorld(), location.getX() + random.nextGaussian()*area.x, location.getY() + random.nextGaussian()*area.y, location.getZ() + random.nextGaussian()*area.z);

            RPGparticles.SpawnParticle(world, 0, loc, new Vector3d(motion.x, motion.y, motion.z), particle, speed);
        }
    }

    public static void SpawnBlockParticleWithMotion(Player player, int count, Location location, Vector3d motion, Vector3d area, BlockData blockData, float speed)
    {
        Random random = new Random();

        for (int i = 0; i < count; i++)
        {
            Location loc = new Location(location.getWorld(), location.getX() + random.nextGaussian()*area.x, location.getY() + random.nextGaussian()*area.y, location.getZ() + random.nextGaussian()*area.z);

            RPGparticles.SpawnBlockParticle(player, 0, loc, new Vector3d(motion.x, motion.y, motion.z), blockData, speed);
        }
    }

    public static void SpawnParticleDoughnut(World world, int count, Location location, Vector3d area, Particle particle, float speed, float radius, int segments, Vector3d facing)
    {
        for (int i = 1; i < segments; i++)
        {
            Vector3d offset = RPGutils.getLocationOffsetByVector(location, facing, 0, (float) Math.cos((Math.PI*2)/((double) i /20)) * radius, (float) Math.sin((Math.PI*2)/((double) i /20)) * radius);

            Location offsetLocation = new Location(world, offset.x, offset.y, offset.z);

            SpawnParticle(world, count, offsetLocation, area, particle, speed);
        }
    }
}
