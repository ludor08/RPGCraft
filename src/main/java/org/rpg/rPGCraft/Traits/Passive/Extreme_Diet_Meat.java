package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Extreme_Diet_Meat extends Trait
{

    public Extreme_Diet_Meat(Main main) {
        // add the name and lore
        super("Extreme Diet:Meat", "extreme diet:meat", ChatColor.AQUA, Material.MUTTON, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Do not gain saturation from foods other than meat",
                ChatColor.AQUA.toString() + "   - Meat also gives 1.5x the saturation."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {

    }

    @Override
    public void OnTick(Player player) {

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
        // if the player ate
        if (e.getItem() != null)
        {
            float GOOD_FOOD_MULTIPLYER = 1.5f;

            Player player = (Player) e.getEntity();

            // the change in food level
            int levelChange = e.getFoodLevel() - player.getFoodLevel();

            // if they ate raw fish
            if (main.gameManager.GetMeatTypes().contains(e.getItem().getType())) {
                // multiply the food gained by the GOOD_FOOD_MULTIPLYER
                e.setFoodLevel((int) (levelChange * GOOD_FOOD_MULTIPLYER) + player.getFoodLevel());
            }
            // if not
            else
            {
                // cancel the event
                e.setCancelled(true);
            }
        }
    }
}
