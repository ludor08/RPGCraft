package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class RebukeOfTheFlame extends Trait
{
    private final NamespacedKey rebukeDamageKey = new NamespacedKey(main, "rebuke_of_the_flame_damage");
    private final int rebukeDamage = 5;

    public RebukeOfTheFlame(Main main) {
        // add the name and lore
        super("Rebuke Of The Flame", "rebuke of the flame", ChatColor.AQUA, Material.FLINT_AND_STEEL, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Upon being hit cause fire to envelope your enemy, dealing five damage at the cost of 10 mana."
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        Player player = (Player) e.getEntity();

        if (player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) >= 10)
        {
            if (e.getDamageSource().getCausingEntity() == null)
            {
                return;
            }

            if (e.getDamageSource().getCausingEntity() != player && e.getDamageSource().getCausingEntity() instanceof LivingEntity living)
            {
                living.damage(rebukeDamage, player);

                for (int i = 1; i < 20; i++)
                {
                    Vector3d offset = new Vector3d(Math.cos((Math.PI*2)/((double) i /20)), i*0.1, Math.sin((Math.PI*2)/((double) i /20)));
                    Location location = new Location(living.getWorld(), living.getLocation().getX() + offset.x, living.getLocation().getY() + offset.y, living.getLocation().getZ() + offset.z);

                    living.getWorld().spawnParticle(Particle.FLAME, location, 10, 0,0,0,0);
                }
            }

            player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER,player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER)-10);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(rebukeDamageKey))
        {
            player.getPersistentDataContainer().set(rebukeDamageKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(rebukeDamageKey, PersistentDataType.INTEGER) - rebukeDamage);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(rebukeDamageKey))
        {
            player.getPersistentDataContainer().set(rebukeDamageKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(rebukeDamageKey, PersistentDataType.INTEGER) + rebukeDamage);
        }
        else
        {
            player.getPersistentDataContainer().set(rebukeDamageKey, PersistentDataType.INTEGER, rebukeDamage);
        }
    }
}
