package org.rpg.rPGCraft.Traits;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.rpg.rPGCraft.Trait;

import static org.rpg.rPGCraft.Utils.AssembleLoreFromString;

public class Fur extends Trait
{

    public Fur() {
        // add the name and lore
        super("Fur", false,AssembleLoreFromString(
                ChatColor.AQUA.toString() + "   - Takes 0.5x cold damage.\n" +
                ChatColor.AQUA.toString() + "   - Takes 1.5x damage from fire.\n"
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {

    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {

    }

    @Override
    public void OnRespawnBuffs(PlayerRespawnEvent e)
    {

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
            if (e.getEntity() instanceof Player player) player.sendMessage("fire damage: " + e.getDamage() + "->" + ((int)e.getDamage() * HOT_DAMAGE_MOD));
            e.setDamage(e.getDamage() * HOT_DAMAGE_MOD);
        }
        // if the damage is coming from cold
        else if (damageCause.equals(DamageCause.FREEZE))
        {
            if (e.getEntity() instanceof Player player) player.sendMessage("cold damage: " + e.getDamage() + "->" + ((int)e.getDamage() * COLD_DAMAGE_MOD));
            e.setDamage(e.getDamage() * COLD_DAMAGE_MOD);
        }
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {

    }

    @Override
    public void OnEat(FoodLevelChangeEvent e)
    {

    }


}
