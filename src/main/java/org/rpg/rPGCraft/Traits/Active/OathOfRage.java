package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class OathOfRage extends ActiveTrait
{
    AttributeModifier damageMod = new AttributeModifier(new NamespacedKey(main, "power_of_the_oak_queen_size_mod"), 1.2, AttributeModifier.Operation.ADD_SCALAR);
    AttributeModifier armorMod = new AttributeModifier(new NamespacedKey(main, "power_of_the_oak_queen_damage_mod"), 2, AttributeModifier.Operation.ADD_NUMBER);

    NamespacedKey rageTimerKey = new NamespacedKey(main, "rage_timer");

    NamespacedKey rageDurationKey = new NamespacedKey(main, "rage_duration");
    int baseRageDuration = 300;

    NamespacedKey rejuvenatingRageKey = new NamespacedKey(main, "rejuvenating_rage");
    NamespacedKey bloodlustRageKey = new NamespacedKey(main, "bloodlust_rage");

    public OathOfRage(Main main) {
        // add the name and lore
        super("Oath of Rage","oath of rage", 60, ChatColor.AQUA, Material.REDSTONE, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Gain two more armor and boost your damage by 20% for 30 seconds"
        ));
    }

    @Override
    public String GetInputSequence()
    {
        return "001";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        if (!player.getPersistentDataContainer().has(rageTimerKey))
        {
            if (player.getPersistentDataContainer().has(rejuvenatingRageKey))
            {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, player.getPersistentDataContainer().get(rageDurationKey, PersistentDataType.INTEGER)*2,0));
            }

            RPGutils.SetNamespacedKeyValue(player, rageTimerKey, player.getPersistentDataContainer().get(rageDurationKey, PersistentDataType.INTEGER));

            RPGutils.SafeAttributeAdd(Attribute.ATTACK_DAMAGE, damageMod, player);
            RPGutils.SafeAttributeAdd(Attribute.ARMOR, armorMod, player);

            for (int i = 0; i < 20; i++)
            {
                Vector3d offset = new Vector3d(Math.cos((Math.PI*2)/((double) i /20)), 0, Math.sin((Math.PI*2)/((double) i /20)));
                Location location = new Location(player.getWorld(), player.getLocation().getX() + offset.x, player.getLocation().getY() + offset.y, player.getLocation().getZ() + offset.z);

                player.getWorld().spawnParticle(Particle.RAID_OMEN, location, 10, 0,0,0,2);
            }
        }
        else
        {
            player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) + GetModifiedCost(player));
        }
    }

    @Override
    public void OnTick(Player player)
    {
        if (player.getPersistentDataContainer().has(rageTimerKey))
        {
            if (player.getPersistentDataContainer().get(rageTimerKey, PersistentDataType.INTEGER) > 0)
            {
                RPGutils.AddToNamespacedKey(player, rageTimerKey, 0, -1);
            }
            else if (player.getPersistentDataContainer().get(rageTimerKey, PersistentDataType.INTEGER) <= 0)
            {
                player.getAttribute(Attribute.ATTACK_DAMAGE).removeModifier(damageMod);
                player.getAttribute(Attribute.ARMOR).removeModifier(armorMod);

                RPGutils.RemoveNamespacedKey(player, rageTimerKey);
                player.sendMessage(ChatColor.GREEN + "You are no longer raging.");
            }
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, rageTimerKey);

        RPGutils.SafeAttributeRemove(Attribute.ATTACK_DAMAGE, damageMod, player);
        RPGutils.SafeAttributeRemove(Attribute.ARMOR, armorMod, player);

        RPGutils.AddToNamespacedKey(player, rageDurationKey, 0, -baseRageDuration);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, rageTimerKey, 0);
        RPGutils.AddToNamespacedKey(player, rageDurationKey, 0, baseRageDuration);
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        if (e.getDamager().getPersistentDataContainer().has(bloodlustRageKey)
                && e.getDamager().getPersistentDataContainer().has(rageTimerKey))
        {
            if (e.getDamager() instanceof LivingEntity living)
            {
                RPGutils.HealWithTraits(e.getDamager(), living, (int) Math.floor(e.getDamage()*0.1), EntityRegainHealthEvent.RegainReason.REGEN,main);
            }
        }
    }
}
