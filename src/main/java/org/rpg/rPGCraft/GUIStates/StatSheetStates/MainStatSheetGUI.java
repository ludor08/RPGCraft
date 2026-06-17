package org.rpg.rPGCraft.GUIStates.StatSheetStates;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
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

        // if the owner has a race
        Race race = Main.GetInstance().statSheetManager.FindStatSheetByPlayer(GetOwner()).GetRace();

        if (race != null)
        {
            // if the owner has a subrace
            if (GetOwner().getPersistentDataContainer().has(MyNamespaces.SUBRACE.GetNamespacedKey(), PersistentDataType.STRING))
            {
                // if there is a subrace
                Race subrace = Main.GetInstance().statSheetManager.FindStatSheetByPlayer(GetOwner()).GetSubrace();

                if (subrace != null)
                {
                    // Get the subrace icon
                    ItemStack subraceIcon = subrace.GetRaceIcon();
                    MenuManager.AddPersistentDataContainerToItemStack(subraceIcon, MyNamespaces.UI.GetNamespacedKey(), "subrace");

                    // place the subrace icon one to the side and three down
                    mainStatSheetGUI.setItem(1+(MenuManager.FULL_ROW_SIZE*3), subraceIcon);
                }
                // if there isn't
                else
                {
                    // place a null icon one to the side and three down
                    ItemStack nullIcon = MenuManager.InitializeItemStack(Material.BARRIER, ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Null");
                    MenuManager.AddPersistentDataContainerToItemStack(nullIcon, MyNamespaces.UI.GetNamespacedKey(), "null");

                    mainStatSheetGUI.setItem(1+(MenuManager.FULL_ROW_SIZE*3), nullIcon);
                }
            }

            // Get the race icon
            ItemStack raceIcon = race.GetRaceIcon();
            MenuManager.AddPersistentDataContainerToItemStack(raceIcon, MyNamespaces.UI.GetNamespacedKey(), "race");

            // place the race icon one to the side and one down
            mainStatSheetGUI.setItem(1+MenuManager.FULL_ROW_SIZE, raceIcon);
        }

        // if the owner has a class
        PlayableClass playableClass = Main.GetInstance().statSheetManager.FindStatSheetByPlayer(GetOwner()).GetClass();

        if (playableClass != null)
        {
            // Get the class icon
            ItemStack classIcon = playableClass.GetClassIcon();

            MenuManager.AddLoreToItemStack(classIcon, List.of("Level: " + GetOwner().getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER)));
            MenuManager.AddPersistentDataContainerToItemStack(classIcon, MyNamespaces.UI.GetNamespacedKey(), "class");

            // place the class icon four to the side and one down
            mainStatSheetGUI.setItem(4+(MenuManager.FULL_ROW_SIZE), classIcon);
        }

        // if the player has a xp and level persistent
        if (GetOwner().getPersistentDataContainer().has(MyNamespaces.CLASS_XP.GetNamespacedKey(), PersistentDataType.INTEGER)
                && GetOwner().getPersistentDataContainer().has(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER))
        {
            // make the xp bar item
            ItemStack xpBar = MenuManager.InitializeItemStack(Material.RED_STAINED_GLASS_PANE,
                    ChatColor.GRAY.toString() + GetOwner().getPersistentDataContainer().get(MyNamespaces.CLASS_XP.GetNamespacedKey(), PersistentDataType.INTEGER) + "/" + Main.GetInstance().statSheetManager.GetLevelXPRequirements(GetOwner().getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER)));

            // place the xp bar items in the middle of the fourth row
            for (int i = -1; i < 2; i++)
            {
                // if the xp is more than 33 percent of the way full, light up the first one. 66 for the second, and 99 for the third
                ItemStack tempXpBar = xpBar;

                if (0.33 * (i+2) < ((float) GetOwner().getPersistentDataContainer().get(MyNamespaces.CLASS_XP.GetNamespacedKey(), PersistentDataType.INTEGER) / (float) Main.GetInstance().statSheetManager.GetLevelXPRequirements(GetOwner().getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER))))
                {
                    tempXpBar.setType(Material.BLACK_STAINED_GLASS_PANE);
                }

                mainStatSheetGUI.setItem((int) ((MenuManager.FULL_ROW_SIZE*3.5) + i), xpBar);
            }
        }

        ItemStack statMenuIcon = MenuManager.InitializeItemStack(Material.BOOK, ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Stats");
        MenuManager.AddPersistentDataContainerToItemStack(statMenuIcon, MyNamespaces.UI.GetNamespacedKey(), "statMenu");

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
        if (e.getCurrentItem().getPersistentDataContainer().has(MyNamespaces.UI.GetNamespacedKey()))
        {
            // cancel the event so the player can't take items
            e.setCancelled(true);

            switch (e.getCurrentItem().getPersistentDataContainer().get(MyNamespaces.UI.GetNamespacedKey(), PersistentDataType.STRING))
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

                    MenuManager.AssignGUIState(new SubraceInfoGUI(GetOwner(), this, Main.GetInstance().statSheetManager.FindRace(GetOwner().getPersistentDataContainer().get(MyNamespaces.SUBRACE.GetNamespacedKey(), PersistentDataType.STRING))), GetOwner());
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
