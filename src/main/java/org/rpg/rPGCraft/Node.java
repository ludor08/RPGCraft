package org.rpg.rPGCraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector2d;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.Arrays;
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

    public ItemStack GetNodeIcon(int level, boolean isDisabled)
    {
        // used level
        int usedLevel = Math.clamp(level-1, 0, level);

        // generate the name
        String name = ChatColor.AQUA.toString() + ChatColor.BOLD + traits.get(usedLevel).name;

        // set the material
        Material material = Material.GREEN_STAINED_GLASS_PANE;

        // add the description
        List<String> lore = traits.get(usedLevel).GetTraitLore();

        lore.set(0,ChatColor.GRAY + "Level " + level + ChatColor.DARK_GRAY + "/" + traits.size());

        // if there are more levels and it has a level
        if (traits.size() > level && level > 0)
        {
            lore.add(" ");
            lore.add(ChatColor.GREEN + "====[NEXT LEVEL]====");

            lore.add(ChatColor.GRAY + "Level " + (level+1) + ChatColor.DARK_GRAY + "/" + traits.size());

            List<String> upgradeLore = traits.get(level).GetTraitLore();
            upgradeLore.removeFirst();

            for (String loreBit : upgradeLore)
            {
                lore.add(loreBit);
            }

        }

        if (level > 0)
        {
            lore.add(" ");

            if (isDisabled)
            {
                lore.add(ChatColor.RED.toString() + ChatColor.BOLD + "Disabled.");
                material = Material.CYAN_STAINED_GLASS_PANE;
            }
            else
            {
                lore.add(ChatColor.GREEN.toString() + ChatColor.BOLD + "Enabled.");
                material = Material.LIME_STAINED_GLASS_PANE;
            }
        }

        // generates the icon for this trait
        ItemStack nodeIcon = MenuManager.InitializeItemStack(material, name, lore);

        // add the trait
        MenuManager.AddPersistentDataContainerToItemStack(nodeIcon, MyNamespaces.TRAIT.GetNamespacedKey(), traits.get(usedLevel).name_id + id);

        // return the icon
        return nodeIcon;
    }

    public List<Trait> GetTraits()
    {
        return traits;
    }

    public String GetID()
    {
        return id;
    }

    public Vector2d GetCoordinates()
    {
        return coordinates;
    }

    public boolean IsNodeEnabled(Player player)
    {
        // get the deactivated
        List<String> deactivatedNodes = Arrays.stream(player.getPersistentDataContainer().get(MyNamespaces.DEACTIVATED_NODES.GetNamespacedKey(), PersistentDataType.STRING).split("_")).toList();

        // go through all traits in the progression in the node
        for (Trait trait : traits)
        {
            if (deactivatedNodes.contains(trait.name_id+id))
            {
                return false;
            }
        }

        return true;
    }

    public boolean IsNodeOwned(Player player)
    {
        // get the owned traits
        List<String> treeProgression = Arrays.stream(player.getPersistentDataContainer().get(MyNamespaces.TREE_PROGRESSION.GetNamespacedKey(), PersistentDataType.STRING).split("_")).toList();

        // get the traits in the TreeProgression
        for (String traitName : treeProgression)
        {
            // go through all traits in the progression in the node
            for (Trait trait : traits)
            {
                if (traitName.equals(trait.name_id+id))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public int GetNodeLevel(Player player)
    {
        // if the node is not owned, return 0
        if (!IsNodeOwned(player))
        {
            return 0;
        }

        // get the owned traits
        List<String> treeProgression = Arrays.stream(player.getPersistentDataContainer().get(MyNamespaces.TREE_PROGRESSION.GetNamespacedKey(), PersistentDataType.STRING).split("_")).toList();

        // check if the node trait is in the treeProgression
        for (String selectedTrait : treeProgression)
        {
            for (Trait trait : traits)
            {
                if (selectedTrait.equals(trait.name_id + id))
                {
                    return traits.indexOf(trait)+1;
                }
            }
        }

        return 0;
    }
}
