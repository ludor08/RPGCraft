package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class AuraOfWeakness extends ActiveTrait
{
    NamespacedKey weaknessKey = new NamespacedKey(main, "aura_of_weakness");
    NamespacedKey willTakeManaKey = new NamespacedKey(main, "aura_of_weakness_will_take_mana");

    public AuraOfWeakness(Main main) {
        // add the name and lore
        super("Aura Of Weakness", "aura of weakness", 0, ChatColor.AQUA, Material.PALE_HANGING_MOSS, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Weaken all entities within five blocks of you.",
                ChatColor.AQUA.toString() + "   - When activated this trait will stay active and consume five mana per second, until deactivated with the same input sequence."
        ));
    }

    @Override
    public String GetInputSequence()
    {
        return "101";
    }

    @Override
    public void OnTick(Player player)
    {
        if (player.getPersistentDataContainer().get(weaknessKey, PersistentDataType.BOOLEAN))
        {
            player.getPersistentDataContainer().set(willTakeManaKey, PersistentDataType.BOOLEAN, !player.getPersistentDataContainer().get(willTakeManaKey, PersistentDataType.BOOLEAN));

            if (player.getPersistentDataContainer().get(willTakeManaKey, PersistentDataType.BOOLEAN))
            {
                if (!(player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) >= 1))
                {
                    player.getPersistentDataContainer().set(weaknessKey, PersistentDataType.BOOLEAN, false);
                    return;
                }

                player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER)-1);
            }

            for (Entity entity : player.getNearbyEntities(5,5,5))
            {
                if (entity instanceof LivingEntity livingEntity)
                {
                    if (RPGutils.getDistance(player.getLocation(), entity.getLocation()) <= 5)
                    {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 2, 1));
                    }
                }
            }

            player.getWorld().spawnParticle(Particle.ASH, player.getLocation(), 100, 2,2,2);
        }
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        player.getPersistentDataContainer().set(weaknessKey, PersistentDataType.BOOLEAN, !player.getPersistentDataContainer().get(weaknessKey, PersistentDataType.BOOLEAN));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(weaknessKey))
        {
            player.getPersistentDataContainer().remove(weaknessKey);
        }

        if (player.getPersistentDataContainer().has(willTakeManaKey))
        {
            player.getPersistentDataContainer().remove(willTakeManaKey);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getPersistentDataContainer().set(weaknessKey, PersistentDataType.BOOLEAN, false);
        player.getPersistentDataContainer().set(willTakeManaKey, PersistentDataType.BOOLEAN, false);
    }
}
