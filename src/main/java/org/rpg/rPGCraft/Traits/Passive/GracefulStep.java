package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class GracefulStep extends Trait
{

    public GracefulStep()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Graceful Step", "graceful step", Material.RABBIT_FOOT, false, List.of(
                ChatColor.AQUA.toString() + "   - Negates all block contact damage."
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        DamageCause damageCause = e.getCause();

        // the damage is coming from a block contact
        if (damageCause.equals(DamageCause.CONTACT))
        {
            e.setCancelled(true);
        }
    }
}
