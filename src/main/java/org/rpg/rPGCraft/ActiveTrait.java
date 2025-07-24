package org.rpg.rPGCraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public abstract class ActiveTrait extends Trait
{
    private int cost;

    public ActiveTrait(String name, String name_id, int cost, ChatColor nameColor, Material iconMaterial, boolean tickTrait, Main main, List<String> lore)
    {
        super(name, name_id, nameColor, iconMaterial, tickTrait, main, lore);

        this.cost = cost;
    }

    public abstract String GetInputSequence();

    public void OnInputSequence(Player player)
    {
        // if the player has the needed mana
        if (player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) - GetCost() > 0)
        {
            TriggerActiveEvent(player);

            player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) - GetCost());
        }
    }

    public abstract void TriggerActiveEvent(Player player);

    public int GetCost()
    {
        return cost;
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {

    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {

    }

    @Override
    public void OnRespawnBuffs(PlayerRespawnEvent e)
    {

    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {

    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {

    }

    @Override
    public void OnFoodLevelChange(FoodLevelChangeEvent e)
    {

    }
}
