package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Pounce extends Trait
{
    private AttributeModifier jumpMod;

    public Pounce(Main main) {
        // add the name and lore
        super("Pounce", "pounce", ChatColor.AQUA, Material.RABBIT_FOOT, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Crits deal 15% more damage.",
                ChatColor.AQUA.toString() + "   - Gain more jump strength."
        ));

        jumpMod = new AttributeModifier(new NamespacedKey(main, "pounce"), 0.2d, AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getAttribute(Attribute.JUMP_STRENGTH).addModifier(jumpMod);
    }

    @Override
    public void OnTick(Player player) {

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
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            Player player = (Player) e.getEntity();

            int distance = (int) (player.getFallDistance()-1);

            if (distance-3 < 1) e.setCancelled(true);
        }
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        float POUNCE_CRIT_MOD = 1.15f;

        // check if the attack was a crit
        boolean wasACrit = e.getDamager().getFallDistance() > 0.0F && !e.getDamager().isOnGround() && e.getDamager() instanceof LivingEntity living && !living.hasPotionEffect(PotionEffectType.BLINDNESS) && e.getDamager().getVehicle() == null;

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

    @Override
    public void OnSneak(PlayerToggleSneakEvent e)
    {

    }

    @Override
    public void OnJump(PlayerJumpEvent e)
    {

    }
}
