package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Charge extends Trait
{
    NamespacedKey attackInputCanceledKey = new NamespacedKey(main, "attack_input_canceled");
    NamespacedKey momentumKey = new NamespacedKey(main, "charge_momentum");
    NamespacedKey chargeDamageKey = new NamespacedKey(main, "charge_damage");
    int baseChargeDamage = 2;

    NamespacedKey speedModKey = new NamespacedKey(main, "charge_speed_mod");

    NamespacedKey shieldChargeDamageModKey = new NamespacedKey(main, "shield_charge_damage_mod");
    NamespacedKey flameChargeKey = new NamespacedKey(main, "flame_charge");

    public Charge(Main main) {
        // add the name and lore
        super("Charge", "charge", ChatColor.AQUA, Material.BLAZE_POWDER, true, main, List.of(
                ChatColor.AQUA.toString() + "   - After sprinting forward for 3 seconds, start charging",
                ChatColor.AQUA.toString() + "     dealing 2 damage to any entity that you run into and move 10% faster."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, momentumKey, 0f);
        RPGutils.AddToNamespacedKey(player, speedModKey, 0, 1.1f);
        RPGutils.AddToNamespacedKey(player, chargeDamageKey, 0, baseChargeDamage);
    }

    @Override
    public void OnTick(Player player)
    {
        if (player.getForwardsMovement() == 1 && player.isSprinting())
        {
            RPGutils.AddToNamespacedKey(player, momentumKey, 0f, 1f);

            if (player.getPersistentDataContainer().get(momentumKey, PersistentDataType.FLOAT) > 30 && player.getAttribute(Attribute.MOVEMENT_SPEED).getModifier(new NamespacedKey(main, "charge_move_mod")) == null)
            {
                RPGutils.SafeAttributeAdd(Attribute.MOVEMENT_SPEED, new AttributeModifier(new NamespacedKey(main, "charge_move_mod"), player.getPersistentDataContainer().get(speedModKey,PersistentDataType.FLOAT), AttributeModifier.Operation.ADD_SCALAR), player);
            }

            if (player.getAttribute(Attribute.MOVEMENT_SPEED).getModifier(new NamespacedKey(main, "charge_move_mod")) != null)
            {
                List<Entity> entities = player.getNearbyEntities(1,1,1);

                for (Entity entity : entities)
                {
                    if (entity instanceof LivingEntity livingEntity)
                    {
                        int damage = player.getPersistentDataContainer().get(chargeDamageKey, PersistentDataType.INTEGER);

                        if (player.getPersistentDataContainer().has(shieldChargeDamageModKey))
                        {
                            if (player.getInventory().getItem(EquipmentSlot.HAND).getType() == Material.SHIELD && player.getInventory().getItem(EquipmentSlot.OFF_HAND).getType() == Material.SHIELD)
                            {
                                damage += player.getPersistentDataContainer().get(shieldChargeDamageModKey, PersistentDataType.INTEGER);
                            }
                        }

                        if (player.getPersistentDataContainer().has(flameChargeKey))
                        {
                            if (livingEntity.getFireTicks() < 100)
                            {
                                livingEntity.setFireTicks(100);
                            }
                        }

                        RPGutils.SetNamespacedKeyValue(player, attackInputCanceledKey, true);
                        livingEntity.damage(damage, player);
                        RPGutils.RemoveNamespacedKey(player, attackInputCanceledKey);

                        player.setSprinting(true);
                    }
                }
            }
        }
        else
        {
            RPGutils.SetNamespacedKeyValue(player, momentumKey, 0f);

            if (player.getAttribute(Attribute.MOVEMENT_SPEED).getModifier(new NamespacedKey(main, "charge_move_mod")) != null)
            {
                RPGutils.SafeAttributeRemove(Attribute.MOVEMENT_SPEED, player.getAttribute(Attribute.MOVEMENT_SPEED).getModifier(new NamespacedKey(main, "charge_move_mod")), player);
            }
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getAttribute(Attribute.MOVEMENT_SPEED).removeModifier(new NamespacedKey(main, "charge_move_mod"));

        RPGutils.RemoveNamespacedKey(player, momentumKey);
        RPGutils.AddToNamespacedKey(player, speedModKey, 0, -1.1f);
        RPGutils.AddToNamespacedKey(player, chargeDamageKey, 0, -baseChargeDamage);
    }
}
