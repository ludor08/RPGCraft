package org.rpg.rPGCraft.GUIStates.StatSheetStates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.GUIStates.GUIState;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.ArrayList;
import java.util.List;

public class TraitMenuGUI extends GUIState
{
    public TraitMenuGUI(Player owner, GUIState lastState)
    {
        super(owner, "trait", 45, lastState);
    }

    @Override
    public Inventory InitializeNewInventoryInstance()
    {
        // create the stat menu
        Inventory traitMenu = Bukkit.createInventory(GetOwner(), GetInventorySize(),
                ChatColor.BOLD.toString() + "Traits");

        // add the borders
        traitMenu = MenuManager.AddBorder(Material.LIME_STAINED_GLASS_PANE, traitMenu);

        // get the back button
        ItemStack statBackButton = MenuManager.GetBackButton();

        traitMenu.setItem(MenuManager.FULL_ROW_SIZE*4, statBackButton);

        // get all of the trait icons
        List<ItemStack> traitIcons = new ArrayList<>();

        for (Trait trait : Main.GetInstance().statSheetManager.FindStatSheetByPlayer(GetOwner()).GetTraits())
        {
            traitIcons.add(trait.GetTraitIcon());
        }

        // place the trait icons
        MenuManager.AddIconInPositions(traitMenu, traitIcons, MenuManager.FULL_ROW_SIZE - 2, 1);

        // return the menu
        return traitMenu;
    }

    @Override
    public void OnClick(InventoryClickEvent e)
    {
        e.setCancelled(true);
    }
}
