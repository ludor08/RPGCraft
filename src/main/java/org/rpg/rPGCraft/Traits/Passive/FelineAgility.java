package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class FelineAgility extends Trait
{
    private AttributeModifier jumpMod;

    public FelineAgility() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Feline Agility", "feline agility", Material.FEATHER, false, List.of(
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
