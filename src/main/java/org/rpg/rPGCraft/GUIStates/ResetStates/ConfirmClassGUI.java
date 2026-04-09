package org.rpg.rPGCraft.GUIStates.ResetStates;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector2d;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.GUIStates.GUIState;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class ConfirmClassGUI extends GUIState
{
    private int xOffset = 0;
    private int yOffset = 0;
    private PlayableClass playableClass;

    public ConfirmClassGUI(Player owner, GUIState lastState, PlayableClass playableClass)
    {
        super(owner, "confirm_class", 54, lastState);

        this.playableClass = playableClass;
    }

    @Override
    public Inventory InitializeNewInventoryInstance()
    {
        // create the menu
        Inventory classMenu = Bukkit.createInventory(GetOwner(), GetInventorySize(),
                ChatColor.BOLD.toString() + "Confirm a Class!");

        // return if there isn't a playableClass
        if (playableClass == null) return classMenu;

        // place the nodes and background
        UpdateTraitTree(classMenu);

        // add the confirm button to the row under the icons
        ItemStack classConfirmButton = MenuManager.GetConfirmButton();

        classMenu.setItem((int) (GetInventorySize()-(MenuManager.FULL_ROW_SIZE*0.5)), classConfirmButton);

        // add the back button to the bottom left corner
        ItemStack classBackButton = MenuManager.GetBackButton();

        classMenu.setItem(MenuManager.FULL_ROW_SIZE*5, classBackButton);

        // return the menu
        return classMenu;
    }

    private void UpdateTraitTree(Inventory inventory)
    {
        // Move the trait tree up or down
        for (int x = xOffset; x < xOffset+MenuManager.FULL_ROW_SIZE; x++)
        {
            for (int y = yOffset; y < yOffset+5; y++)
            {
                Node node = playableClass.traitTree.GetNodeAtCoordinates(new Vector2d(x,y));
                int inventoryIndex = (x - xOffset) + ((4 - (y - yOffset)) * MenuManager.FULL_ROW_SIZE);

                if (node == null)
                {
                    ItemStack nothingStack = MenuManager.InitializeItemStack(Material.RED_STAINED_GLASS_PANE, " ");
                    ItemMeta nothingMeta = nothingStack.getItemMeta();

                    nothingMeta.setHideTooltip(true);

                    nothingStack.setItemMeta(nothingMeta);

                    inventory.setItem(inventoryIndex, nothingStack);

                    continue;
                }

                ItemStack nodeIcon = node.GetNodeIcon(node.GetNodeLevel(GetOwner()), !node.IsNodeEnabled(GetOwner()));

                inventory.setItem(inventoryIndex, nodeIcon);
            }
        }

        // if the slot coordinates do correspond to the bottom row
        for (int i = 0; i < MenuManager.FULL_ROW_SIZE; i++)
        {
            if (i == 0 || i == 4)
            {
                continue;
            }

            ItemStack border = MenuManager.InitializeItemStack(Material.BLACK_STAINED_GLASS_PANE, " ");
            ItemMeta borderMeta = border.getItemMeta();

            borderMeta.setHideTooltip(true);
            border.setItemMeta(borderMeta);

            // add border to the inventory
            inventory.setItem(GetInventorySize() - MenuManager.FULL_ROW_SIZE + i, border);

        }

        // Add the up/down buttons if they need to be
        boolean notAtTheTop = false;

        for (Node node : playableClass.traitTree.GetNode())
        {
            if (node.GetCoordinates().y > yOffset + 4) {
                notAtTheTop = true;
                break;
            }
        }

        if (notAtTheTop)
        {
            ItemStack upButton = MenuManager.InitializeItemStack(Material.ARROW, "Up");
            MenuManager.AddPersistentDataContainerToItemStack(upButton, NamespaceDefinitions.GetUIKey(), "up");

            inventory.setItem(MenuManager.FULL_ROW_SIZE * 6 - 1, upButton);
        }

        // if we're not at the bottom
        if (yOffset > 0)
        {
            // add the up item to the bottom row and 7 to the side
            ItemStack downButton = MenuManager.InitializeItemStack(Material.ARROW, "Down");
            MenuManager.AddPersistentDataContainerToItemStack(downButton, NamespaceDefinitions.GetUIKey(), "down");

            inventory.setItem(MenuManager.FULL_ROW_SIZE * 6 - 2, downButton);
        }
    }

    @Override
    public void OnClick(InventoryClickEvent e)
    {
        // cancelled the event
        e.setCancelled(true);

        // if a UI button was pressed
        if (e.getCurrentItem().getPersistentDataContainer().has(NamespaceDefinitions.GetUIKey()))
        {
            // play a button click sound
            GetOwner().playSound(GetOwner().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5f, 1);

            switch (e.getCurrentItem().getPersistentDataContainer().get(NamespaceDefinitions.GetUIKey(), PersistentDataType.STRING))
            {
                case "up":
                    yOffset++;
                    break;

                case "down":
                    yOffset--;
                    break;
            }

            UpdateTraitTree(GetInventory());
        }

        // if the player clicked the confirm button
        if (e.getCurrentItem().getPersistentDataContainer().has(NamespaceDefinitions.GetUIKey())
                && e.getCurrentItem().getPersistentDataContainer().get(NamespaceDefinitions.GetUIKey(), PersistentDataType.STRING).equals("confirm"))
        {
            Main.GetInstance().statSheetManager.FindStatSheetByPlayer(GetOwner()).SetClassPersistent(playableClass.name);

            // send the confirmation message
            GetOwner().sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Class Selected!");

            // play a ding sound
            GetOwner().playSound(GetOwner().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5f, 1);

            // close the menu
            GetOwner().closeInventory();
        }
    }
}
