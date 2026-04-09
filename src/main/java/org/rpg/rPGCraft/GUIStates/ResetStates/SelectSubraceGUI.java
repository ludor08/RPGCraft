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
import org.rpg.rPGCraft.NamespaceDefinitions;
import org.rpg.rPGCraft.Race;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectSubraceGUI extends GUIState
{
    Race parentRace;

    public SelectSubraceGUI(Player owner, GUIState lastState, Race parentRace)
    {
        super(owner, "select_subrace", 45, lastState);

        this.parentRace = parentRace;
    }

    @Override
    public Inventory InitializeNewInventoryInstance()
    {
        // create the menu
        Inventory subraceMenu = Bukkit.createInventory(GetOwner(), GetInventorySize(),
                ChatColor.BOLD.toString() + "Select a Subrace!");

        // add the borders
        MenuManager.AddBorder(Material.RED_STAINED_GLASS_PANE, subraceMenu);

        // extract all of the icons from races
        List<ItemStack> raceIcons = new ArrayList<>();
        for (Race race : parentRace.subraces)
        {
            ItemStack icon = race.GetRaceIcon();
            MenuManager.AddPersistentDataContainerToItemStack(icon, NamespaceDefinitions.GetRaceKey(), race.name);

            raceIcons.add(icon);
        }

        // generate the icon positions and place them
        MenuManager.AddIconInPositions(subraceMenu, raceIcons, 7, 2);

        // get the parentRace icon
        ItemStack parentIcon = parentRace.GetRaceIcon();

        // add the parentRace icon to the top center
        subraceMenu.setItem(MenuManager.FULL_ROW_SIZE/2, parentIcon);

        // add the back button to the bottom left corner
        subraceMenu.setItem(MenuManager.FULL_ROW_SIZE*4, MenuManager.GetBackButton());

        // return the menu
        return subraceMenu;
    }

    @Override
    public void OnClick(InventoryClickEvent e)
    {
        // cancelled the event
        e.setCancelled(true);

        // if the player didn't click a border
        if (e.getCurrentItem().getPersistentDataContainer().has(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING))
        {
            // get the PersistentDataContainer of the icon clicked
            String racePersistent = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING);

            // find the race clicked
            for (Race race : parentRace.subraces)
            {
                // if the race name is the same as the race of what the player click, open the confirm subrace menu
                if (Objects.equals(race.name, racePersistent))
                {
                    // play a button click sound
                    GetOwner().playSound(GetOwner().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5f, 1);

                    MenuManager.AssignGUIState(new ConfirmSubraceGUI(GetOwner(), this, parentRace, race), GetOwner());
                }
            }

        }
    }
}
