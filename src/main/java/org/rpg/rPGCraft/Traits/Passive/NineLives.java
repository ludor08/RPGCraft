package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class NineLives extends Trait
{
    NamespacedKey nineLivesKey = new NamespacedKey(main, "nine_lives");

    public NineLives(Main main) {
        // add the name and lore
        super("Nine Lives", "nine lives", ChatColor.AQUA, Material.RED_DYE, false, main, List.of(
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
