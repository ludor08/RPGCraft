package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class RebukeOfTheFlame extends Trait
{
    private final NamespacedKey rebukeDamageKey = new NamespacedKey(Main.GetInstance(), "rebuke_of_the_flame_damage");
    private final int rebukeDamage = 5;

    private final NamespacedKey rebukeCostKey = new NamespacedKey(Main.GetInstance(), "rebuke_of_the_flame_cost");
    private final int baseCost = 10;

    public RebukeOfTheFlame() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Rebuke Of The Flame", "rebuke of the flame", Material.FLINT_AND_STEEL, false, List.of(
                ChatColor.AQUA.toString() + "   - Upon being hit cause fire to envelope your enemy, dealing five damage at the cost of 10 mana."
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        Player player = (Player) e.getEntity();

        if (e.isCancelled())
        {
            return;
        }

        if (player.getPersistentDataContainer().get(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER) >= player.getPersistentDataContainer().get(rebukeCostKey, PersistentDataType.INTEGER))
        {
            if (e.getDamageSource().getCausingEntity() == null)
            {
                return;
            }

            if (e.getDamageSource().getCausingEntity() != player && e.getDamageSource().getCausingEntity() instanceof LivingEntity living)
            {
                RPGutils.DamageWithTrait(living, player, rebukeDamage, false);

                if (player.getPersistentDataContainer().has(new NamespacedKey(Main.GetInstance(), "rebuke_of_the_flame_set_on_fire")) && player.getPersistentDataContainer().get(new NamespacedKey(Main.GetInstance(), "rebuke_of_the_flame_set_on_fire"),PersistentDataType.BOOLEAN))
                {
                    living.setFireTicks(100);
                }

                for (int i = 1; i < 20; i++)
                {
                    Vector3d offset = new Vector3d(Math.cos((Math.PI*2)/((double) i /20)), i*0.1, Math.sin((Math.PI*2)/((double) i /20)));
                    Location location = new Location(living.getWorld(), living.getLocation().getX() + offset.x, living.getLocation().getY() + offset.y, living.getLocation().getZ() + offset.z);

                    living.getWorld().spawnParticle(Particle.FLAME, location, 10, 0,0,0,0);
                }
            }

            player.getPersistentDataContainer().set(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER,player.getPersistentDataContainer().get(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER)-player.getPersistentDataContainer().get(rebukeCostKey, PersistentDataType.INTEGER));
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(rebukeDamageKey))
        {
            player.getPersistentDataContainer().set(rebukeDamageKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(rebukeDamageKey, PersistentDataType.INTEGER) - rebukeDamage);
        }

        if (player.getPersistentDataContainer().has(rebukeCostKey))
        {
            player.getPersistentDataContainer().set(rebukeCostKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(rebukeCostKey, PersistentDataType.INTEGER) - baseCost);
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

        if (player.getPersistentDataContainer().has(rebukeCostKey))
        {
            player.getPersistentDataContainer().set(rebukeCostKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(rebukeCostKey, PersistentDataType.INTEGER) + baseCost);
        }
        else
        {
            player.getPersistentDataContainer().set(rebukeCostKey, PersistentDataType.INTEGER, baseCost);
        }
    }
}
