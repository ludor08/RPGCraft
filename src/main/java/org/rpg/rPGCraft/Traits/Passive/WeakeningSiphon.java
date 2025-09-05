package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class WeakeningSiphon extends Trait {
    NamespacedKey weaknessLevelKey = new NamespacedKey(main, "aura_of_weakness_level");
    int extraLevel = 1;

    public WeakeningSiphon(Main main) {
        // add the name and lore
        super("Weakening Siphon", "weakening siphon", ChatColor.AQUA, Material.GRAY_DYE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Mana Siphon weaken enemies."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(weaknessLevelKey))
        {
            player.getPersistentDataContainer().set(weaknessLevelKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(weaknessLevelKey, PersistentDataType.INTEGER) - extraLevel);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(weaknessLevelKey))
        {
            player.getPersistentDataContainer().set(weaknessLevelKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(weaknessLevelKey, PersistentDataType.INTEGER) + extraLevel);
        }
        else
        {
            player.getPersistentDataContainer().set(weaknessLevelKey, PersistentDataType.INTEGER, extraLevel);
        }
    }
}
