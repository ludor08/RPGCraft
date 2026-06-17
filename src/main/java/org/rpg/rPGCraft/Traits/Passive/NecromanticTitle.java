package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class NecromanticTitle extends Trait
{
    public NecromanticTitle() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Necromantic Title", "necromantic title", Material.POTION, true, List.of(
                ChatColor.AQUA.toString() + "   - Zombies and skeletons will no longer target you."
        ));
    }

    @Override
    public void OnTargeted(EntityTargetEvent e)
    {
        // if the targeting mob is a zombie or a skeleton
        if (e.getEntity() instanceof Zombie || e.getEntity() instanceof Skeleton)
        {
            e.setCancelled(true);
        }
    }
}
