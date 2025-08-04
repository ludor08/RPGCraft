package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;
import java.util.Objects;

public class Regeneration extends Trait
{
    private final NamespacedKey healTimer;
    private final int timeToHeal = 50; // TODO make configalbe with the tick rate

    public Regeneration(Main main) {
        // add the name and lore
        super("Regeneration", "regeneration", ChatColor.AQUA, Material.MANGROVE_PROPAGULE, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Regenerates half a heart every 5 seconds."
        ));

        healTimer = new NamespacedKey(main, "regeneration_heal_timer");
    }

    @Override
    public void OnTick(Player player)
    {
        // if the player has a heal timer
        if (player.getPersistentDataContainer().has(healTimer))
        {
            // if the heal timer is more or equal than time needed to heal
            if (player.getPersistentDataContainer().get(healTimer, PersistentDataType.INTEGER) >= timeToHeal)
            {
                player.getPersistentDataContainer().set(healTimer, PersistentDataType.INTEGER,0);
                // check if they can be healed
                if (Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue() > player.getHealth())
                {
                    player.heal(1);
                }
            }
            else
            {
                // add to the timer
                player.getPersistentDataContainer().set(healTimer, PersistentDataType.INTEGER,player.getPersistentDataContainer().get(healTimer, PersistentDataType.INTEGER)+1);
            }
        }
        else
        {
            // give them the timer
            player.getPersistentDataContainer().set(healTimer, PersistentDataType.INTEGER,0);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(healTimer))
        {
            player.getPersistentDataContainer().remove(healTimer);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (!player.getPersistentDataContainer().has(healTimer))
        {
            player.getPersistentDataContainer().set(healTimer, PersistentDataType.INTEGER, 0);
        }
    }


}
