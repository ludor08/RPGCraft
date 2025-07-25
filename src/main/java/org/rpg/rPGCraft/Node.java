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
    List<Trait> traits;
    String id;
    Vector2d coordinates;

    public Node(Vector2d coordinates, List<Trait> traits, String id)
    {
        this.coordinates = coordinates;
        this.traits = traits;
        this.id = id;
    }

    public int GetTranslatedCoordinates(int fullRowSize, Vector2d offset)
    {
        int translatedCoordinates = (int) (coordinates.x-offset.x);
        translatedCoordinates += (int) (fullRowSize*(4-(coordinates.y-offset.y)));

        return translatedCoordinates;
    }

    public ItemStack GetNodeIcon(int level, boolean showLevel)
    {
        // generates the icon for this trait
        ItemStack nodeIcon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta traitIconMeta = nodeIcon.getItemMeta();

        if (showLevel) traitIconMeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + traits.get(level-1).name + " " + level);
        else traitIconMeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + traits.get(level-1).name);

        // add the trait
        traitIconMeta.getPersistentDataContainer().set(traits.get(level-1).main.GetTraitKey(), PersistentDataType.STRING, traits.get(level-1).name_id);

        // add the description
        List<String> lore = traits.get(level-1).GetTraitLore();
        lore.set(0, "Max Level " + traits.size());

        traitIconMeta.setLore(lore);

        // set the item meta
        nodeIcon.setItemMeta(traitIconMeta);

        // return the icon
        return nodeIcon;
    }

    public Trait GetTraitFromString(String traitName)
    {
        Trait trait = null;

        for (Trait nodeTrait : traits)
        {
            if (nodeTrait.name_id.equals(traitName.replace(id, "")))
            {
                trait = traits.get(traits.indexOf(nodeTrait));
            }
        }

        return trait;
    }


}
