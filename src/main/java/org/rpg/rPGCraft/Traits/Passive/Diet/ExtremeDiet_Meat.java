package org.rpg.rPGCraft.Traits.Passive.Diet;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class ExtremeDiet_Meat extends Trait
{

    public ExtremeDiet_Meat(Main main) {
        // add the name and lore
        super("Extreme Diet:Meat", "extreme diet:meat", ChatColor.AQUA, Material.MUTTON, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Gains 25% the saturation and nutrition from foods other than meat",
                ChatColor.AQUA.toString() + "   - Meat also gives 150% the saturation."
        ));
    }

    @Override
    public void OnPlayerItemConsume(PlayerItemConsumeEvent e)
    {
        // the change in food level
        int nutritionLevelChange = e.getItem().getItemMeta().getFood().getNutrition();
        float saturationLevelChange = e.getItem().getItemMeta().getFood().getSaturation();

        float BAD_FOOD_MULTIPLIER = 0.25f;
        float GOOD_FOOD_MULTIPLIER = 1.5f;

        Player player = e.getPlayer();

        // if they didn't eat meat
        if (!main.itemManager.GetMeatTypes().contains(e.getItem().getType())) {
            player.setFoodLevel((int) (nutritionLevelChange * BAD_FOOD_MULTIPLIER) + player.getFoodLevel() - nutritionLevelChange);
            player.setSaturation((saturationLevelChange * BAD_FOOD_MULTIPLIER) + player.getSaturation() - saturationLevelChange);
        }
        else
        {
            player.setFoodLevel((int) (nutritionLevelChange * GOOD_FOOD_MULTIPLIER) + player.getFoodLevel() - nutritionLevelChange);
            player.setSaturation((saturationLevelChange * GOOD_FOOD_MULTIPLIER) + player.getSaturation() - saturationLevelChange);
        }
    }
}
