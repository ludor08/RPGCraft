package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuManager implements Listener
{

    private ItemStack backButton = new ItemStack(Material.GUNPOWDER);
    private ItemStack confirmButton = new ItemStack(Material.LIME_CONCRETE);

    int FULL_ROW_SIZE = 9;

    Main main;

    public MenuManager(Main main) {
        this.main = main;

        // generate the back button
        ItemMeta backButtonMeta = backButton.getItemMeta();
        backButtonMeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Back");
        backButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "back");

        backButton.setItemMeta(backButtonMeta);

        // generate the confirm button
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        confirmButtonMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Confirm");
        confirmButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "confirm");

        confirmButton.setItemMeta(backButtonMeta);
    }

    public Inventory CreateRaceMenu(Player player, List<Race> races, int startRow, String inventoryTitle)
    {
        // create the menu
        Inventory raceMenu = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + inventoryTitle);

        // generate the item for the border
        ItemStack border = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();

        borderMeta.setDisplayName(" ");
        border.setItemMeta(borderMeta);

        // fill the menu with the borders
        for (int i = 0; i < raceMenu.getSize(); i++)
        {
            raceMenu.setItem(i, border);
        }

        // extract all of the icons from races
        List<ItemStack> raceIcons = new ArrayList<>();
        for (Race race : races)
        {
            ItemStack icon = race.GetRaceIcon();

            // add the PersistentDataContainer to the icon
            ItemMeta iconMeta = icon.getItemMeta();
            iconMeta.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, race.name);

            icon.setItemMeta(iconMeta);

            raceIcons.add(icon);
        }

        // generate the icon positions and place them
        raceMenu = GenerateIconPositions(raceIcons, raceMenu, 7, startRow);

        // return the menu
        return raceMenu;
    }

    public Inventory CreateSubraceMenu(Player player, Race parentRace)
    {
        // create the menu
        Inventory subraceMenu = CreateRaceMenu(player, parentRace.subraces, 2, "Select a Subrace!");

        // get the parentRace icon
        ItemStack parentIcon = parentRace.GetRaceIcon();

        ItemMeta iconMeta = parentIcon.getItemMeta();
        iconMeta.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, parentRace.name);

        parentIcon.setItemMeta(iconMeta);

        // add the parentRace icon to the top center
        subraceMenu.setItem(FULL_ROW_SIZE/2, parentIcon);

        // add the back button to the bottom left corner
        subraceMenu.setItem(FULL_ROW_SIZE*4, backButton);

        // return the menu
        return subraceMenu;
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        // if the player has not yet chosen a race when they join the game, give them a prompt to choose a race
        if (!player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
        {
            player.openInventory(CreateRaceMenu(e.getPlayer(), main.GetChooseAbleRaces(), 1, "Select a Race!"));
        }
    }

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e)
    {
        // if the player clicked on an item
        if (e.getCurrentItem() != null)
        {
            ItemStack clickedItem = e.getCurrentItem();
            Player player = (Player) e.getWhoClicked();

            // if the player is in the select race inventory
            if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BOLD.toString() + "Select a Race!"))
            {
                // cancel the event so the player can't take items
                e.setCancelled(true);

                // if the player didn't click a border
                if (clickedItem.getItemMeta().getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
                {
                    // get the PersistentDataContainer of the icon clicked
                    String racePersistent = clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING);

                    // find the race clicked
                    for (Race race : main.GetChooseAbleRaces())
                    {
                        // if the race name is the same as the race of what the player click, open the new subrace menu
                        if (Objects.equals(race.name, racePersistent))
                        {
                            player.openInventory(CreateSubraceMenu(player, race));
                        }
                    }

                }
            }
            // if the player is in the select subrace inventory
            else if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BOLD.toString() + "Select a Subrace!"))
            {
                // cancel the event so the player can't take items
                e.setCancelled(true);

                // if the player didn't click a border
                if (clickedItem.getItemMeta().getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
                {
                    // get the PersistentDataContainer of the icon clicked
                    String clickedPersistent = clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING);

                    // get the PersistentDataContainer of the parent
                    String parentPersistent = e.getClickedInventory().getItem(FULL_ROW_SIZE/2).getItemMeta().getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING);

                    // if the player didn't click the parent race, generate and open the confirm menu
                    if (clickedPersistent != parentPersistent)
                    {
                        // create the menu
                        Inventory confirmMenu = Bukkit.createInventory(player, 45,
                            ChatColor.BOLD.toString() + "inventoryTitle");

                        // place the icons and buttons
                        confirmMenu = GenerateIconPositions(List.of(clickedItem, e.getClickedInventory().getItem(FULL_ROW_SIZE/2)), confirmMenu, 7, 1);

                        // add the back button to the bottom left corner
                        confirmMenu.setItem(FULL_ROW_SIZE*4, backButton);

                        // add the confirm button to the row under the icons



                    }
                }

            }
        }
    }

    public Inventory GenerateIconPositions(List<ItemStack> icons, Inventory inventory, int maxItemsPerRow, int startRow)
    {
        for (int y = 0; y < Math.ceil(((float) icons.size()) / maxItemsPerRow)+0; y++)
        {
            int rowSize = Math.min(icons.size()-(maxItemsPerRow*y), maxItemsPerRow);

            for (int x = 0; x < rowSize; x++)
            {
                // get the icon
                ItemStack icon = icons.get(y*maxItemsPerRow+x);

                // get the middle of the menu
                int placePosition = (FULL_ROW_SIZE / 2) + ((y+startRow) * FULL_ROW_SIZE);

                // move placePosition to the left
                placePosition -= rowSize/2;

                // start moving to the right
                placePosition += x;

                // add a separation if rowSize is even and there is more then one item in this row
                if (rowSize%2 == 0 && x >= rowSize/2)
                {
                    placePosition += 1;
                }

                // place the icons with a separation in the middle
                inventory.setItem(placePosition, icon);
            }
        }

        return inventory;
    }
}
