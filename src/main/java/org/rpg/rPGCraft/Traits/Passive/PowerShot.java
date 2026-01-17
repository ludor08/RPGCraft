package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class PowerShot extends Trait
{
    public PowerShot() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Power Shot", "power shot", Material.ARROW, false, List.of(
                ChatColor.AQUA.toString() + "   - Projectiles launch at 125% of the normal speed."
        ));
    }

    @Override
    public void OnLaunchProjectile(ProjectileLaunchEvent e)
    {
        e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(1.25));
    }
}
