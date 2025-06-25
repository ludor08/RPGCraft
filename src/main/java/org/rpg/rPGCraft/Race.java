package org.rpg.rPGCraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Race {
    public String name;
    public ChatColor nameColor;

    public Material iconMaterial;
    public List<Race> subraces;
    public List<Trait> traits;

    public Race(String name, ChatColor nameColor, Material iconMaterial, List<Trait> traits, List<Race> subraces)
    {
        this.name = name;
        this.nameColor = nameColor;
        this.iconMaterial = iconMaterial;

        this.traits = traits;
        this.subraces = subraces;
    }

    public ItemStack GetRaceIcon()
    {
        // generates the icon for this race
        ItemStack raceIcon = new ItemStack(iconMaterial);
        ItemMeta raceIconMeta = raceIcon.getItemMeta();

        raceIconMeta.setDisplayName(nameColor.toString() + ChatColor.BOLD + name);

        // add the description
        List<String> lore = new ArrayList<>();

        // add the gap between the item name and the lore
        lore.add("\n");

        // if there are any traits
        if (traits != null)
        {
            // add the traits header
            lore.add(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Traits :");

            // add the traits to the lore
            for (Trait trait : traits)
            {
                lore.addAll(trait.GetTraitLore());
                lore.add("\n");
            }
        }

        // if there are any subraces
        if (subraces != null)
        {
            // add the subraces header
            lore.add(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Subraces :");

            // add the subraces to the lore
            for (Race subrace : subraces)
            {
                lore.add(ChatColor.RED.toString() + "- " + subrace.name);
            }
        }

        raceIconMeta.setLore(lore);

        raceIcon.setItemMeta(raceIconMeta);

        // return the icon
        return raceIcon;
    }

}
