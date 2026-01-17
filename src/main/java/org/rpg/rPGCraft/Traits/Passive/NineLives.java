package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class NineLives extends Trait
{
    NamespacedKey nineLivesKey = new NamespacedKey(Main.GetInstance(), "nine_lives");

    public NineLives() {
        // add the name and lore
        super(ChatColor.BOLD.toString() + "Nine Lives", "nine lives", Material.RED_DYE, false, List.of(
                ChatColor.AQUA.toString() + "   - Once per life when the Feloid is dropped to 0 hp, they drop to 2 hp instead."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getPersistentDataContainer().set(nineLivesKey, PersistentDataType.BOOLEAN, true);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getPersistentDataContainer().remove(nineLivesKey);
    }

    @Override
    public void OnRespawnBuffs(PlayerRespawnEvent e)
    {
        e.getPlayer().getPersistentDataContainer().set(nineLivesKey, PersistentDataType.BOOLEAN, true);
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (((Player)e.getEntity()).getHealth()-e.getDamage() < 1 &&
                e.getEntity().getPersistentDataContainer().get(nineLivesKey, PersistentDataType.BOOLEAN))
        {
            e.setCancelled(true);
            ((Player)e.getEntity()).setHealth(2);

            e.getEntity().getWorld().spawnParticle(Particle.CRIT, e.getEntity().getLocation(), 100, 0,0,0,1);
            ((Player) e.getEntity()).playSound(e.getEntity().getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);

            e.getEntity().getPersistentDataContainer().set(nineLivesKey, PersistentDataType.BOOLEAN, false);
        }
    }
}
