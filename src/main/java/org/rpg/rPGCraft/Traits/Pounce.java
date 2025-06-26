package org.rpg.rPGCraft.Traits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import static org.rpg.rPGCraft.Utils.AssembleLoreFromString;

public class Pounce extends Trait
{
    private AttributeModifier jumpMod;

    public Pounce(Main main) {
        // add the name and lore
        super("Pounce", ChatColor.AQUA, Material.RABBIT_FOOT, false,AssembleLoreFromString(
                ChatColor.AQUA.toString() + "   - Crits deal 15% more damage.\n" +
                ChatColor.AQUA.toString() + "   - Gain more jump strength."
        ));

        jumpMod = new AttributeModifier(main.GetRaceKey(), 0.2d, AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getAttribute(Attribute.JUMP_STRENGTH).addModifier(jumpMod);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getAttribute(Attribute.JUMP_STRENGTH).removeModifier(jumpMod);
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
        float POUNCE_CRIT_MOD = 1.15f;

        // check if the attack was a crit
        boolean wasACrit = e.getDamager().getFallDistance() > 0.0F && !e.getDamager().isOnGround() && e.getDamager() instanceof LivingEntity living && !living.hasPotionEffect(PotionEffectType.BLINDNESS) && e.getDamager().getVehicle() == null;

        Player player = (Player) e.getDamager();

        // if it was a crit
        if (wasACrit)
        {
            // do pounceCritMod more damage
            e.setDamage(e.getDamage()* POUNCE_CRIT_MOD);
        }
    }

    @Override
    public void OnFoodLevelChange(FoodLevelChangeEvent e)
    {

    }


}
