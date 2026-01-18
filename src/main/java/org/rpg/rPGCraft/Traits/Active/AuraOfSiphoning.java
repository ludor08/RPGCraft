package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class AuraOfSiphoning extends ActiveTrait
{
    NamespacedKey siphoningKey = new NamespacedKey(Main.GetInstance(), "aura_of_siphoning");
    NamespacedKey siphoningTickKey = new NamespacedKey(Main.GetInstance(), "aura_of_siphoning_tick");

    int manaCost = 5;

    NamespacedKey weaknessLevelKey = new NamespacedKey(Main.GetInstance(), "aura_of_weakness_level");

    public AuraOfSiphoning() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Aura Of Siphoning", "aura of siphoning", 40, Material.PALE_HANGING_MOSS, true, List.of(
                ChatColor.AQUA.toString() + "   - Siphon mana from all entities within five blocks of you at a 2 to 1 ratio.",
                ChatColor.AQUA.toString() + "   - When activated this trait will stay active and consume 5 mana per second, until deactivated with the same input sequence."
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
        Main main = Main.GetInstance();

        if (player.getPersistentDataContainer().get(siphoningKey, PersistentDataType.BOOLEAN))
        {
            player.getPersistentDataContainer().set(siphoningTickKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(siphoningTickKey, PersistentDataType.INTEGER)+1);

            if (player.getPersistentDataContainer().get(siphoningTickKey, PersistentDataType.INTEGER) >= 10)
            {
                if (player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) < manaCost)
                {
                    player.getPersistentDataContainer().set(siphoningKey, PersistentDataType.BOOLEAN, false);
                    return;
                }

                for (Entity entity : player.getNearbyEntities(5,5,5))
                {
                    if (entity instanceof LivingEntity livingEntity)
                    {
                        if (main.partyManager.IsInTheSameParty(player, livingEntity))
                        {
                            continue;
                        }

                        if (RPGutils.getDistance(player.getLocation(), entity.getLocation()) > 5)
                        {
                            continue;
                        }

                        RPGutils.DamageWithTrait(livingEntity, player, 1, false);

                        if (livingEntity.getPersistentDataContainer().has(main.GetManaKey()))
                        {
                            if (livingEntity.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) > 1)
                            {
                                player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, Math.min(player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER)+1,player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER)));
                                livingEntity.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER)-2);
                            }
                        }
                        else
                        {
                            player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, Math.min(player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER)+1,player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER)));
                        }

                        if (player.getPersistentDataContainer().has(weaknessLevelKey))
                        {
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 2, player.getPersistentDataContainer().get(weaknessLevelKey, PersistentDataType.INTEGER)-1));
                        }
                    }
                }

                player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER)-manaCost);
            }

            player.getWorld().spawnParticle(Particle.ASH, player.getLocation(), 100, 2,2,2);
        }
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        player.getPersistentDataContainer().set(siphoningKey, PersistentDataType.BOOLEAN, !player.getPersistentDataContainer().get(siphoningKey, PersistentDataType.BOOLEAN));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(siphoningKey))
        {
            player.getPersistentDataContainer().remove(siphoningKey);
        }

        if (player.getPersistentDataContainer().has(siphoningTickKey))
        {
            player.getPersistentDataContainer().remove(siphoningTickKey);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getPersistentDataContainer().set(siphoningKey, PersistentDataType.BOOLEAN, false);
        player.getPersistentDataContainer().set(siphoningTickKey, PersistentDataType.INTEGER, 0);
    }
}
