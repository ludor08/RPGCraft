package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class Combo extends Trait
{
    NamespacedKey lacerate = new NamespacedKey(Main.GetInstance(), "lacerate");

    NamespacedKey comboCount = new NamespacedKey(Main.GetInstance(), "combo_count");
    NamespacedKey comboTimer = new NamespacedKey(Main.GetInstance(), "combo_timer");

    NamespacedKey comboDamageScalerKey = new NamespacedKey(Main.GetInstance(), "combo_damage_scaler");
    int baseComboDamageScaler = 1;

    public Combo() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Combo", "combo", Material.CHAIN, true, List.of(
                ChatColor.AQUA.toString() + "   - Every time you hit a creature with a weapon you gain one combo.",
                ChatColor.AQUA.toString() + "   - You do plus 1% for every combo that you have.",
                ChatColor.AQUA.toString() + "   - Your combo reset when you take damage or if you go 3 seconds without gaining more combo."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        if (player.getPersistentDataContainer().get(comboTimer, PersistentDataType.FLOAT) > 0)
        {
            player.getPersistentDataContainer().set(comboTimer, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(comboTimer, PersistentDataType.FLOAT)-0.1f);
        }
        else
        {
            if (player.getPersistentDataContainer().get(comboCount, PersistentDataType.INTEGER) > 0)
            {
                player.sendMessage("You lost your combo of " + player.getPersistentDataContainer().get(comboCount, PersistentDataType.INTEGER) + ".");
                player.getPersistentDataContainer().set(comboCount, PersistentDataType.INTEGER, 0);
            }
        }
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE || Main.GetInstance().itemManager.GetWeaponTypes().contains(((Player)e.getDamager()).getInventory().getItem(EquipmentSlot.HAND).getType()))
        {
            e.setDamage(e.getDamage() * (1 + (e.getDamager().getPersistentDataContainer().get(comboCount, PersistentDataType.INTEGER) / 100)));

            e.getDamager().getPersistentDataContainer().set(comboTimer, PersistentDataType.FLOAT, 3f);
            e.getDamager().getPersistentDataContainer().set(comboCount, PersistentDataType.INTEGER, e.getDamager().getPersistentDataContainer().get(comboCount, PersistentDataType.INTEGER)+1);
            e.getDamager().sendMessage("Combo : " + e.getDamager().getPersistentDataContainer().get(comboCount, PersistentDataType.INTEGER));

            if (e.getDamager().getPersistentDataContainer().has(lacerate))
            {
                if (e.getEntity() instanceof LivingEntity living)
                {
                    if (e.getDamager().getPersistentDataContainer().get(comboCount, PersistentDataType.INTEGER) % 3 == 0)
                    {
                        int lacerateDamage = (int) Math.min(10, Math.floor(living.getAttribute(Attribute.MAX_HEALTH).getValue()/10));

                        e.setDamage(e.getDamage() + lacerateDamage);
                    }
                }

            }
        }
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (e.isCancelled())
        {
            return;
        }

        if (e.getEntity().getPersistentDataContainer().get(comboCount, PersistentDataType.INTEGER) > 0)
        {
            e.getEntity().sendMessage("You lost your combo of " + e.getEntity().getPersistentDataContainer().get(comboCount, PersistentDataType.INTEGER) + ".");

            e.getEntity().getPersistentDataContainer().set(comboTimer, PersistentDataType.FLOAT, 0f);
            e.getEntity().getPersistentDataContainer().set(comboCount, PersistentDataType.INTEGER, 0);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getPersistentDataContainer().set(comboTimer, PersistentDataType.FLOAT, 0f);
        player.getPersistentDataContainer().set(comboCount, PersistentDataType.INTEGER, 0);

        if (player.getPersistentDataContainer().has(comboDamageScalerKey))
        {
            player.getPersistentDataContainer().set(comboDamageScalerKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(comboDamageScalerKey, PersistentDataType.INTEGER) + baseComboDamageScaler);
        }
        else
        {
            player.getPersistentDataContainer().set(comboDamageScalerKey, PersistentDataType.INTEGER, baseComboDamageScaler);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getPersistentDataContainer().remove(comboTimer);
        player.getPersistentDataContainer().remove(comboCount);

        if (player.getPersistentDataContainer().has(comboDamageScalerKey))
        {
            player.getPersistentDataContainer().set(comboDamageScalerKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(comboDamageScalerKey, PersistentDataType.INTEGER) - baseComboDamageScaler);
        }
    }
}
