package org.rpg.rPGCraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayableClass {
    public String name;
    public ChatColor nameColor;

    public Material iconMaterial;
    public TraitTree traitTree;
    public List<String> description;

    public PlayableClass(String name, ChatColor nameColor, Material iconMaterial, List<String> description, TraitTree traitTree)
    {
        this.name = name;
        this.nameColor = nameColor;
        this.iconMaterial = iconMaterial;

        this.description = description;
        this.traitTree = traitTree;
    }

    public ItemStack GetClassIcon()
    {
        // generates the icon for this race
        ItemStack classIcon = new ItemStack(iconMaterial);
        ItemMeta classIconMeta = classIcon.getItemMeta();

        classIconMeta.setDisplayName(nameColor.toString() + ChatColor.BOLD + name);

        // add the description
        classIconMeta.setLore(description);

        // save the meta
        classIcon.setItemMeta(classIconMeta);

        // return the icon
        return classIcon;
    }

}
