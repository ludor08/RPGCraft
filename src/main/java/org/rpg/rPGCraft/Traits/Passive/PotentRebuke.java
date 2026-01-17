package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class PotentRebuke extends Trait
{
    private final NamespacedKey rebukeCostKey = new NamespacedKey(Main.GetInstance(), "rebuke_of_the_flame_cost");
    private final int extraCost = 10;

    private final NamespacedKey setOnFireKey = new NamespacedKey(Main.GetInstance(), "rebuke_of_the_flame_set_on_fire");

    public PotentRebuke() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Potent Rebuke", "potent rebuke", Material.FLINT_AND_STEEL, false, List.of(
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
