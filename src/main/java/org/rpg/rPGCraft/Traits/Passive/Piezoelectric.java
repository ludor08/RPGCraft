package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class Piezoelectric extends Trait
{
    private final NamespacedKey storedDamage;
    private final List<EntityDamageEvent.DamageCause> physicalDamageCauses = List.of(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, EntityDamageEvent.DamageCause.ENTITY_ATTACK, EntityDamageEvent.DamageCause.FALL, EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION,
            EntityDamageEvent.DamageCause.FALLING_BLOCK, EntityDamageEvent.DamageCause.CONTACT, EntityDamageEvent.DamageCause.FLY_INTO_WALL, EntityDamageEvent.DamageCause.PROJECTILE, EntityDamageEvent.DamageCause.THORNS);

    public Piezoelectric()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Piezoelectric", "toxic spores", Material.FIREWORK_STAR, true, List.of(
                ChatColor.AQUA.toString() + "   - Upon being hit by a physical attack, half of the",
                ChatColor.AQUA.toString() + "     damage will be stored within you. If you hit a",
                ChatColor.AQUA.toString() + "     creature well crouching, all of this stored",
                ChatColor.AQUA.toString() + "     damage will be transferred into the hit.",
                ChatColor.AQUA.toString() + "     This stored damage will slowly fade."
        ));

        storedDamage = new NamespacedKey(Main.GetInstance(), "piezoelectric_stored_damage");
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        Player player = (Player) e.getEntity();

        // if the damage cause is one of the physicalDamageCauses
        if (physicalDamageCauses.contains(e.getCause()))
        {
            if (player.getPersistentDataContainer().has(storedDamage))
            {
                player.getPersistentDataContainer().set(storedDamage, PersistentDataType.DOUBLE, (e.getDamage())/2 + player.getPersistentDataContainer().get(storedDamage, PersistentDataType.DOUBLE));
            }
            else
            {
                player.getPersistentDataContainer().set(storedDamage, PersistentDataType.DOUBLE, (e.getDamage())/2);
            }
        }
    }

    @Override
    public void OnTick(Player player)
    {
        if (player.getPersistentDataContainer().has(storedDamage))
        {
            if (player.getPersistentDataContainer().get(storedDamage, PersistentDataType.DOUBLE)-0.1 > 0)
            {
                player.getPersistentDataContainer().set(storedDamage, PersistentDataType.DOUBLE, player.getPersistentDataContainer().get(storedDamage, PersistentDataType.DOUBLE)-0.1);
            }
            else
            {
                player.getPersistentDataContainer().remove(storedDamage);
            }
        }
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        Player player = (Player) e.getDamager();

        if (player.isSneaking())
        {
            if (player.getPersistentDataContainer().has(storedDamage))
            {
                e.setDamage(e.getDamage() + player.getPersistentDataContainer().get(storedDamage, PersistentDataType.DOUBLE));
                player.getPersistentDataContainer().remove(storedDamage);
            }
        }
    }
}
