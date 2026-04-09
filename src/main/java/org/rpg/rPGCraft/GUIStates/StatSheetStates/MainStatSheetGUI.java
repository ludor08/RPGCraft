package org.rpg.rPGCraft.GUIStates.StatSheetStates;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.GUIStates.GUIState;

import java.util.List;

public class MainStatSheetGUI extends GUIState
{
    public MainStatSheetGUI(Player owner, GUIState lastState)
    {
        super(owner, "main_stat_sheet", 45, lastState);
    }

    @Override
    public Inventory InitializeNewInventoryInstance()
    {
        // create the stat sheet
        Inventory mainStatSheetGUI = Bukkit.createInventory(GetOwner(), GetInventorySize(),
                ChatColor.BOLD.toString() + "Stat Sheet");

        // add the borders
        MenuManager.AddBorder(Material.LIME_STAINED_GLASS_PANE, mainStatSheetGUI);

        // if the owner has a race persistent
        if (GetOwner().getPersistentDataContainer().has(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING))
        {
            // Find the parent race script
            Race raceOfParent = Main.GetInstance().statSheetManager.FindRace(GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING));

            // if there isn't a parent race then end the function and throw an error
            if (raceOfParent == null)
            {
                Bukkit.getLogger().warning("ERROR: invalid parent race");
                return null;
            }

            // if the owner has a subrace persistent
            if (GetOwner().getPersistentDataContainer().has(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING))
            {
                // Find the subrace script
                Race raceOfSubrace = Main.GetInstance().statSheetManager.FindRace(GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING));

                // if there is a subrace
                if (raceOfSubrace != null)
                {
                    // Get the subrace icon
                    ItemStack subraceIcon = raceOfSubrace.GetRaceIcon();
                    MenuManager.AddPersistentDataContainerToItemStack(subraceIcon, NamespaceDefinitions.GetUIKey(), "subrace");

                    // place the subrace icon one to the side and three down
                    mainStatSheetGUI.setItem(1+(MenuManager.FULL_ROW_SIZE*3), subraceIcon);
                }
                // if there isn't
                else
                {
                    // place a null icon one to the side and three down
                    ItemStack nullIcon = MenuManager.InitializeItemStack(Material.BARRIER, ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Null");
                    MenuManager.AddPersistentDataContainerToItemStack(nullIcon, NamespaceDefinitions.GetUIKey(), "null");

                    mainStatSheetGUI.setItem(1+(MenuManager.FULL_ROW_SIZE*3), nullIcon);
                }
            }

            // Get the race icon
            ItemStack raceIcon = raceOfParent.GetRaceIcon();
            MenuManager.AddPersistentDataContainerToItemStack(raceIcon, NamespaceDefinitions.GetUIKey(), "race");

            // place the race icon one to the side and one down
            mainStatSheetGUI.setItem(1+MenuManager.FULL_ROW_SIZE, raceIcon);
        }

        // if the owner has a class persistent
        if (GetOwner().getPersistentDataContainer().has(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING))
        {
            // Find the class script
            PlayableClass playableClass = Main.GetInstance().statSheetManager.FindClass(GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING));

            // if there isn't a class then end the function and throw an error
            if (playableClass == null)
            {
                Bukkit.getLogger().warning(ChatColor.RED + "ERROR: invalid class");
                return null;
            }

            // Get the class icon
            ItemStack classIcon = playableClass.GetClassIcon();

            MenuManager.AddLoreToItemStack(classIcon, List.of("Level: " + GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER)));
            MenuManager.AddPersistentDataContainerToItemStack(classIcon, NamespaceDefinitions.GetUIKey(), "class");

            // place the class icon four to the side and one down
            mainStatSheetGUI.setItem(4+(MenuManager.FULL_ROW_SIZE), classIcon);
        }

        // if the player has a xp and level persistent
        if (GetOwner().getPersistentDataContainer().has(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER)
                && GetOwner().getPersistentDataContainer().has(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER))
        {
            // make the xp bar item
            ItemStack xpBar = MenuManager.InitializeItemStack(Material.RED_STAINED_GLASS_PANE,
                    ChatColor.GRAY.toString() + GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER) + "/" + Main.GetInstance().statSheetManager.GetLevelXPRequirements(GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER)));

            // place the xp bar items in the middle of the fourth row
            for (int i = -1; i < 2; i++)
            {
                // if the xp is more than 33 percent of the way full, light up the first one. 66 for the second, and 99 for the third
                ItemStack tempXpBar = xpBar;

                if (0.33 * (i+2) < ((float) GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER) / (float) Main.GetInstance().statSheetManager.GetLevelXPRequirements(GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER))))
                {
                    tempXpBar.setType(Material.BLACK_STAINED_GLASS_PANE);
                }

                mainStatSheetGUI.setItem((int) ((MenuManager.FULL_ROW_SIZE*3.5) + i), xpBar);
            }
        }

        ItemStack statMenuIcon = MenuManager.InitializeItemStack(Material.BOOK, ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Stats");
        MenuManager.AddPersistentDataContainerToItemStack(statMenuIcon, NamespaceDefinitions.GetUIKey(), "statMenu");

        // add the stat menu icon seven to the side and one down
        mainStatSheetGUI.setItem(7+(MenuManager.FULL_ROW_SIZE), statMenuIcon);

        // return the menu
        return mainStatSheetGUI;
    }

    @Override
    public void OnClick(InventoryClickEvent e)
    {
        // cancel the event so the player can't take items
        e.setCancelled(true);

        // if the owner click on an item
        if (e.getCurrentItem() == null)
        {
            return;
        }

        // if the item is a UI item
        if (e.getCurrentItem().getPersistentDataContainer().has(NamespaceDefinitions.GetUIKey()))
        {
            // cancel the event so the player can't take items
            e.setCancelled(true);

            switch (e.getCurrentItem().getPersistentDataContainer().get(NamespaceDefinitions.GetUIKey(), PersistentDataType.STRING))
            {
                case "statMenu" :
                    // play a button click sound
                    GetOwner().playSound(GetOwner().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5f, 1);

                    MenuManager.AssignGUIState(new ShownStatsSheetGUI(GetOwner(), this), GetOwner());
                    break;

                case "race" :
                    // play a button click sound
                    GetOwner().playSound(GetOwner().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5f, 1);

                    MenuManager.AssignGUIState(new RaceInfoGUI(GetOwner(), this), GetOwner());
                    break;

                case "subrace" :
                    // play a button click sound
                    GetOwner().playSound(GetOwner().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5f, 1);

                    MenuManager.AssignGUIState(new SubraceInfoGUI(GetOwner(), this, Main.GetInstance().statSheetManager.FindRace(GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING))), GetOwner());
                    break;

                case "class" :
                    // play a button click sound
                    GetOwner().playSound(GetOwner().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5f, 1);

                    MenuManager.AssignGUIState(new ClassInfoGUI(GetOwner(), this), GetOwner());
                    break;
            }
        }

    }
}
