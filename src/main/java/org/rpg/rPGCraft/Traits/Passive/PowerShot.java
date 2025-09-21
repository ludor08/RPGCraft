package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;
import java.util.Objects;

public class PowerShot extends Trait
{
    public PowerShot(Main main) {
        // add the name and lore
        super("Power Shot", "power shot", ChatColor.AQUA, Material.ARROW, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Projectiles launch at 125% of the normal speed."
        ));
    }

    @Override
    public void OnLaunchProjectile(ProjectileLaunchEvent e)
    {
        e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(1.25));
    }
}
