package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;
import org.rpg.rPGCraft.Traits.Passive.FlashOfOak;

import java.util.List;
import java.util.Objects;

public class PowerOfTheOakQueen extends ActiveTrait
{
    NamespacedKey oakQueenTimerKey = new NamespacedKey(main, "power_of_the_oak_queen_timer");
    AttributeModifier entSizeMod = new AttributeModifier(new NamespacedKey(main, "power_of_the_oak_queen_size_mod"), 0.5, AttributeModifier.Operation.ADD_NUMBER);
    AttributeModifier entDamageMod = new AttributeModifier(new NamespacedKey(main, "power_of_the_oak_queen_damage_mod"), 5, AttributeModifier.Operation.ADD_NUMBER);

    public PowerOfTheOakQueen(Main main) {
        // add the name and lore
        super("Power Of The Oak Queen", "power of the oak queen", 150, ChatColor.AQUA, Material.GOLDEN_HELMET, true, main, List.of(
                ChatColor.AQUA.toString() + "Wield the power of the oak queen to become something better than mortal for 60 seconds.",
                ChatColor.AQUA.toString() + " ",
                ChatColor.AQUA.toString() + "   - Grow one block in size.",
                ChatColor.AQUA.toString() + "   - Gain five base attack damage.",
                ChatColor.AQUA.toString() + "   - To use this trait you must have Flash Of Oak active."
        ));
    }

    @Override
    public String GetInputSequence()
    {
        return "010";
    }

    @Override
    public void OnTick(Player player)
    {
        if (player.getPersistentDataContainer().has(oakQueenTimerKey))
        {
            boolean hasFlashOfOak = false;

            for (Trait trait : main.statSheetManager.FindStatSheetByPlayer(player).GetActiveTraits())
            {
                if (trait.name_id.equals("flash of oak"))
                {
                    hasFlashOfOak = true;
                    break;
                }
            }

            if (!hasFlashOfOak)
            {
                player.getPersistentDataContainer().remove(oakQueenTimerKey);

                player.getAttribute(Attribute.SCALE).removeModifier(entSizeMod);

                player.getAttribute(Attribute.ATTACK_DAMAGE).removeModifier(entDamageMod);
                player.sendMessage(ChatColor.DARK_RED + "You must have Flash Of Oak active for the duration of this trait.");
            }

            player.getPersistentDataContainer().set(oakQueenTimerKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(oakQueenTimerKey, PersistentDataType.INTEGER) - 1);

            if (player.getPersistentDataContainer().get(oakQueenTimerKey, PersistentDataType.INTEGER) <= 0)
            {
                player.getPersistentDataContainer().remove(oakQueenTimerKey);

                player.getAttribute(Attribute.SCALE).removeModifier(entSizeMod);

                player.getAttribute(Attribute.ATTACK_DAMAGE).removeModifier(entDamageMod);
            }

        }
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        for (Trait trait : main.statSheetManager.FindStatSheetByPlayer(player).GetActiveTraits())
        {
            if (trait.name_id.equals("flash of oak"))
            {
                player.getPersistentDataContainer().set(oakQueenTimerKey, PersistentDataType.INTEGER, 600);

                player.getAttribute(Attribute.SCALE).addModifier(entSizeMod);
                player.getAttribute(Attribute.ATTACK_DAMAGE).addModifier(entDamageMod);
                return;
            }
        }

        player.sendMessage(ChatColor.DARK_RED + "You must have Flash Of Oak active to use this trait.");
        player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER,player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER)+150);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(oakQueenTimerKey))
        {
            player.getPersistentDataContainer().remove(oakQueenTimerKey);
        }

        if (player.getAttribute(Attribute.SCALE).getModifier(new NamespacedKey(main, "power_of_the_oak_queen_size_mod")) != null)
        {
            player.getAttribute(Attribute.SCALE).removeModifier(entSizeMod);
        }

        if (player.getAttribute(Attribute.ATTACK_DAMAGE).getModifier(new NamespacedKey(main, "power_of_the_oak_queen_damage_mod")) != null)
        {
            player.getAttribute(Attribute.ATTACK_DAMAGE).removeModifier(entDamageMod);
        }
    }
}
