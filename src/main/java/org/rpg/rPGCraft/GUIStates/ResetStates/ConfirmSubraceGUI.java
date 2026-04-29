package org.rpg.rPGCraft.GUIStates.ResetStates;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.GUIStates.GUIState;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.MenuManager;
import org.rpg.rPGCraft.Definitions.NamespaceDefinitions;
import org.rpg.rPGCraft.Race;

import java.util.List;

public class ConfirmSubraceGUI extends GUIState
{
    Race parentRace;
    Race subrace;

    public ConfirmSubraceGUI(Player owner, GUIState lastState, Race parentRace, Race subrace)
    {
        super(owner, "confirm_subrace", 45, lastState);

        this.parentRace = parentRace;
        this.subrace = subrace;
    }

    @Override
    public Inventory InitializeNewInventoryInstance()
    {
        // create the menu
        Inventory confirmMenu = Bukkit.createInventory(GetOwner(), GetInventorySize(),
                ChatColor.BOLD.toString() + "Confirm Subrace?");

        // add the borders
        MenuManager.AddBorder(Material.RED_STAINED_GLASS_PANE, confirmMenu);

        // place the icons and buttons
        MenuManager.AddIconInPositions(confirmMenu, List.of(subrace.GetRaceIcon(), parentRace.GetRaceIcon()), 7, 1);

        // add the back button to the bottom left corner
        confirmMenu.setItem(MenuManager.FULL_ROW_SIZE*4, MenuManager.GetBackButton());

        // add the confirm button to the row under the icons
        ItemStack subraceConfirmButton = MenuManager.GetConfirmButton();

        confirmMenu.setItem((int) (MenuManager.FULL_ROW_SIZE*3.5f), subraceConfirmButton);

        // return the menu
        return confirmMenu;
    }

    @Override
    public void OnClick(InventoryClickEvent e)
    {
        // cancelled the event
        e.setCancelled(true);

        // if the player clicked the confirm button
        if (e.getCurrentItem().getPersistentDataContainer().has(NamespaceDefinitions.GetUIKey())
            && e.getCurrentItem().getPersistentDataContainer().get(NamespaceDefinitions.GetUIKey(), PersistentDataType.STRING).equals("confirm"))
        {
            Main.GetInstance().statSheetManager.FindStatSheetByPlayer(GetOwner()).SetRace(subrace.name, parentRace.name);

            // send the confirmation message
            GetOwner().sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Race Selected!");

            // play a ding sound
            GetOwner().playSound(GetOwner().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5f, 1);

            // close the menu
            GetOwner().closeInventory();
        }
    }
}
