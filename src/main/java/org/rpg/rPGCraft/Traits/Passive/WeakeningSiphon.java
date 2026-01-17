package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class WeakeningSiphon extends Trait {
    NamespacedKey weaknessLevelKey = new NamespacedKey(Main.GetInstance(), "aura_of_weakness_level");
    int extraLevel = 1;

    public WeakeningSiphon() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Weakening Siphon", "weakening siphon", Material.GRAY_DYE, false, List.of(
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
