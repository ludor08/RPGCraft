package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuManager implements Listener
{
    private ItemStack backButton = new ItemStack(Material.GUNPOWDER);
    private ItemStack confirmButton = new ItemStack(Material.LIME_CONCRETE);

    private ItemStack nullIcon = new ItemStack(Material.BARRIER);
    private ItemStack statMenuIcon = new ItemStack(Material.BOOK);

    int FULL_ROW_SIZE = 9;

    Main main;

    public MenuManager(Main main)
    {
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

        // generate the none icon
        ItemMeta nullIconMeta = nullIcon.getItemMeta();
        nullIconMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Null");
        nullIconMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "null");

        nullIcon.setItemMeta(nullIconMeta);

        // generate the stat menu icon
        ItemMeta statMenuIconMeta = statMenuIcon.getItemMeta();
        statMenuIconMeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Stats");
        statMenuIconMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "statMenu");

        statMenuIcon.setItemMeta(statMenuIconMeta);
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
        if (clickedItem.getItemMeta() != null
                && clickedItem.getItemMeta().getPersistentDataContainer().has(main.GetUIKey(), PersistentDataType.STRING))
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

                    case "trait" :
                        // if there is a third argument
                        if (UIPersistents.length > 2)
                        {
                            // make the back button data
                            String backButtonData = UIPersistents[0];

                            for (int i = 1; i < UIPersistents.length-1; i++)
                            {
                                backButtonData += "_"+UIPersistents[i];
                            }

                            // open a race info menu
                            player.openInventory(CreateRaceInfoMenu(player, main.statSheetManager.FindRace(UIPersistents[2]), backButtonData));
                        }
                        // if there isn't a third argument
                        else
                        {
                            // open the main stat sheet menu
                            player.openInventory(CreateStatSheetMenu(player));
                        }
                        break;

                    case "stat" :
                        // if there is a third argument
                        if (UIPersistents.length > 2)
                        {
                            // open a stat menu
                            player.openInventory(CreateStatMenu(player));
                        }
                        // if there isn't a third argument
                        else
                        {
                            // open the main stat sheet menu
                            player.openInventory(CreateStatSheetMenu(player));
                        }
                        break;

                    case "class" :
                        // if we are in the select menu
                        if (Objects.equals(UIPersistents[2], "view"))
                        {
                            // open a class select menu
                            player.openInventory(CreateClassMenu(player, main.GetChooseAbleClasses()));
                        }
                        // if we are in the stat sheet menu
                        else if (Objects.equals(UIPersistents[2], "stat"))
                        {
                            // open the stat sheet menu
                            player.openInventory(CreateStatSheetMenu(player));
                        }
                        break;

                    default:
                        player.sendMessage("undefined menu exception : " + clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetUIKey(), PersistentDataType.STRING));
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
                                    main.statSheetManager.FindStatSheetByPlayer(player).SetRacePersistent(e.getInventory().getItem(i).getItemMeta().getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING), UIPersistents[2]);

                                    // send the confirmation message
                                    player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Race Selected!");

                                    break;
                                }
                            }
                        }

                        // if the player has not yet chosen a class when they join the game
                        if (!player.getPersistentDataContainer().has(main.GetClassKey(), PersistentDataType.STRING))
                        {
                            // give them a prompt to choose a class
                            player.openInventory(main.menuManager.CreateClassMenu(player, main.GetChooseAbleClasses()));
                        }
                        // if they have a class
                        else
                        {
                            // close the inventory
                            e.getInventory().close();
                        }
                        break;

                    case "class" :
                        // set the class
                        main.statSheetManager.FindStatSheetByPlayer(player).SetClassPersistent(e.getInventory().getItem(FULL_ROW_SIZE/2).getItemMeta().getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING));

                        // send the confirmation message
                        player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Class Selected!");

                        // close the inventory
                        e.getInventory().close();
                        break;

                    default:
                        player.sendMessage("undefined menu exception : " + clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetUIKey(), PersistentDataType.STRING));
                }
            }
            // if it was a stat menu button
            else if (Objects.equals(UIPersistents[0], "statMenu"))
            {
                player.openInventory(CreateStatMenu(player));
            }
            // if it was a trait menu button
            else if (Objects.equals(UIPersistents[0], "traits"))
            {
                player.openInventory(CreateTraitMenu(player, e.getInventory().getItem(4*FULL_ROW_SIZE).getPersistentDataContainer().get(main.GetUIKey(), PersistentDataType.STRING) + "_traits"));
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
        // if the player is in the select class inventory
        else if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BOLD.toString() + "Select a Class!"))
        {
            // cancel the event so the player can't take items
            e.setCancelled(true);

            // if the player didn't click a border
            if (clickedItem.getItemMeta().getPersistentDataContainer().has(main.GetClassKey(), PersistentDataType.STRING))
            {
                // get the PersistentDataContainer of the icon clicked
                String classPersistent = clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING);

                // find the class clicked
                for (PlayableClass playableClass : main.GetChooseAbleClasses())
                {
                    // if the class name is the same as the class of what the player click, open the new class menu
                    if (Objects.equals(playableClass.name, classPersistent))
                    {
                        Inventory classMenu = CreateTraitTreeMenu(player, playableClass, "Confirm Class?", "class_view");
                        // add the confirm button to the row under the icons
                        ItemStack classConfirmButton = confirmButton;
                        ItemMeta classConfirmButtonMeta = classConfirmButton.getItemMeta();
                        classConfirmButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "confirm_class");

                        classConfirmButton.setItemMeta(classConfirmButtonMeta);

                        classMenu.setItem((int) (FULL_ROW_SIZE*5.5f), classConfirmButton);

                        player.openInventory(classMenu);
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
                    player.openInventory(CreateConfirmMenu(player, List.of(clickedItem,e.getClickedInventory().getItem(FULL_ROW_SIZE/2)), "Subrace", "subrace_" + parentPersistent, Material.RED_STAINED_GLASS_PANE));
                }
            }

        }
        // if the player is in the stat sheet
        else if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BOLD.toString() + "Stat Sheet"))
        {
            // cancel the event so the player can't take items
            e.setCancelled(true);

            // if the player clicked a race icon
            if (clickedItem.getItemMeta().getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
            {
                player.openInventory(CreateRaceInfoMenu(player, main.statSheetManager.FindRace(clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING)),null));
            }
            // if the player clicked a class icon
            if (clickedItem.getItemMeta().getPersistentDataContainer().has(main.GetClassKey(), PersistentDataType.STRING))
            {
                // build the trait tree menu
                Inventory traitTreeMenu = CreateTraitTreeMenu(player, main.statSheetManager.FindClass(clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING)),"Class Trait Tree", "class_stat");

                // get the info icon
                ItemStack infoIcon = new ItemStack(Material.NETHER_STAR);
                ItemMeta infoIconMeta = infoIcon.getItemMeta();

                List<String> lore = new ArrayList<>();
                lore.add("Level : " + player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER));
                lore.add("Available trait points : " + (player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) - (player.getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING).split("_").length-1)));

                infoIconMeta.setDisplayName(ChatColor.BOLD.toString() + ChatColor.RED + "Class Info");
                infoIconMeta.setLore(lore);

                infoIcon.setItemMeta(infoIconMeta);

                // add the info
                traitTreeMenu.setItem((int) (traitTreeMenu.getSize()-(FULL_ROW_SIZE*0.5)), infoIcon);

                // open the inventory
                player.openInventory(traitTreeMenu);
            }
        }
        // if the player is in the race info menu in the stat sheet
        else if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BOLD.toString() + "Race Info"))
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
                    // find the subrace
                    Race subrace = main.statSheetManager.FindRace(clickedPersistent);

                    // find the back button
                    String backButtonPersistent = e.getInventory().getItem(4*FULL_ROW_SIZE).getPersistentDataContainer().get(main.GetUIKey(), PersistentDataType.STRING);

                    // open the confirm menu
                    player.openInventory(CreateRaceInfoMenu(player, subrace, backButtonPersistent+"_"+parentPersistent));
                }
            }
        }
        // if the player is in the stats menu in the stat sheet
        else if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BOLD.toString() + "Stats"))
        {
            // cancel the event so the player can't take items
            e.setCancelled(true);
        }
        // if the player is in the traits menu in the stat sheet
        else if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BOLD.toString() + "Traits"))
        {
            // cancel the event so the player can't take items
            e.setCancelled(true);
        }
        // if the player is in the class trait tree menu in the stat sheet
        else if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BOLD.toString() + "Class Trait Tree"))
        {
            // cancel the event so the player can't take items
            e.setCancelled(true);

            // if what the player clicked was a trait node
            if (clickedItem.getItemMeta().getPersistentDataContainer().has(main.GetTraitKey(), PersistentDataType.STRING))
            {
                // find the class
                PlayableClass playableClass = main.statSheetManager.FindClass(player.getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING));

                // already selected traits
                List<String> selectedTraits = Arrays.stream(player.getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING).split("_")).toList();

                // go through all of the traits in selected traits
                for (String trait : selectedTraits)
                {
                    // if the player has already selected this trait
                    if (Objects.equals(trait, clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetTraitKey(), PersistentDataType.STRING)))
                    {
                        return;
                    }
                }

                // if the player has enough trait selection points(equal to the players level)
                if ((player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) - (selectedTraits.size()-1)) >= 1)
                {
                    // add the trait
                    Trait trait = null;

                    // go through all of the nodes
                    for (Node node : playableClass.traitTree.nodes)
                    {
                        // if the node trait name and the trait that you clicked on have the same name
                        if (Objects.equals(clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetTraitKey(), PersistentDataType.STRING), node.trait.name))
                        {
                            trait = node.trait;
                            break;
                        }
                    }

                    // if there is a trait
                    if (trait != null)
                    {
                        trait.OnGainTraitBuff(player);

                        clickedItem.setType(Material.LIME_STAINED_GLASS_PANE);

                        player.getPersistentDataContainer().set(
                                main.GetTreeProgressionKey(), PersistentDataType.STRING, player.getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING) + "_" + clickedItem.getItemMeta().getPersistentDataContainer().get(main.GetTraitKey(), PersistentDataType.STRING));
                    }

                    // get the info icon
                    ItemStack infoIcon = e.getInventory().getItem((int) (e.getInventory().getSize()-(FULL_ROW_SIZE*0.5)));
                    ItemMeta infoIconMeta = infoIcon.getItemMeta();

                    List<String> lore = new ArrayList<>();
                    lore.add("Level : " + player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER));
                    lore.add("Available trait points : " + (player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) - (player.getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING).split("_").length-1)));

                    infoIconMeta.setLore(lore);

                    infoIcon.setItemMeta(infoIconMeta);

                    // add the info
                    e.getInventory().setItem((int) (e.getInventory().getSize()-(FULL_ROW_SIZE*0.5)), infoIcon);
                }
            }
        }
        // if the player is in the select class inventory
        else if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BOLD.toString() + "Confirm Class?"))
        {
            // cancel the event so the player can't take items
            e.setCancelled(true);
        }
    }

    public Inventory CreateTraitTreeMenu(Player player, PlayableClass playableClass, String title, String origin)
    {
        // create the menu
        Inventory traitTreeMenu = Bukkit.createInventory(player, 54,
                ChatColor.BOLD.toString() + title);

        // add the borders
        traitTreeMenu = AddBorder(Material.RED_STAINED_GLASS_PANE, traitTreeMenu);

        // get the parentRace icon
        ItemStack classIcon = playableClass.GetClassIcon();

        ItemMeta iconMeta = classIcon.getItemMeta();
        iconMeta.getPersistentDataContainer().set(main.GetClassKey(), PersistentDataType.STRING, playableClass.name);

        classIcon.setItemMeta(iconMeta);

        // add the parentRace icon to the top center
        traitTreeMenu.setItem(FULL_ROW_SIZE/2, classIcon);

        // go through all of the inventory
        for (int i = 0; i < traitTreeMenu.getSize(); i++)
        {
            // go through all of the nodes
            for (Node node : playableClass.traitTree.nodes)
            {
                // if the slot coordinates
                if (i >= traitTreeMenu.getSize()-FULL_ROW_SIZE)
                {
                    // generate the item for the border
                    ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);;

                    ItemMeta borderMeta = border.getItemMeta();

                    borderMeta.setDisplayName(" ");
                    borderMeta.setHideTooltip(true);
                    border.setItemMeta(borderMeta);

                    // add border to the inventory
                    traitTreeMenu.setItem(i, border);
                }
                // if the slot coordinates are the same as a nodes coordinates
                else if (i == node.GetTranslatedCoordinates(FULL_ROW_SIZE))
                {
                    ItemStack nodeIcon = node.GetNodeIcon();

                    // already selected traits
                    List<String> selectedTraits = Arrays.stream(player.getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING).split("_")).toList();

                    // if the player has this node, light it up
                    if (selectedTraits.contains(node.trait.name))
                    {
                        nodeIcon.setType(Material.LIME_STAINED_GLASS_PANE);
                    }

                    traitTreeMenu.setItem(i, nodeIcon);
                }
            }
        }

        // add the back button to the bottom left corner
        ItemStack classBackButton = backButton;
        ItemMeta classBackButtonMeta = classBackButton.getItemMeta();

        classBackButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "back_" + origin);

        classBackButton.setItemMeta(classBackButtonMeta);

        traitTreeMenu.setItem(FULL_ROW_SIZE*5, classBackButton);

        // return the menu
        return traitTreeMenu;
    }

    public Inventory CreateClassMenu(Player player, List<PlayableClass> classes)
    {
        // create the menu
        Inventory classMenu = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + "Select a Class!");

        // add the borders
        classMenu = AddBorder(Material.RED_STAINED_GLASS_PANE, classMenu);

        // extract all of the icons from races
        List<ItemStack> classIcons = new ArrayList<>();
        for (PlayableClass playableClass : classes)
        {
            ItemStack icon = playableClass.GetClassIcon();

            // add the PersistentDataContainer to the icon
            ItemMeta iconMeta = icon.getItemMeta();
            iconMeta.getPersistentDataContainer().set(main.GetClassKey(), PersistentDataType.STRING, playableClass.name);

            icon.setItemMeta(iconMeta);

            classIcons.add(icon);
        }

        // generate the icon positions and place them
        classMenu = GenerateIconPositions(classIcons, classMenu, 7, 1);

        // return the menu
        return classMenu;
    }

    public Inventory AddBorder(Material borderType, Inventory inventory)
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

    public Inventory CreateRaceMenu(Player player, List<Race> races, int startRow, String inventoryTitle)
    {
        // create the menu
        Inventory raceMenu = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + inventoryTitle);

        // add the borders
        raceMenu = AddBorder(Material.RED_STAINED_GLASS_PANE, raceMenu);

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

    public Inventory CreateStatMenu(Player player)
    {
        // create the stat menu
        Inventory statMenu = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + "Stats");

        // add the borders
        statMenu = AddBorder(Material.LIME_STAINED_GLASS_PANE, statMenu);

        // get the back button
        ItemStack statBackButton = backButton;
        ItemMeta statBackButtonMeta = statBackButton.getItemMeta();

        statBackButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "back_stat");

        // add the back button to the bottom left corner
        statBackButton.setItemMeta(statBackButtonMeta);

        statMenu.setItem(FULL_ROW_SIZE*4, statBackButton);


        // generate the max health icon
        ItemStack maxHealthIcon = new ItemStack(Material.POTION);

        // set the max health icon color
        PotionMeta maxHealthPotion = (PotionMeta) maxHealthIcon.getItemMeta();

        maxHealthPotion.setColor(Color.RED);
        maxHealthIcon.setItemMeta(maxHealthPotion);

        // set the max health icon name
        ItemMeta maxHealthMeta = maxHealthIcon.getItemMeta();

        maxHealthMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Max Health : " + player.getAttribute(Attribute.MAX_HEALTH).getValue());
        maxHealthIcon.setItemMeta(maxHealthMeta);

        // place the icon one to the side and two down
        statMenu.setItem(FULL_ROW_SIZE*2+1, maxHealthIcon);

        // generate the armor mod icon
        ItemStack armorModIcon = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta armorModMeta = armorModIcon.getItemMeta();

        double armorMod = 0;

        for (AttributeModifier modifier : player.getAttribute(Attribute.ARMOR).getModifiers())
        {
            if (!modifier.getKey().namespace().contains("minecraft"))
            {
                armorMod += modifier.getAmount();
            }
        }

        armorModMeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Armor Mod : " + armorMod);
        armorModIcon.setItemMeta(armorModMeta);

        // place the icon two to the side and two down
        statMenu.setItem(FULL_ROW_SIZE*2+2, armorModIcon);

        // generate the trait icon
        ItemStack traitIcon = new ItemStack(Material.PAPER);
        ItemMeta traitMeta = traitIcon.getItemMeta();

        List<Trait> traits = main.statSheetManager.FindStatSheetByPlayer(player).GetTraits();
        List<String> traitsLore = new ArrayList<>();

        for (Trait trait : traits)
        {
            traitsLore.add("- " + trait.name);
        }

        traitMeta.setLore(traitsLore);
        traitMeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Traits");
        traitMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "traits");
        traitIcon.setItemMeta(traitMeta);

        // place the icon four to the side and two down
        statMenu.setItem(FULL_ROW_SIZE*2+4, traitIcon);

        // generate the speed mod icon
        ItemStack speedModIcon = new ItemStack(Material.SUGAR);
        ItemMeta speedModMeta = speedModIcon.getItemMeta();

        double speedMod = 0;

        for (AttributeModifier modifier : player.getAttribute(Attribute.MOVEMENT_SPEED).getModifiers())
        {
            if (!modifier.getKey().namespace().contains("minecraft"))
            {
                speedMod += modifier.getAmount();
            }
        }

        speedModMeta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "Speed Mod : " + speedMod);
        speedModIcon.setItemMeta(speedModMeta);

        // place the icon six to the side and two down
        statMenu.setItem(FULL_ROW_SIZE*2+6, speedModIcon);

        // generate the jump mod icon
        ItemStack jumpModIcon = new ItemStack(Material.RABBIT_FOOT);
        ItemMeta jumpModMeta = jumpModIcon.getItemMeta();

        double jumpMod = 0;

        for (AttributeModifier modifier : player.getAttribute(Attribute.JUMP_STRENGTH).getModifiers())
        {
            if (!modifier.getKey().namespace().contains("minecraft"))
            {
                jumpMod += modifier.getAmount();
            }
        }

        jumpModMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Jump Mod : " + jumpMod);
        jumpModIcon.setItemMeta(jumpModMeta);

        // place the icon seven to the side and two down
        statMenu.setItem(FULL_ROW_SIZE*2+7, jumpModIcon);

        // return the menu
        return statMenu;
    }

    public Inventory CreateTraitMenu(Player player, String origin)
    {
        // create the stat menu
        Inventory traitMenu = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + "Traits");

        // add the borders
        traitMenu = AddBorder(Material.LIME_STAINED_GLASS_PANE, traitMenu);

        // get the back button
        ItemStack statBackButton = backButton;
        ItemMeta statBackButtonMeta = statBackButton.getItemMeta();

        statBackButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, origin);

        // add the back button to the bottom left corner
        statBackButton.setItemMeta(statBackButtonMeta);

        traitMenu.setItem(FULL_ROW_SIZE*4, statBackButton);

        // get all of the trait icons
        List<ItemStack> traitIcons = new ArrayList<>();

        for (Trait trait : main.statSheetManager.FindStatSheetByPlayer(player).GetTraits())
        {
            traitIcons.add(trait.GetTraitIcon());
        }

        // place the trait icons
        traitMenu = GenerateIconPositions(traitIcons, traitMenu,7,2);

        // return the menu
        return traitMenu;
    }

    public Inventory CreateRaceInfoMenu(Player player, Race race, String origin)
    {
        // create the menu
        Inventory raceInfoMenu = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + "Race Info");

        // add the borders
        raceInfoMenu = AddBorder(Material.RED_STAINED_GLASS_PANE, raceInfoMenu);

        // get the back button
        ItemStack traitBackButton = backButton;
        ItemMeta traitBackButtonMeta = traitBackButton.getItemMeta();

        // if there is an origin
        if (origin != null)
        {
            traitBackButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, origin);
        }
        // if there isn't
        else
        {
            traitBackButtonMeta.getPersistentDataContainer().set(main.GetUIKey(), PersistentDataType.STRING, "back_trait");
        }

        // add the back button to the bottom left corner
        traitBackButton.setItemMeta(traitBackButtonMeta);

        raceInfoMenu.setItem(FULL_ROW_SIZE*4, traitBackButton);

        // get the parentRace icon
        ItemStack raceIcon = race.GetRaceIcon();

        ItemMeta raceIconMeta = raceIcon.getItemMeta();
        raceIconMeta.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, race.name);

        raceIcon.setItemMeta(raceIconMeta);

        // add the parentRace icon to the top center
        raceInfoMenu.setItem(FULL_ROW_SIZE/2, raceIcon);

        // if the race has subraces
        if (race.subraces != null)
        {
            // go through all of the traits and add the trait icons
            for (int i = 0; i < race.traits.size(); i++)
            {
                // add the icon one to the side and i+1 down
                raceInfoMenu.setItem(1 + FULL_ROW_SIZE + (FULL_ROW_SIZE*i), race.traits.get(i).GetTraitIcon());
            }

            // go through all of the subraces and save their icons to subraces
            List<ItemStack> subraceIcons = new ArrayList<>();

            for (Race subrace : race.subraces)
            {
                // save the persistent in the subraceIcon
                ItemStack subraceIcon = subrace.GetRaceIcon();
                ItemMeta subraceIconMeta = subraceIcon.getItemMeta();

                subraceIconMeta.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, subrace.name);
                subraceIcon.setItemMeta(subraceIconMeta);

                // add subraceIcon to subraceIcons
                subraceIcons.add(subraceIcon);
            }

            raceInfoMenu = GenerateIconPositions(subraceIcons, raceInfoMenu, 3, 2);

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
            raceInfoMenu = GenerateIconPositions(icons, raceInfoMenu, 7, 2);
        }

        // return the menu
        return raceInfoMenu;
    }

    public Inventory CreateConfirmMenu(Player player, List<ItemStack> itemsToConfirm, String confirmation, String buttonData, Material confirmBorder)
    {
        // create the menu
        Inventory confirmMenu = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + "Confirm " + confirmation + "?");

        // add the borders
        confirmMenu = AddBorder(confirmBorder, confirmMenu);

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

    public Inventory CreateStatSheetMenu(Player player)
    {
        // create the stat sheet
        Inventory statSheet = Bukkit.createInventory(player, 45,
                ChatColor.BOLD.toString() + "Stat Sheet");

        // add the borders
        statSheet = AddBorder(Material.LIME_STAINED_GLASS_PANE, statSheet);

        /// extract all of the icons from the stat sheet
        // if the player has a race persistent
        if (player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
        {
            // Find the parent race script
            Race raceOfParent = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING));

            // if there isn't a parent race then end the function and throw an error
            if (raceOfParent == null)
            {
                Bukkit.getLogger().info(ChatColor.RED + "ERROR: invalid parent race");
                return null;
            }

            // if the player has a subrace persistent
            if (player.getPersistentDataContainer().has(main.GetSubraceKey(), PersistentDataType.STRING))
            {
                // Find the subrace script
                Race raceOfSubrace = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(main.GetSubraceKey(), PersistentDataType.STRING));

                // if there is a subrace
                if (raceOfSubrace != null)
                {
                    // Get the subrace icon
                    ItemStack subraceIcon = raceOfSubrace.GetRaceIcon();

                    ItemMeta subraceIconMeta = subraceIcon.getItemMeta();
                    subraceIconMeta.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, player.getPersistentDataContainer().get(main.GetSubraceKey(), PersistentDataType.STRING));

                    subraceIcon.setItemMeta(subraceIconMeta);

                    // place the subrace icon one to the side and three down
                    statSheet.setItem(1+(FULL_ROW_SIZE*3), subraceIcon);
                }
                // if there isn't
                else
                {
                    // place a none icon one to the side and three down
                    statSheet.setItem(1+(FULL_ROW_SIZE*3), nullIcon);
                }
            }

            // Get the race icon
            ItemStack raceIcon = raceOfParent.GetRaceIcon();

            ItemMeta raceIconMeta = raceIcon.getItemMeta();
            raceIconMeta.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, player.getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING));

            raceIcon.setItemMeta(raceIconMeta);

            // place the race icon one to the side and one down
            statSheet.setItem(1+FULL_ROW_SIZE, raceIcon);
        }

        // if the player has a class persistent
        if (player.getPersistentDataContainer().has(main.GetClassKey(), PersistentDataType.STRING))
        {
            // Find the class script
            PlayableClass playableClass = main.statSheetManager.FindClass(player.getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING));

            // if there isn't a class then end the function and throw an error
            if (playableClass == null)
            {
                Bukkit.getLogger().info(ChatColor.RED + "ERROR: invalid class");
                return null;
            }

            // Get the class icon
            ItemStack classIcon = playableClass.GetClassIcon();

            ItemMeta classIconMeta = classIcon.getItemMeta();
            List<String> lore = classIconMeta.getLore();
            lore.add("Level: " + player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER));

            classIconMeta.setLore(lore);
            classIconMeta.getPersistentDataContainer().set(main.GetClassKey(), PersistentDataType.STRING, player.getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING));

            classIcon.setItemMeta(classIconMeta);

            // place the class icon four to the side and one down
            statSheet.setItem(4+(FULL_ROW_SIZE), classIcon);
        }

        // add the stat menu icon seven to the side and one down
        statSheet.setItem(7+(FULL_ROW_SIZE), statMenuIcon);

        // return the menu
        return statSheet;
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
