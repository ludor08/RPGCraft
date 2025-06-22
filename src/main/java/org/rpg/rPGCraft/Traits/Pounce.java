package org.rpg.rPGCraft.Traits;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Trait;

import static org.rpg.rPGCraft.Utils.AssembleLoreFromString;

public class Pounce extends Trait
{

    public Pounce() {
        // add the name and lore
        super("Pounce", false,AssembleLoreFromString(
                ChatColor.AQUA.toString() + "   - Crits deal 10% more damage."
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

    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        // check if the attack was a crit
        boolean wasACrit = e.getDamager().getFallDistance() > 0.0F && !e.getDamager().isOnGround() && e.getDamager() instanceof LivingEntity living && !living.hasPotionEffect(PotionEffectType.BLINDNESS) && e.getDamager().getVehicle() == null;

        Player player = (Player) e.getDamager();
        player.sendMessage("was it a crit?");

        // if it was a crit
        if (wasACrit)
        {
            // do 10% more damage
            player.sendMessage("damage : " + e.getDamage() + " -> " + e.getDamage() * 1.15);
            e.setDamage(e.getDamage()*1.15);
        }
    }

    @Override
    public void OnEat(FoodLevelChangeEvent e)
    {

    }


}
