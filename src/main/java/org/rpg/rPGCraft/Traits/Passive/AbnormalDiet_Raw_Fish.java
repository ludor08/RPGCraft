package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class AbnormalDiet_Raw_Fish extends Trait
{

    public AbnormalDiet_Raw_Fish() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Abnormal Diet:Raw Fish", "abnormal diet:raw fish", Material.SALMON, false, List.of(
                ChatColor.AQUA.toString() + "   - Food other then raw fish only gives 75% saturation and nutrition.",
                ChatColor.AQUA.toString() + "   - Raw fish also give 3x the nutrition, and 6x the saturation."
        ));
    }

    @Override
    public void OnPlayerItemConsume(PlayerItemConsumeEvent e)
    {
        // the change in food level
        int nutritionLevelChange = Main.GetInstance().itemManager.GetVanillaFoodNutrition(e.getItem().getType());
        float saturationLevelChange = Main.GetInstance().itemManager.GetVanillaFoodSaturation(e.getItem().getType());

        float BAD_FOOD_MULTIPLIER = 0.75f;
        float GOOD_NUTRITION_MULTIPLIER = 3f;
        float GOOD_SATURATION_MULTIPLIER = 6f;

        Player player = e.getPlayer();

        // if they didn't eat a raw fish
        if (!e.getItem().getType().equals(Material.COD) &&
                    !e.getItem().getType().equals(Material.SALMON) &&
                    !e.getItem().getType().equals(Material.PUFFERFISH) &&
                    !e.getItem().getType().equals(Material.TROPICAL_FISH))
        {
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
