package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class AbnormalDiet_Berries extends Trait
{

    public AbnormalDiet_Berries(Main main) {
        // add the name and lore
        super("Abnormal Diet:Berries", "abnormal diet:Berries", ChatColor.AQUA, Material.SWEET_BERRIES, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Food other then berries only gives 0.75% saturation.",
                ChatColor.AQUA.toString() + "   - Berries also give 2.5x the saturation."
        ));
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
        // if the player ate
        if (e.getItem() != null)
        {
            float BERRY_MULTIPLYER = 2.5f;
            float BAD_FOOD_MULTIPLYER = 0.75f;


            Player player = (Player) e.getEntity();

            // the change in food level
            int levelChange = e.getFoodLevel() - player.getFoodLevel();

            // if they ate berry
            if (e.getItem().getType().equals(Material.SWEET_BERRIES) ||
                    e.getItem().getType().equals(Material.GLOW_BERRIES)) {
                // multiply the food gained by the BERRY_MULTIPLYER
                e.setFoodLevel((int) (levelChange * BERRY_MULTIPLYER) + player.getFoodLevel());
            }
            // if not
            else
            {
                // multiply the food gained by the BAD_FOOD_MULTIPLYER
                e.setFoodLevel((int) (levelChange * BAD_FOOD_MULTIPLYER) + player.getFoodLevel());
            }
        }
    }
}
