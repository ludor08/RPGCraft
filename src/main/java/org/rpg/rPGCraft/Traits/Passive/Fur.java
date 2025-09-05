package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Fur extends Trait
{

    public Fur(Main main)
    {
        // add the name and lore
        super("Fur", "fur", ChatColor.AQUA, Material.RABBIT_HIDE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Takes 50% less cold damage.",
                ChatColor.AQUA.toString() + "   - Takes 50% more fire damage."
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        DamageCause damageCause = e.getCause();

        float HOT_DAMAGE_MOD = 1.5f;
        float COLD_DAMAGE_MOD = 0.5f;

        // the damage is coming from fire
        if (damageCause.equals(DamageCause.CAMPFIRE)
            || damageCause.equals(DamageCause.FIRE)
            || damageCause.equals(DamageCause.FIRE_TICK)
            || damageCause.equals(DamageCause.LAVA))
        {
            e.setDamage(e.getDamage() * HOT_DAMAGE_MOD);
        }
        // if the damage is coming from cold
        else if (damageCause.equals(DamageCause.FREEZE))
        {
            e.setDamage(e.getDamage() * COLD_DAMAGE_MOD);
        }
    }
}
