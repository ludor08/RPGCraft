package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class AbnormalDiet_Death extends Trait
{
    private final NamespacedKey willLoseHunger = new NamespacedKey(Main.GetInstance(), "abnormal_diet_death_hunger");

    public AbnormalDiet_Death() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Abnormal Diet:Death", "abnormal diet:death", Material.WITHER_ROSE, false, List.of(
                ChatColor.AQUA.toString() + "   - Food other then meat and mushroom stew give half as much saturation and nutrition.",
                ChatColor.AQUA.toString() + "   - Needs to eat half as often.",
                ChatColor.AQUA.toString() + "   - Immune to the hunger condition."
        ));
    }

    @Override
    public void OnFoodLevelChange(FoodLevelChangeEvent e)
    {
        Player player = (Player) e.getEntity();

        // the change in food level
        int levelChange = e.getFoodLevel() - player.getFoodLevel();

        // if the player ate
        if (e.getItem() == null)
        {
            // if the player lost hunger
            if (levelChange < 0)
            {
                // if the player has willLoseHunger
                if (player.getPersistentDataContainer().has(willLoseHunger))
                {
                    // if they will lose hunger, lose hunger
                    e.setCancelled(!player.getPersistentDataContainer().get(willLoseHunger, PersistentDataType.BOOLEAN));

                    player.getPersistentDataContainer().set(willLoseHunger, PersistentDataType.BOOLEAN,!player.getPersistentDataContainer().get(willLoseHunger, PersistentDataType.BOOLEAN));
                }
                else
                {
                    player.getPersistentDataContainer().set(willLoseHunger, PersistentDataType.BOOLEAN,true);
                }
            }
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(willLoseHunger))
        {
            player.getPersistentDataContainer().remove(willLoseHunger);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (!player.getPersistentDataContainer().has(willLoseHunger))
        {
            player.getPersistentDataContainer().set(willLoseHunger, PersistentDataType.BOOLEAN, true);
        }
    }

    @Override
    public void OnPlayerItemConsume(PlayerItemConsumeEvent e)
    {
        // the change in food level
        int nutritionLevelChange = e.getItem().getItemMeta().getFood().getNutrition();
        float saturationLevelChange = e.getItem().getItemMeta().getFood().getSaturation();

        float BAD_FOOD_MULTIPLIER = 0.5f;

        Player player = e.getPlayer();

        // if they didn't eat meat or mushroom stew
        if (!Main.GetInstance().itemManager.GetMeatTypes().contains(e.getItem().getType()) &&
                !e.getItem().getType().equals(Material.MUSHROOM_STEW)) {
            player.setFoodLevel((int) (nutritionLevelChange * BAD_FOOD_MULTIPLIER) + player.getFoodLevel() - nutritionLevelChange);
            player.setSaturation((saturationLevelChange * BAD_FOOD_MULTIPLIER) + player.getSaturation() - saturationLevelChange);
        }
    }

    @Override
    public void OnGainEffect(EntityPotionEffectEvent e)
    {
        if (e.getModifiedType() == PotionEffectType.HUNGER)
        {
            e.setCancelled(true);
        }
    }
}
