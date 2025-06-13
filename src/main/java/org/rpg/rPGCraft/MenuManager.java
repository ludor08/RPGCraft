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
        Bukkit.getPluginManager().registerEvents(this,main);


        // generate the back button
        ItemMeta backButtonMeta = backButton.getItemMeta();
        backButtonMeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Back");

        backButton.setItemMeta(backButtonMeta);

        // generate the confirm button
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        confirmButtonMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Confirm");
        confirmButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "confirm");

        confirmButton.setItemMeta(confirmButtonMeta);
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
        ItemStack subraceBackButton = backButton;
        ItemMeta subraceBackButtonMeta = subraceBackButton.getItemMeta();

        subraceBackButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "back_race");

        subraceBackButton.setItemMeta(subraceBackButtonMeta);

        subraceMenu.setItem(FULL_ROW_SIZE*4, subraceBackButton);

        // return the menu
        return subraceMenu;
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        player.getPersistentDataContainer().remove(main.GetRaceKey());

        // if the player has not yet chosen a race when they join the game, give them a prompt to choose a race
        if (!player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
        {
            player.openInventory(CreateRaceMenu(e.getPlayer(), main.GetChooseAbleRaces(), 1, "Select a Race!"));
        }
    }

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e)
    {
        // if the player did not click on an item
        if (e.getCurrentItem() == null)
        {
            return;
        }

        ItemStack clickedItem = e.getCurrentItem();
        Player player = (Player) e.getWhoClicked();

        // check if a UI button was clicked
        if (clickedItem.getItemMeta().getPersistentDataContainer().has(main.GetUIKey(), PersistentDataType.STRING))
        {
            // cancel the event so the player can't take items
            e.setCancelled(true);

            String[] UIPersistents = clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetUIKey(), PersistentDataType.STRING).split("_");

            // if it was a back button
            if (Objects.equals(UIPersistents[0], "back"))
            {
                switch (UIPersistents[1])
                {
                    // if the back button has the data of race then go back to the race menu
                    case "race" :
                        player.openInventory(CreateRaceMenu(player, main.GetChooseAbleRaces(), 1, "Select a Race!"));
                        break;

                    // if the back button has the data of subrace then go back to the corresponding subrace menu
                    case "subrace" :
                        // find the corresponding parent race
                        for (Race race : main.GetChooseAbleRaces())
                        {
                            // if the parent race name is the same as the parent race as the data in the back button, open the subrace menu
                            if (Objects.equals(race.name, UIPersistents[2]))
                            {
                                player.openInventory(CreateSubraceMenu(player, race));
                            }
                        }
                        break;

                    default:
                        player.sendMessage("undefined menu exception : " + UIPersistents[1]);
                }
            }
            // if it was a confirm button
            else if (Objects.equals(UIPersistents[0], "confirm"))
            {
                switch (UIPersistents[1])
                {
                    case "subrace" :
                        // find the subrace
                        for (int i = 0; i < e.getInventory().getSize(); i++)
                        {
                            // if this item is not null
                            if (e.getInventory().getItem(i) != null)
                            {
                                // if this item has the race PersistentDataContainer
                                if (e.getInventory().getItem(i).getItemMeta().getPersistentDataContainer().has(main.GetRaceKey()))
                                {
                                    player.getPersistentDataContainer().set(main.GetSubraceKey(), PersistentDataType.STRING, e.getInventory().getItem(i).getItemMeta().getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING));
                                    break;
                                }
                            }
                        }

                        // add the race PersistentDataContainer to the player
                        player.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, UIPersistents[2]);

                        // close the inventory
                        e.getInventory().close();
                        break;
                }
            }
        }

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
                    // open the confirm menu
                    player.openInventory(CreateConfirmMenu(player, List.of(clickedItem,e.getClickedInventory().getItem(FULL_ROW_SIZE/2)), "Subrace", "subrace_" + parentPersistent));
                }
            }

        }
    }

    public Inventory CreateConfirmMenu(Player player, List<ItemStack> itemsToConfirm, String confirmation, String buttonData)
    {
        // create the menu
        Inventory confirmMenu = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + "Confirm " + confirmation + "?");

        // generate the item for the border
        ItemStack border = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();

        borderMeta.setDisplayName(" ");
        border.setItemMeta(borderMeta);

        // fill the menu with the borders
        for (int i = 0; i < confirmMenu.getSize(); i++)
        {
            confirmMenu.setItem(i, border);
        }

        // place the icons and buttons
        confirmMenu = GenerateIconPositions(itemsToConfirm, confirmMenu, 7, 1);

        // add the back button to the bottom left corner
        ItemStack subraceBackButton = backButton;
        ItemMeta subraceBackButtonMeta = subraceBackButton.getItemMeta();

        subraceBackButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "back_" + buttonData);

        subraceBackButton.setItemMeta(subraceBackButtonMeta);

        confirmMenu.setItem(FULL_ROW_SIZE*4, backButton);

        // add the confirm button to the row under the icons
        ItemStack subraceConfirmButton = confirmButton;
        ItemMeta subraceConfirmButtonMeta = subraceConfirmButton.getItemMeta();
        subraceConfirmButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "confirm_" + buttonData);

        subraceConfirmButton.setItemMeta(subraceConfirmButtonMeta);

        confirmMenu.setItem((int) (FULL_ROW_SIZE*3.5f), subraceConfirmButton);

        // return the menu
        return confirmMenu;
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
