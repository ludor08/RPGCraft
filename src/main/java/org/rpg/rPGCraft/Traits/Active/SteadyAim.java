package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.Traits.ActiveTrait;

import java.util.List;

public class SteadyAim extends ActiveTrait
{
    NamespacedKey hasSteadyAimKey = new NamespacedKey(Main.GetInstance(), "has_steady_aim");

    NamespacedKey damageModKey = new NamespacedKey(Main.GetInstance(), "steady_aim_damage_bonus");
    float damageMod = 1.75f;

    private final NamespacedKey laserDamageKey = new NamespacedKey(Main.GetInstance(), "laser_shot_damage");

    AttributeModifier speedMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "steady_aim_speed_mod"), -10, AttributeModifier.Operation.ADD_NUMBER);

    public SteadyAim() {
        // add the name and lore
        super(ChatColor.RED + ChatColor.BOLD.toString() + "Steady Aim", "steady aim", 50, Material.ARROW, true, List.of(
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

        player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) + GetModifiedCost(player));
        player.getPersistentDataContainer().set(Main.GetInstance().GetManaKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(Main.GetInstance().GetManaKey(), PersistentDataType.INTEGER) + GetModifiedCost(player));
    }

    }

    @Override
    public void OnLaunchProjectile(ProjectileLaunchEvent e)
    {
        Player player = (Player) e.getEntity().getShooter();

        if (player.getPersistentDataContainer().has(damageModKey))
        {
            if (player.getPersistentDataContainer().has(laserDamageKey))
            {
                e.setCancelled(true);

                Vector3d direction = RPGutils.getFacingDirection(player).mul(0.1);
                Location location = player.getEyeLocation();

                ShootLaser(direction, location, player);

                Bukkit.getScheduler().runTaskLater(Main.GetInstance(), () -> {
                    ShootLaser(direction, location, player);
                }, 20);

                Bukkit.getScheduler().runTaskLater(Main.GetInstance(), () -> {
                    ShootLaser(direction, location, player);
                }, 40);
                return;
            }

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

    public void ShootLaser(Vector3d direction, Location location, Player player)
    {
        List<Entity> entities = RPGraycast.RecastForEntities(250, direction, location, true, player, Particle.HAPPY_VILLAGER, 5,new Vector3d(0.5,0.5,0.5));

        for (Entity entity : entities)
        {
            if (entity instanceof LivingEntity living)
            {
                RPGutils.DamageWithTrait(living, player, (player.getPersistentDataContainer().get(new NamespacedKey(Main.GetInstance(), "laser_shot_damage"), PersistentDataType.DOUBLE)), false);
            }
        }
    }
}
