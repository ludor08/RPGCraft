package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class AbnormalDiet_Berries extends Trait
{

    public AbnormalDiet_Berries() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Abnormal Diet:Berries", "abnormal diet:berries", Material.SWEET_BERRIES, false, List.of(
                ChatColor.AQUA.toString() + "   - Food other then berries only gives 75% saturation and nutrition.",
                ChatColor.AQUA.toString() + "   - Berries also give 2.5x the nutrition, and 5x the saturation."
        ));
    }

    @Override
    public void OnPlayerItemConsume(PlayerItemConsumeEvent e)
    {
        // the change in food level
        int nutritionLevelChange = e.getItem().getItemMeta().getFood().getNutrition();
        float saturationLevelChange = e.getItem().getItemMeta().getFood().getSaturation();

        float BAD_FOOD_MULTIPLIER = 0.75f;
        float GOOD_NUTRITION_MULTIPLIER = 2.5f;
        float GOOD_SATURATION_MULTIPLIER = 5f;

        Player player = e.getPlayer();

        // if they didn't eat a berry
        if (!e.getItem().getType().equals(Material.SWEET_BERRIES) &&
                !e.getItem().getType().equals(Material.GLOW_BERRIES)) {
            player.setFoodLevel((int) (nutritionLevelChange * BAD_FOOD_MULTIPLIER) + player.getFoodLevel() - nutritionLevelChange);
            player.setSaturation((saturationLevelChange * BAD_FOOD_MULTIPLIER) + player.getSaturation() - saturationLevelChange);
        }
        else
        {
            player.setFoodLevel((int) (nutritionLevelChange * GOOD_NUTRITION_MULTIPLIER) + player.getFoodLevel() - nutritionLevelChange);
            player.setSaturation((saturationLevelChange * GOOD_SATURATION_MULTIPLIER) + player.getSaturation() - saturationLevelChange);
        }
    }
}
