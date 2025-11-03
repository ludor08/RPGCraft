package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class GiantsLeap extends ActiveTrait
{
    NamespacedKey noFallDamageTime = new NamespacedKey(main, "giants_leap_no_fall_damage_time");
    NamespacedKey giantsImpactKey = new NamespacedKey(main, "giants_impact");
    NamespacedKey cleavingImpactKey = new NamespacedKey(main, "cleaving_impact");
    NamespacedKey incineratingImpactKey = new NamespacedKey(main, "incinerating_impact");

    NamespacedKey attackInputCanceledKey = new NamespacedKey(main, "attack_input_canceled");

    float jumpPowerMod = 2;

    public GiantsLeap(Main main) {
        // add the name and lore
        super("Giants Leap", "giants leap", 15, ChatColor.RED, Material.LEATHER_BOOTS, true, main, List.of(
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

        direction.y = Math.max(0.25, direction.y);

        direction.mul(jumpPowerMod);

        player.setVelocity(Vector.fromJOML(direction));
        player.getPersistentDataContainer().set(noFallDamageTime, PersistentDataType.INTEGER, 50);
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            if (e.getEntity().getPersistentDataContainer().has(noFallDamageTime))
            {
                if (e.getEntity().getPersistentDataContainer().has(giantsImpactKey))
                {
                    List<Entity> shockwavedEntitys = e.getEntity().getNearbyEntities(2,0.5, 2);

                    for (Entity shockwavedEntity : shockwavedEntitys)
                    {
                        if (shockwavedEntity instanceof LivingEntity livingShockwavedEntity)
                        {
                            if (e.getEntity().getPersistentDataContainer().has(incineratingImpactKey))
                            {
                                RPGutils.SetNamespacedKeyValue(e.getEntity(), attackInputCanceledKey, true);
                                livingShockwavedEntity.damage(2, e.getEntity());
                                RPGutils.RemoveNamespacedKey(e.getEntity(), attackInputCanceledKey);

                                if (livingShockwavedEntity.getFireTicks() < 100)
                                {
                                    livingShockwavedEntity.setFireTicks(100);
                                }
                            }

                            if (!e.getEntity().getPersistentDataContainer().has(cleavingImpactKey))
                            {
                                RPGutils.SetNamespacedKeyValue(e.getEntity(), attackInputCanceledKey, true);
                                livingShockwavedEntity.damage(e.getDamage()/2, e.getEntity());
                                RPGutils.RemoveNamespacedKey(e.getEntity(), attackInputCanceledKey);
                            }

                            RPGutils.SetNamespacedKeyValue(e.getEntity(), attackInputCanceledKey, true);
                            ((LivingEntity)e.getEntity()).attack(shockwavedEntity);
                            RPGutils.RemoveNamespacedKey(e.getEntity(), attackInputCanceledKey);

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
