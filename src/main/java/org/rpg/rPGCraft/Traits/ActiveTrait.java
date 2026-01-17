package org.rpg.rPGCraft.Traits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;

import java.util.List;

public abstract class ActiveTrait extends Trait
{
    private final int cost;

    public ActiveTrait(String name, String name_id, int cost, Material iconMaterial, boolean tickTrait, List<String> lore)
    {
        super(name, name_id, iconMaterial, tickTrait, lore);

        this.cost = cost;
    }

    public abstract String GetInputSequence();

    public void OnInputSequence(Player player)
    {
        // if the player has the needed mana
        if (player.getPersistentDataContainer().get(Main.GetInstance().GetManaKey(), PersistentDataType.INTEGER) - GetModifiedCost(player) >= 0)
        {
            TriggerActiveEvent(player);

            player.getPersistentDataContainer().set(Main.GetInstance().GetManaKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(Main.GetInstance().GetManaKey(), PersistentDataType.INTEGER) - GetModifiedCost(player));
        }
        // if not
        else
        {
            player.sendMessage(ChatColor.RED + "You do not have enough mana to use " + name + ChatColor.RED + ". " + "You need " + GetModifiedCost(player) + " mana");
        }
    }

    public abstract void TriggerActiveEvent(Player player);

    public int GetCost()
    {
        return cost;
    }

    public int GetModifiedCost(Player player)
    {
        // get the modifier price
        int newCost = cost;

        for (Trait trait : Main.GetInstance().statSheetManager.FindStatSheetByPlayer(player).GetActiveTraits())
        {
            if (trait instanceof CostModifierTrait costModifier && costModifier.GetModifiedTraitID().equals(name_id))
            {
                newCost += costModifier.GetCostModifier();
            }
        }

        return newCost;
    }
}
