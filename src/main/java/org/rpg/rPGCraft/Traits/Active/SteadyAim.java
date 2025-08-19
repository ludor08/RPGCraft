package org.rpg.rPGCraft.Traits.Active;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;

import java.util.List;

public class SteadyAim extends ActiveTrait
{
    NamespacedKey damageModKey = new NamespacedKey(main, "steady_aim_damage_bonus");
    float damageMod = 1.75f;

    AttributeModifier speedMod = new AttributeModifier(new NamespacedKey(main, "steady_aim_speed_mod"), -10, AttributeModifier.Operation.ADD_NUMBER);

    public SteadyAim(Main main) {
        // add the name and lore
        super("Steady Aim", "steady aim", 50, ChatColor.RED, Material.ARROW, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Upon activating this trait while sneaking, you will not be able to move, however, ",
                ChatColor.AQUA.toString() + "     you will also gain a 1.75x multiplier to your next shots damage.",
                ChatColor.AQUA.toString() + "   - This ability will end upon unsneaking or firing a projectile."
        ));


    }

    @Override
    public String GetInputSequence()
    {
        return "001";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        if (player.isSneaking())
        {
            if (!player.getPersistentDataContainer().has(damageModKey))
            {
                player.getPersistentDataContainer().set(damageModKey, PersistentDataType.FLOAT, damageMod);
                player.getAttribute(Attribute.MOVEMENT_SPEED).addModifier(speedMod);
                return;
            }
            else
            {
                player.sendMessage("This trait is already activate.");
            }
        }
        else
        {
            player.sendMessage("You must be sneaking to activate this trait.");
        }

        player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) + GetCost());
    }

    @Override
    public void OnLaunchProjectile(ProjectileLaunchEvent e)
    {
        Player player = (Player) e.getEntity().getShooter();

        if (player.getPersistentDataContainer().has(damageModKey))
        {
            if (e.getEntity() instanceof Arrow arrow)
            {
                arrow.setDamage(arrow.getDamage()*player.getPersistentDataContainer().get(damageModKey, PersistentDataType.FLOAT));
            }
            else if (e.getEntity() instanceof Trident trident)
            {
                trident.setDamage(trident.getDamage()*player.getPersistentDataContainer().get(damageModKey, PersistentDataType.FLOAT));
            }
            else if (e.getEntity() instanceof SpectralArrow spectralArrow)
            {
                spectralArrow.setDamage(spectralArrow.getDamage()*player.getPersistentDataContainer().get(damageModKey, PersistentDataType.FLOAT));
            }

            player.getPersistentDataContainer().remove(damageModKey);
            player.getAttribute(Attribute.MOVEMENT_SPEED).removeModifier(speedMod);
        }
    }

    @Override
    public void OnToggleSneak(PlayerToggleSneakEvent e)
    {
        if (!e.isSneaking())
        {
            if (e.getPlayer().getPersistentDataContainer().has(damageModKey))
            {
                e.getPlayer().getPersistentDataContainer().remove(damageModKey);
                e.getPlayer().getAttribute(Attribute.MOVEMENT_SPEED).removeModifier(speedMod);
            }
        }
    }
}
