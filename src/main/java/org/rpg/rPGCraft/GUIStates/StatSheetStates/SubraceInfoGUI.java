package org.rpg.rPGCraft.GUIStates.StatSheetStates;

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
import org.rpg.rPGCraft.Traits.Trait;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubraceInfoGUI extends GUIState
{
    Race race;

    public SubraceInfoGUI(Player owner, GUIState lastState, Race race)
    {
        super(owner, "subrace_info", 45, lastState);

        this.race = race;
    }

    @Override
    public Inventory InitializeNewInventoryInstance()
    {
        // create the menu
        Inventory raceInfoMenu = Bukkit.createInventory(GetOwner(), GetInventorySize(),
                ChatColor.BOLD.toString() + "Subrace Info");

        // add the borders
        MenuManager.AddBorder(Material.RED_STAINED_GLASS_PANE, raceInfoMenu);

        // get the back button
        ItemStack traitBackButton = MenuManager.GetBackButton();

        // add the back button to the bottom left corner
        raceInfoMenu.setItem(MenuManager.FULL_ROW_SIZE*4, traitBackButton);

        // get the parentRace icon
        ItemStack raceIcon = race.GetRaceIcon();

        // add the parentRace icon to the top center
        raceInfoMenu.setItem(MenuManager.FULL_ROW_SIZE/2, raceIcon);

        // if the race has subraces
        if (race.subraces != null)
        {
            // go through all of the traits and add the trait icons
            for (int i = 0; i < race.traits.size(); i++)
            {
                // add the icon one to the side and i+1 down
                raceInfoMenu.setItem(1 + MenuManager.FULL_ROW_SIZE + (MenuManager.FULL_ROW_SIZE*i), race.traits.get(i).GetTraitIcon());
            }

            // go through all of the subraces and save their icons to subraces
            List<ItemStack> subraceIcons = new ArrayList<>();

            for (Race subrace : race.subraces)
            {
                // save the persistent in the subraceIcon
                ItemStack subraceIcon = subrace.GetRaceIcon();
                MenuManager.AddPersistentDataContainerToItemStack(subraceIcon, NamespaceDefinitions.GetRaceKey(), subrace.name);

                // add subraceIcon to subraceIcons
                subraceIcons.add(subraceIcon);
            }

            MenuManager.AddIconInPositions(raceInfoMenu, subraceIcons, 3, 2);

        }
        // if there isn't
        else
        {
            // find all of the trait icon
            List<ItemStack> icons = new ArrayList<>();

            for (Trait trait : race.traits)
            {
                icons.add(trait.GetTraitIcon());
            }

            // Generate the icon positions
            MenuManager.AddIconInPositions(raceInfoMenu, icons, 7, 2);
        }

        // return the menu
        return raceInfoMenu;
    }

    @Override
    public void OnClick(InventoryClickEvent e)
    {
        // cancel the event so the player can't take items
        e.setCancelled(true);

        // if the player didn't click a border
        if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING))
        {
            // get the PersistentDataContainer of the icon clicked
            String clickedPersistent = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING);

            // get the PersistentDataContainer of the parent
            String parentPersistent = Main.GetInstance().statSheetManager.FindRace(GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING)).name;

            // if the player didn't click the parent race, generate and open the confirm menu
            if (!Objects.equals(clickedPersistent, parentPersistent))
            {
                // Get the subrace clicked
                Race subrace = Main.GetInstance().statSheetManager.FindRace(clickedPersistent);

                // play a button click sound
                GetOwner().playSound(GetOwner().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5f, 1);

                // open the subrace info menu
                MenuManager.AssignGUIState(new SubraceInfoGUI(GetOwner(), this, subrace), GetOwner());
            }
        }
    }
}
