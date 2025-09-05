package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class PotentRebuke extends Trait
{
    private final NamespacedKey rebukeCostKey = new NamespacedKey(main, "rebuke_of_the_flame_cost");
    private final int extraCost = 10;

    private final NamespacedKey setOnFireKey = new NamespacedKey(main, "rebuke_of_the_flame_set_on_fire");

    public PotentRebuke(Main main) {
        // add the name and lore
        super("Potent Rebuke", "potent rebuke", ChatColor.AQUA, Material.FLINT_AND_STEEL, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Rebuke Of The Flame sets enemies on fire for five seconds, but also cost five more mana."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(setOnFireKey))
        {
            player.getPersistentDataContainer().set(setOnFireKey, PersistentDataType.BOOLEAN, false);
        }

        if (player.getPersistentDataContainer().has(rebukeCostKey))
        {
            player.getPersistentDataContainer().set(rebukeCostKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(rebukeCostKey, PersistentDataType.INTEGER) - extraCost);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getPersistentDataContainer().set(setOnFireKey, PersistentDataType.BOOLEAN, true);

        if (player.getPersistentDataContainer().has(rebukeCostKey))
        {
            player.getPersistentDataContainer().set(rebukeCostKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(rebukeCostKey, PersistentDataType.INTEGER) + extraCost);
        }
        else
        {
            player.getPersistentDataContainer().set(rebukeCostKey, PersistentDataType.INTEGER, extraCost);
        }
    }
}
