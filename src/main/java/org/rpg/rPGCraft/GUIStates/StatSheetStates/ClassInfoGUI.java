package org.rpg.rPGCraft.GUIStates.StatSheetStates;

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

public class ClassInfoGUI extends GUIState
{
    private int xOffset = 0;
    private int yOffset = 0;
    private PlayableClass playableClass;

    public ClassInfoGUI(Player owner, GUIState lastState)
    {
        super(owner, "class_info", 54, lastState);

        if (GetOwner().getPersistentDataContainer().has(NamespaceDefinitions.GetClassKey()))
        {
            playableClass = Main.GetInstance().statSheetManager.FindClass(GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING));
        }
    }

    @Override
    public Inventory InitializeNewInventoryInstance()
    {
        // create the menu
        Inventory traitTreeMenu = Bukkit.createInventory(GetOwner(), GetInventorySize(), ChatColor.BOLD.toString() + "Class Info");

        // return if there isn't a playableClass
        if (playableClass == null) return traitTreeMenu;

        // place the nodes and the border
        UpdateTraitTree(traitTreeMenu);

        // add the back button to the bottom left corner
        ItemStack classBackButton = MenuManager.GetBackButton();

        traitTreeMenu.setItem(MenuManager.FULL_ROW_SIZE*5, classBackButton);

        // return the menu
        return traitTreeMenu;
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

        // get the info icon
        ItemStack infoIcon = MenuManager.InitializeItemStack(Material.NETHER_STAR, ChatColor.BOLD.toString() + ChatColor.RED + "Class Info",
                List.of("Class : " + playableClass.name, "Level : " + GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER), "Available trait points : " + Main.GetInstance().statSheetManager.FindStatSheetByPlayer(GetOwner()).GetAvailableTraitPoints()));

        // add the info
        inventory.setItem((int) (inventory.getSize()-(MenuManager.FULL_ROW_SIZE*0.5)), infoIcon);
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

        // if a trait node wasn't pressed
        if (!e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(NamespaceDefinitions.GetTraitKey(), PersistentDataType.STRING))
        {
            return;
        }

        // get the node
        int x = (int) (e.getSlot() - (MenuManager.FULL_ROW_SIZE * Math.floor((double) e.getSlot() / MenuManager.FULL_ROW_SIZE)));
        int y = Math.abs(((e.getSlot()-x) / MenuManager.FULL_ROW_SIZE)-4);

        Node node = playableClass.traitTree.GetNodeAtCoordinates(new Vector2d(x + xOffset, y + yOffset));

        // if this was a right click
        if (e.getAction() == InventoryAction.PICKUP_HALF)
        {
            // if you have this trait
            if (node.IsNodeOwned(GetOwner()))
            {
                // play a button click sound
                GetOwner().playSound(GetOwner().getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.PLAYERS, 0.5f, 1);

                // Get the trait of the node
                Trait nodeTrait = node.GetTraits().get(node.GetNodeLevel(GetOwner())-1);

                // if this trait is turned off
                if (!node.IsNodeEnabled(GetOwner()))
                {
                    String newDeactivatedNodes = GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetDeactivatedNodesKey(), PersistentDataType.STRING);
                    newDeactivatedNodes = newDeactivatedNodes.replace("_" + nodeTrait.name_id + node.GetID(), "");

                    // activate this trait
                    GetOwner().getPersistentDataContainer().set(NamespaceDefinitions.GetDeactivatedNodesKey(), PersistentDataType.STRING, newDeactivatedNodes);
                    nodeTrait.OnGainTraitBuff(GetOwner());
                }
                else
                {
                    String newDeactivatedNodes = GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetDeactivatedNodesKey(), PersistentDataType.STRING);
                    newDeactivatedNodes = newDeactivatedNodes + "_" + nodeTrait.name_id + node.GetID();

                    // deactivate this trait
                    GetOwner().getPersistentDataContainer().set(NamespaceDefinitions.GetDeactivatedNodesKey(), PersistentDataType.STRING,newDeactivatedNodes);
                    nodeTrait.OnRemoveTraitBuff(GetOwner());
                }

            }
        }
        else if (e.getAction() != InventoryAction.HOTBAR_SWAP && node.IsNodeEnabled(GetOwner()))
        {
            // if the player has enough trait selection points(equal to the players level)
            if (Main.GetInstance().statSheetManager.FindStatSheetByPlayer(GetOwner()).GetAvailableTraitPoints() >= 1)
            {
                // if the player already owns this trait
                if (node.IsNodeOwned(GetOwner()))
                {
                    // check if the node has more levels
                    if (node.GetTraits().size() > node.GetNodeLevel(GetOwner()))
                    {
                        // play a button click sound
                        GetOwner().playSound(GetOwner().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5f, 1);

                        // remove the old trait
                        node.GetTraits().get(node.GetNodeLevel(GetOwner()) - 1).OnRemoveTraitBuff(GetOwner());
                        GetOwner().getPersistentDataContainer().set(
                                NamespaceDefinitions.GetTreeProgressionKey(), PersistentDataType.STRING, GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetTreeProgressionKey(), PersistentDataType.STRING).replace("_" + node.GetTraits().get(node.GetNodeLevel(GetOwner()) - 1).name_id + node.GetID(), ""));

                        // add the new trait
                        node.GetTraits().get(node.GetNodeLevel(GetOwner())).OnGainTraitBuff(GetOwner());
                        GetOwner().getPersistentDataContainer().set(
                                NamespaceDefinitions.GetTreeProgressionKey(), PersistentDataType.STRING, GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetTreeProgressionKey(), PersistentDataType.STRING) + "_" + node.GetTraits().get(node.GetNodeLevel(GetOwner())).name_id + node.GetID());

                    }
                }
                else
                {
                    // if this node is not at y 0
                    if (node.GetCoordinates().y > 0)
                    {
                        // if the player has already selected an adjacent node
                        boolean hasAdjacentNode = false;

                        for (Node adjacentNode : playableClass.traitTree.GetSurroundingNodes(node))
                        {
                            // if the player has this node
                            if (adjacentNode.IsNodeOwned(GetOwner()))
                            {
                                hasAdjacentNode = true;
                                break;
                            }
                        }

                        if (!hasAdjacentNode)
                        {
                            return;
                        }
                    }

                    // add the new trait
                    node.GetTraits().getFirst().OnGainTraitBuff(GetOwner());
                    GetOwner().getPersistentDataContainer().set(
                            NamespaceDefinitions.GetTreeProgressionKey(), PersistentDataType.STRING, GetOwner().getPersistentDataContainer().get(NamespaceDefinitions.GetTreeProgressionKey(), PersistentDataType.STRING) + "_" + node.GetTraits().getFirst().name_id + node.GetID());

                    // play a button click sound
                    GetOwner().playSound(GetOwner().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5f, 1);

                    // set the clicked slot to the new node icon
                    ItemStack newNodeIcon = node.GetNodeIcon(0, false);
                    newNodeIcon.setType(Material.LIME_STAINED_GLASS_PANE);

                    e.getInventory().setItem(e.getRawSlot(), newNodeIcon);
                }
            }
        }

        UpdateTraitTree(GetInventory());
    }
}
