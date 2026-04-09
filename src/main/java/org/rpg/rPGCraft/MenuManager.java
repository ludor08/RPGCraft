package org.rpg.rPGCraft;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector2d;
import org.rpg.rPGCraft.GUIStates.GUIState;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.*;

public class MenuManager implements Listener
{
    private static HashMap<Player, GUIState> playerGUIStateHashMap = new HashMap<>();

    public static final int FULL_ROW_SIZE = 9;

    public MenuManager()
    {
        Bukkit.getPluginManager().registerEvents(this,Main.GetInstance());
    }

    public static void AssignGUIState(GUIState state, Player player)
    {
        // if the player is in a gui
        if (playerGUIStateHashMap.containsKey(player))
        {
            // close the gui and replace it in the map
            playerGUIStateHashMap.get(player).OnClose();
        }

        // open the menu
        state.Open();

        // add it to the map
        playerGUIStateHashMap.put(player, state);
    }

    public static ItemStack GetBackButton()
    {
        ItemStack backButton = InitializeItemStack(Material.GUNPOWDER, ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Back");
        AddPersistentDataContainerToItemStack(backButton, NamespaceDefinitions.GetUIKey(), "back");

        return backButton;
    }

    public static ItemStack GetConfirmButton()
    {
        ItemStack confirmButton = InitializeItemStack(Material.LIME_CONCRETE, ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Confirm");
        AddPersistentDataContainerToItemStack(confirmButton, NamespaceDefinitions.GetUIKey(), "confirm");

        return confirmButton;
    }

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e)
    {
        // if the player did not click on an item
        if (e.getCurrentItem() == null)
        {
            return;
        }

        // if the player has a menu open
        if (e.getWhoClicked() instanceof Player player && playerGUIStateHashMap.containsKey(player))
        {
            // if the player clicked a back button
            if (e.getCurrentItem().getPersistentDataContainer().has(NamespaceDefinitions.GetUIKey())
                && e.getCurrentItem().getPersistentDataContainer().get(NamespaceDefinitions.GetUIKey(), PersistentDataType.STRING).equals("back"))
            {
                playerGUIStateHashMap.get(player).Back();
            }

            playerGUIStateHashMap.get(player).OnClick(e);
        }
    }

    @EventHandler
    public void OnClose(InventoryCloseEvent e)
    {
        // if the player has a menu open
        if (e.getPlayer() instanceof Player player)
        {
            playerGUIStateHashMap.remove(player);
        }
    }

    public static Inventory AddBorder(Material borderType, Inventory inventory)
    {
        // generate the item for the border
        ItemStack border = new ItemStack(borderType);
        ItemMeta borderMeta = border.getItemMeta();

        borderMeta.setDisplayName(" ");
        borderMeta.setHideTooltip(true);
        border.setItemMeta(borderMeta);

        // fill the menu with the borders
        for (int i = 0; i < inventory.getSize(); i++)
        {
            inventory.setItem(i, border);
        }

        return inventory;
    }

    public static void AddPersistentDataContainerToItemStack(ItemStack stack, NamespacedKey key, String string)
    {
        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, string);

        stack.setItemMeta(meta);
    }

    public static ItemStack InitializeItemStack(Material material, String itemName)
    {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();

        if (itemName != null)
        {
            meta.setDisplayName(itemName);
        }

        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack InitializeItemStack(Material material, String itemName, List<String> lore)
    {
        ItemStack stack = InitializeItemStack(material, itemName);
        AddLoreToItemStack(stack, lore);

        return stack;
    }

    public static void AddLoreToItemStack(ItemStack stack, List<String> lore)
    {
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(lore);

        stack.setItemMeta(meta);
    }

    public static void AddIconInPositions(Inventory inventory, List<ItemStack> icons, int maxItemsPerRow, int startRow)
    {
        for (int y = 0; y < Math.ceil(((float) icons.size()) / maxItemsPerRow); y++)
        {
            int rowSize = Math.min(icons.size()-(maxItemsPerRow *y), maxItemsPerRow);

            for (int x = 0; x < rowSize; x++)
            {
                // get the icon
                ItemStack icon = icons.get(y* maxItemsPerRow +x);

                // get the middle of the menu
                int placePosition = (MenuManager.FULL_ROW_SIZE / 2) + ((y+ startRow) * MenuManager.FULL_ROW_SIZE);

                // move placePosition to the left
                placePosition -= rowSize/2;

                // start moving to the right
                placePosition += x;

                // add a separation if rowSize is even and there is more then one item in this row
                if (rowSize%2 == 0 && x >= rowSize/2)
                {
                    placePosition++;
                }

                // place the icons with a separation in the middle
                inventory.setItem(placePosition, icon);
            }
        }
    }
}
