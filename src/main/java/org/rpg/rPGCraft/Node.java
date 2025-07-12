package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector2d;

import java.util.List;

public class Node
{
    Trait trait;
    Vector2d coordinates;

    public Node(Vector2d coordinates, Trait trait)
    {
        this.coordinates = coordinates;
        this.trait = trait;
    }

    public int GetTranslatedCoordinates(int fullRowSize, Vector2d offset)
    {
        int translatedCoordinates = (int) (coordinates.x-offset.x);
        translatedCoordinates += (int) (fullRowSize*(4-(coordinates.y-offset.y)));

        return translatedCoordinates;
    }

    public ItemStack GetNodeIcon()
    {
        // generates the icon for this trait
        ItemStack nodeIcon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta traitIconMeta = nodeIcon.getItemMeta();

        traitIconMeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + trait.name);

        // add the trait
        traitIconMeta.getPersistentDataContainer().set(trait.main.GetTraitKey(), PersistentDataType.STRING, trait.name);

        // add the description
        List<String> lore = trait.GetTraitLore();
        lore.removeFirst();

        traitIconMeta.setLore(lore);

        // set the item meta
        nodeIcon.setItemMeta(traitIconMeta);

        // return the icon
        return nodeIcon;
    }
}
