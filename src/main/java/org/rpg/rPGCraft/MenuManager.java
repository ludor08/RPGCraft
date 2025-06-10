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

public class MenuManager implements Listener
{

    int FULL_ROW_SIZE = 9;

    Main main;

    public MenuManager(Main main) {
        this.main = main;
    }

    public void CreateRaceMenu(Player player, List<Race> races)
    {
        // create the menu
        Inventory raceMenu = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + "Select a Race!");

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
        raceMenu = GenerateIconPositions(raceIcons, raceMenu, 7, 1);

        // open the menu
        player.openInventory(raceMenu);
    }

    // TODO make CreateRaceMenu work for this
    public void CreateSubraceMenu(Player player, Race parentRace)
    {
        // create the menu
        Inventory raceMenu = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + "Select a Subrace!");

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
        for (Race race : parentRace.subraces)
        {
            ItemStack icon = race.GetRaceIcon();

            // add the PersistentDataContainer to the icon
            ItemMeta iconMeta = icon.getItemMeta();
            iconMeta.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, race.name);

            icon.setItemMeta(iconMeta);

            raceIcons.add(icon);
        }

        // generate the icon positions and place them
        raceMenu = GenerateIconPositions(raceIcons, raceMenu, 7,2);

        // get the parentRace icon
        ItemStack parentIcon = parentRace.GetRaceIcon();

        ItemMeta iconMeta = parentIcon.getItemMeta();
        iconMeta.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, parentRace.name);

        parentIcon.setItemMeta(iconMeta);

        // add the parentRace icon to the top left corner
        raceMenu.setItem(FULL_ROW_SIZE/2, parentIcon);

        // open the menu
        player.openInventory(raceMenu);
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        // if the player has not yet chosen a race when they join the game, give them a prompt to choose a race
        if (!player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
        {
            CreateRaceMenu(e.getPlayer(), main.GetChooseAbleRaces());
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

            // if the player is in the correct inventory
            if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BOLD.toString() + "Select a Race!"))
            {
                // cancel the event so the player can't take items
                e.setCancelled(true);

                // if the player didn't click a border
                if (clickedItem.getItemMeta().getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
                {
                    String racePersistent = clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING);

                    player.sendMessage(racePersistent + " racePersistent");
                    // find the race clicked
                    for (Race race : main.GetChooseAbleRaces())
                    {
                        player.sendMessage(race.name + " race");
                        // if the race name is the same as the race of what the player click, open the new subrace menu
                        if (race.name == racePersistent)
                        {
                            player.sendMessage(race.name + " == " + racePersistent);
                            CreateSubraceMenu(player, race);
                        }
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
