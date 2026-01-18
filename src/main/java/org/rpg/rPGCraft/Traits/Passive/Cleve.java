package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class Cleve extends Trait
{
    NamespacedKey canNotCleveKey = new NamespacedKey(Main.GetInstance(), "can_not_cleve");

    public Cleve() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Cleve", "cleve", Material.IRON_AXE, true, List.of(
                ChatColor.AQUA.toString() + "   - Swing your weapon with a powerful cleaving motion in front of you,",
                ChatColor.AQUA.toString() + "     hitting all entities in front of you."
        ));
    }

    @Override
    public void OnTick(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, canNotCleveKey);
    }

    @Override
    public void OnClick(PlayerInteractEvent e)
    {
        if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.LEFT_CLICK_AIR)
        {
            Location location = RPGraycast.Recast(2, RPGutils.getFacingDirection(e.getPlayer()), e.getPlayer().getEyeLocation(), false, null, 0);

            CleveEnemies(List.of(), location, e.getPlayer());
        }
    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        Location location = RPGraycast.Recast(2, RPGutils.getFacingDirection(e.getDamager()), ((LivingEntity)e.getDamager()).getEyeLocation(), false, null, 0);

        CleveEnemies(List.of(), location, ((Player)e.getDamager()));
    }

    private void CleveEnemies(List<Entity> exceptions, Location location, Player player)
    {
        if (!player.getPersistentDataContainer().has(canNotCleveKey))
        {
            RPGutils.SetNamespacedKeyValue(player, canNotCleveKey, true);

            List<Entity> clevedEnemies = location.getWorld().getNearbyEntities(location, 2,2,2).stream().toList();

            for (Entity clevedEntity : clevedEnemies)
            {
                if (Main.GetInstance().partyManager.IsInTheSameParty(player, clevedEntity))
                {
                    continue;
                }

                if (!exceptions.contains(clevedEntity) && clevedEntity != player)
                {
                    if (clevedEntity instanceof LivingEntity living)
                    {
                        RPGutils.AttackWithTrait(living, player, false);
                    }
                }
            }
        }
    }
}
