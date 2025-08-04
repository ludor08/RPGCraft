package org.rpg.rPGCraft.Traits.Passive.Diet;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class AbnormalDiet_Berries extends Trait
{

    public AbnormalDiet_Berries(Main main) {
        // add the name and lore
        super("Abnormal Diet:Berries", "abnormal diet:Berries", ChatColor.AQUA, Material.SWEET_BERRIES, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Food other then berries only gives 75% saturation.",
                ChatColor.AQUA.toString() + "   - Berries also give 2.5x the saturation."
        ));
    }

    @Override
    public void OnPlayerItemConsume(PlayerItemConsumeEvent e)
    {
        // the change in food level
        int nutritionLevelChange = e.getItem().getItemMeta().getFood().getNutrition();
        float saturationLevelChange = e.getItem().getItemMeta().getFood().getSaturation();

        float BAD_FOOD_MULTIPLIER = 0.75f;
        float GOOD_FOOD_MULTIPLIER = 2.5f;

        Player player = e.getPlayer();

        // if they didn't eat a berry
        if (!e.getItem().getType().equals(Material.SWEET_BERRIES) &&
                !e.getItem().getType().equals(Material.GLOW_BERRIES)) {
            player.setFoodLevel((int) (nutritionLevelChange * BAD_FOOD_MULTIPLIER - nutritionLevelChange) + player.getFoodLevel());
            player.setSaturation((saturationLevelChange * BAD_FOOD_MULTIPLIER - saturationLevelChange) + player.getSaturation());
        }
        else
        {
            player.setFoodLevel((int) (nutritionLevelChange * GOOD_FOOD_MULTIPLIER - nutritionLevelChange) + player.getFoodLevel());
            player.setSaturation((saturationLevelChange * GOOD_FOOD_MULTIPLIER - saturationLevelChange) + player.getSaturation());
        }
    }
}
