package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Piezoelectric extends Trait
{
    private final NamespacedKey storedDamage;
    private final List<EntityDamageEvent.DamageCause> physicalDamageCauses = List.of(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, EntityDamageEvent.DamageCause.ENTITY_ATTACK, EntityDamageEvent.DamageCause.FALL, EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION,
            EntityDamageEvent.DamageCause.FALLING_BLOCK, EntityDamageEvent.DamageCause.CONTACT, EntityDamageEvent.DamageCause.FLY_INTO_WALL, EntityDamageEvent.DamageCause.PROJECTILE, EntityDamageEvent.DamageCause.THORNS);

    public Piezoelectric(Main main)
    {
        // add the name and lore
        super("Piezoelectric", "toxic spores", ChatColor.AQUA, Material.FIREWORK_STAR, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Upon being hit by a physical attack, half of the",
                ChatColor.AQUA.toString() + "     damage will be stored within you. If you hit a",
                ChatColor.AQUA.toString() + "     creature well crouching, all of this stored",
                ChatColor.AQUA.toString() + "     damage will be transferred into the hit.",
                ChatColor.AQUA.toString() + "     This stored damage will slowly fade."
        ));

        storedDamage = new NamespacedKey(main, "piezoelectric_stored_damage");
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

                player.sendMessage(player.getPersistentDataContainer().get(storedDamage, PersistentDataType.DOUBLE)+"");
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
