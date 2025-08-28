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

public class AbnormalDiet_Raw_Fish extends Trait
{

    public AbnormalDiet_Raw_Fish(Main main) {
        // add the name and lore
        super("Abnormal Diet:Raw Fish", "abnormal diet:raw fish", ChatColor.AQUA, Material.SALMON, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Food other then raw fish only gives 75% saturation.",
                ChatColor.AQUA.toString() + "   - Raw fish also give 3x the saturation."
        ));
    }

//    @Override
//    public void OnFoodLevelChange(FoodLevelChangeEvent e)
//    {
//        // if the player ate
//        if (e.getItem() != null)
//        {
//            float GOOD_FOOD_MULTIPLIER = 5f;
//            float BAD_FOOD_MULTIPLIER = 0.75f;
//
//
//            Player player = (Player) e.getEntity();
//
//            // the change in food level
//            int levelChange = e.getFoodLevel() - player.getFoodLevel();
//
//            // if they ate raw fish
//            if (e.getItem().getType().equals(Material.COD) ||
//                    e.getItem().getType().equals(Material.SALMON) ||
//                    e.getItem().getType().equals(Material.PUFFERFISH) ||
//                    e.getItem().getType().equals(Material.TROPICAL_FISH)) {
//                // multiply the food gained by the GOOD_FOOD_MULTIPLIER
//                e.setFoodLevel((int) (levelChange * GOOD_FOOD_MULTIPLIER) + player.getFoodLevel());
//            }
//            // if not
//            else
//            {
//                // multiply the food gained by the BAD_FOOD_MULTIPLIER
//                e.setFoodLevel((int) (levelChange * BAD_FOOD_MULTIPLIER) + player.getFoodLevel());
//            }
//        }
//    }

    @Override
    public void OnPlayerItemConsume(PlayerItemConsumeEvent e)
    {
        // the change in food level
        int nutritionLevelChange = main.itemManager.GetVanillaFoodNutrition(e.getItem().getType());
        float saturationLevelChange = main.itemManager.GetVanillaFoodSaturation(e.getItem().getType());

        float BAD_FOOD_MULTIPLIER = 0.75f;
        float GOOD_FOOD_MULTIPLIER = 3f;

        Player player = e.getPlayer();

        // if they didn't eat a raw fish
        if (!e.getItem().getType().equals(Material.COD) &&
                    !e.getItem().getType().equals(Material.SALMON) &&
                    !e.getItem().getType().equals(Material.PUFFERFISH) &&
                    !e.getItem().getType().equals(Material.TROPICAL_FISH))
        {
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
