package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class OathOfRage extends ActiveTrait
{
    AttributeModifier damageMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "power_of_the_oak_queen_size_mod"), 1.2, AttributeModifier.Operation.ADD_SCALAR);
    AttributeModifier armorMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "power_of_the_oak_queen_damage_mod"), 2, AttributeModifier.Operation.ADD_NUMBER);

    NamespacedKey rageTimerKey = new NamespacedKey(Main.GetInstance(), "rage_timer");

    NamespacedKey rageDurationKey = new NamespacedKey(Main.GetInstance(), "rage_duration");
    int baseRageDuration = 300;

    NamespacedKey rejuvenatingRageKey = new NamespacedKey(Main.GetInstance(), "rejuvenating_rage");
    NamespacedKey bloodlustRageKey = new NamespacedKey(Main.GetInstance(), "bloodlust_rage");

    public OathOfRage() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Oath of Rage","oath of rage", 60, Material.REDSTONE, true, List.of(
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

            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_POLAR_BEAR_WARNING, SoundCategory.PLAYERS, 3, 0.5f, 1);
        }
        else
        {
            player.getPersistentDataContainer().set(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER) + GetModifiedCost(player));
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
                RPGparticles.SpawnParticle(3, player.getLocation().add(0, player.getHeight()/2, 0), new Vector3d(1, 1, 1), Particle.RAID_OMEN, 1);

                if (player.getPersistentDataContainer().has(rejuvenatingRageKey))
                {
                    RPGparticles.SpawnParticle(1, player.getLocation().add(0, player.getHeight()/2, 0), new Vector3d(1, 1, 1), Particle.HEART, 1);
                }
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
                RPGutils.HealWithTrait(e.getDamager(), living, (int) Math.floor(e.getDamage()*0.1), EntityRegainHealthEvent.RegainReason.REGEN);

                RPGparticles.SpawnBlockParticle(5*((int) e.getDamage()/3), e.getEntity().getLocation().add(0,e.getEntity().getHeight()/2, 0), new Vector3d(0.0625, 0.0625,0.0625), BlockType.REDSTONE_BLOCK.createBlockData(), 1);
            }
        }
    }
}
