package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class AntiGravityArrow extends Trait
{
    public AntiGravityArrow(Main main) {
        // add the name and lore
        super("Anti-Gravity Arrow", "anti gravity arrow", ChatColor.AQUA, Material.ARROW, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Projectiles have gravity.",
                ChatColor.AQUA.toString() + "   - Projectiles launch at 50% of the normal speed."
        ));
    }

    @Override
    public void OnLaunchProjectile(ProjectileLaunchEvent e)
    {
        e.getEntity().setGravity(false);
    }
}
