package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class ThickSkin extends Trait
{
    private final NamespacedKey otherDamageKey = new NamespacedKey(Main.GetInstance(), "flash_of_oak_other_damage");
    float otherDamageMod = 0.1f;

    public ThickSkin()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Thick Skin", "thick skin", Material.ARMADILLO_SCUTE, false, List.of(
                ChatColor.AQUA.toString() + "   - Takes 10% less damage from all damage sources."
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        e.setDamage(e.getDamage() * 1 - e.getEntity().getPersistentDataContainer().get(otherDamageKey, PersistentDataType.FLOAT));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, otherDamageKey);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, otherDamageKey, 0, otherDamageMod);
    }
}
