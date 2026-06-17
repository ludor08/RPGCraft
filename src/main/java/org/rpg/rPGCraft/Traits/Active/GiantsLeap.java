package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class GiantsLeap extends ActiveTrait
{
    NamespacedKey noFallDamageTime = new NamespacedKey(Main.GetInstance(), "giants_leap_no_fall_damage_time");
    NamespacedKey giantsImpactKey = new NamespacedKey(Main.GetInstance(), "giants_impact");
    NamespacedKey cleavingImpactKey = new NamespacedKey(Main.GetInstance(), "cleaving_impact");
    NamespacedKey incineratingImpactKey = new NamespacedKey(Main.GetInstance(), "incinerating_impact");

    float jumpPowerMod = 2;

    public GiantsLeap() {
        // add the name and lore
        super(ChatColor.RED + ChatColor.BOLD.toString() + "Giants Leap", "giants leap", 15, Material.LEATHER_BOOTS, true, List.of(
                ChatColor.AQUA.toString() + "   - Upon activating this trait you will leap forward."
        ));


    }

    @Override
    public String GetInputSequence()
    {
        return "010";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        Vector3d direction = RPGutils.getFacingDirection(player);

        RPGparticles.SpawnBlockParticle(300, player.getLocation().add(0, 0.1, 0), new Vector3d(0.375,0,0.375), player.getLocation().add(new Vector(0,-1,0)).getBlock().getBlockData(), 1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1, 1.7f, 1);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, SoundCategory.PLAYERS, 1, 2f, 1);

        direction.y = Math.max(0.25, direction.y);
        direction.mul(jumpPowerMod);

        player.setVelocity(Vector.fromJOML(direction));
        player.getPersistentDataContainer().set(noFallDamageTime, PersistentDataType.INTEGER, 50);
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        Main main = Main.GetInstance();

        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            if (e.getEntity().getPersistentDataContainer().has(noFallDamageTime))
            {
                if (e.getEntity().getPersistentDataContainer().has(giantsImpactKey))
                {
                    List<Entity> shockwavedEntitys = e.getEntity().getNearbyEntities(2,0.5, 2);
                    RPGparticles.SpawnBlockParticle(1000, e.getEntity().getLocation().add(0, 0.1, 0), new Vector3d(1,0.025,1), e.getEntity().getLocation().add(new Vector(0,-1,0)).getBlock().getBlockData(), 1);
                    e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 3, 0.75f, 1);
                    e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 3, 1.25f, 1);
                    e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 3, 1f, 1);

                    if (e.getEntity().getPersistentDataContainer().has(incineratingImpactKey))
                    {
                        e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation().add(0, 1, 0), 250, 1,0.5,1, 0);
                        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.PLAYERS, 3, 1, 1);
                    }

                    if (e.getEntity().getPersistentDataContainer().has(cleavingImpactKey))
                    {
                        e.getEntity().getWorld().spawnParticle(Particle.CRIT, e.getEntity().getLocation().add(0, 1, 0), 250, 1,0.5,1, 0);
                        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 3, 1, 1);
                    }

                    for (Entity shockwavedEntity : shockwavedEntitys)
                    {
                        if (main.partyManager.ShouldHitBeStoppedByParty(e.getEntity(), shockwavedEntity))
                        {
                            continue;
                        }

                        if (shockwavedEntity instanceof LivingEntity livingEntity)
                        {
                            if (e.getEntity().getPersistentDataContainer().has(incineratingImpactKey))
                            {
                                RPGutils.DamageWithTrait(livingEntity, e.getEntity(), 2, false);

                                if (livingEntity.getFireTicks() < 100)
                                {
                                    livingEntity.setFireTicks(100);
                                }
                            }

                            if (e.getEntity().getPersistentDataContainer().has(cleavingImpactKey))
                            {
                                RPGutils.AttackWithTrait(shockwavedEntity, (LivingEntity) e.getEntity(), false);

                                e.getEntity().getWorld().playSound(shockwavedEntity.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 3, 1, 1);
                            }

                            RPGutils.DamageWithTrait(livingEntity, e.getEntity(), (int) (e.getDamage()/2), false);
                        }
                    }
                }

                e.setCancelled(true);
                e.getEntity().getPersistentDataContainer().remove(noFallDamageTime);
            }

        }
    }

    @Override
    public void OnTick(Player player)
    {
        if (player.getPersistentDataContainer().has(noFallDamageTime))
        {
            RPGutils.AddToNamespacedKey(player, noFallDamageTime, 0, -1);

            if (player.getPersistentDataContainer().get(noFallDamageTime, PersistentDataType.INTEGER) <= 0)
            {
                player.getPersistentDataContainer().remove(noFallDamageTime);
            }
        }
    }
}
