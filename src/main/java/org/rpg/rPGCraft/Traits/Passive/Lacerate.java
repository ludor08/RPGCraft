package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Lacerate extends Trait
{
    NamespacedKey lacerate = new NamespacedKey(main, "lacerate");

    public Lacerate(Main main) {
        // add the name and lore
        super("Lacerate", "lacerate", ChatColor.AQUA, Material.REDSTONE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Every third combo leave a deep wound in your opponent",
                ChatColor.AQUA.toString() + "     dealing 20% of your max health (up to 10 damage)."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, lacerate, true);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, lacerate);
    }
}
