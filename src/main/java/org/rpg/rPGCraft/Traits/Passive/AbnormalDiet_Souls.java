package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class AbnormalDiet_Souls extends Trait
{
    private final NamespacedKey willLoseHunger;

    public AbnormalDiet_Souls() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Abnormal Diet:Souls", "abnormal diet:souls", Material.ECHO_SHARD, false, List.of(
                ChatColor.AQUA.toString() + "   - Gain nutrition, saturation, and health from XP at a 3 to 1 ratio. Foods give half as much nutrition and saturation",
                ChatColor.AQUA.toString() + "   - Needs to eat half as often."
        ));

        willLoseHunger = new NamespacedKey(Main.GetInstance(), "abnormal_diet_souls_hunger");
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
    public void OnPickUpXP(PlayerPickupExperienceEvent e)
    {
        Player player = e.getPlayer();

        if (player.getSaturation() < 10 || player.getFoodLevel() < 20 || player.getHealth() < player.getAttribute(Attribute.MAX_HEALTH).getValue())
        {
            ExperienceOrb orb = e.getExperienceOrb();

            if (orb.getExperience() >= 3)
            {
                orb.setExperience(orb.getExperience()-3);
                if (player.getHealth() < player.getAttribute(Attribute.MAX_HEALTH).getValue()) player.heal(1);
                if (player.getFoodLevel() < 20) player.setFoodLevel(player.getFoodLevel()+1);
                if (player.getSaturation() < 20) player.setSaturation(player.getSaturation()+1);
            }
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

        player.setFoodLevel((int) (nutritionLevelChange * BAD_FOOD_MULTIPLIER) + player.getFoodLevel() - nutritionLevelChange);
        player.setSaturation((saturationLevelChange * BAD_FOOD_MULTIPLIER) + player.getSaturation() - saturationLevelChange);
    }

}
