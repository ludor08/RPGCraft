package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class PowerOfTheOakQueen extends ActiveTrait
{
    NamespacedKey oakQueenTimerKey = new NamespacedKey(Main.GetInstance(), "power_of_the_oak_queen_timer");
    AttributeModifier entSizeMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "power_of_the_oak_queen_size_mod"), 0.5, AttributeModifier.Operation.ADD_NUMBER);
    AttributeModifier entDamageMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "power_of_the_oak_queen_damage_mod"), 5, AttributeModifier.Operation.ADD_NUMBER);

    public PowerOfTheOakQueen() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Power Of The Oak Queen", "power of the oak queen", 150, Material.GOLDEN_HELMET, true, List.of(
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

            for (Trait trait : Main.GetInstance().statSheetManager.FindStatSheetByPlayer(player).GetActiveTraits())
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

                return;
            }

            player.getPersistentDataContainer().set(oakQueenTimerKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(oakQueenTimerKey, PersistentDataType.INTEGER) - 1);

            if (player.getPersistentDataContainer().get(oakQueenTimerKey, PersistentDataType.INTEGER) <= 0)
            {
                player.getPersistentDataContainer().remove(oakQueenTimerKey);

                player.getAttribute(Attribute.SCALE).removeModifier(entSizeMod);

                player.getAttribute(Attribute.ATTACK_DAMAGE).removeModifier(entDamageMod);
            }

            for (Entity entity : player.getNearbyEntities(100, 100, 100))
            {
                if (entity instanceof Player particlePlayer)
                {
                    Vector3d lOff = RPGutils.getLocationOffsetByVector(player.getEyeLocation(), RPGutils.getFacingDirection(player),   (float) player.getWidth()*0.5f, (float) -(player.getWidth()*0.2f), 0.05f * (0.6f / (float) player.getWidth()));
                    Location leftEye = new Location(player.getWorld(), lOff.x, lOff.y, lOff.z);
                    RPGparticles.SpawnParticle(particlePlayer, 5, leftEye,new Vector3d(0,0,0), Particle.ASH, 0);

                    Vector3d rOff = RPGutils.getLocationOffsetByVector(player.getEyeLocation(), RPGutils.getFacingDirection(player),  (float) player.getWidth()*0.5f, (float) (player.getWidth()*0.2f), 0.05f * (0.6f / (float) player.getWidth()));
                    Location rightEye = new Location(player.getWorld(), rOff.x, rOff.y, rOff.z);
                    RPGparticles.SpawnParticle(particlePlayer, 5, rightEye,new Vector3d(0,0,0), Particle.ASH, 0);
                }
            }
        }
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        for (Trait trait : Main.GetInstance().statSheetManager.FindStatSheetByPlayer(player).GetActiveTraits())
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
        player.getPersistentDataContainer().set(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER,player.getPersistentDataContainer().get(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER)+150);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(oakQueenTimerKey))
        {
            player.getPersistentDataContainer().remove(oakQueenTimerKey);
        }

        if (player.getAttribute(Attribute.SCALE).getModifier(new NamespacedKey(Main.GetInstance(), "power_of_the_oak_queen_size_mod")) != null)
        {
            player.getAttribute(Attribute.SCALE).removeModifier(entSizeMod);
        }

        if (player.getAttribute(Attribute.ATTACK_DAMAGE).getModifier(new NamespacedKey(Main.GetInstance(), "power_of_the_oak_queen_damage_mod")) != null)
        {
            player.getAttribute(Attribute.ATTACK_DAMAGE).removeModifier(entDamageMod);
        }
    }
}
