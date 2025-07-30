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

public class FelineAgility extends Trait
{
    private AttributeModifier jumpMod;

    public FelineAgility(Main main) {
        // add the name and lore
        super("Feline Agility", "feline agility", ChatColor.AQUA, Material.FEATHER, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Takes fall damage as if they fall half as far."
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            Player player = (Player) e.getEntity();

            int distance = (int) (player.getFallDistance()/2);

            if (distance-3 < 1) e.setCancelled(true);
            else e.setDamage(e.getDamage()/2);
        }
    }
}
