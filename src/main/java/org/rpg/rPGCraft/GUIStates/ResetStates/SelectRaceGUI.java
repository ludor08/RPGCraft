package org.rpg.rPGCraft.GUIStates.ResetStates;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.GUIStates.GUIState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectRaceGUI extends GUIState
{
    public SelectRaceGUI(Player owner, GUIState lastState)
    {
        super(owner, "select_race", 45, lastState);
    }

    @Override
    public Inventory InitializeNewInventoryInstance()
    {
        // create the menu
        Inventory raceMenu = Bukkit.createInventory(GetOwner(), GetInventorySize(),
                ChatColor.BOLD.toString() + "Select a Race!");

        // add the borders
        MenuManager.AddBorder(Material.RED_STAINED_GLASS_PANE, raceMenu);

        // extract all of the icons from races
        List<ItemStack> raceIcons = new ArrayList<>();
        for (Race race : Main.GetInstance().GetChooseAbleRaces())
        {
            ItemStack icon = race.GetRaceIcon();
            MenuManager.AddPersistentDataContainerToItemStack(icon, MyNamespaces.RACE.GetNamespacedKey(), race.name);

            raceIcons.add(icon);
        }

        // generate the icon positions and place them
        MenuManager.AddIconInPositions(raceMenu, raceIcons, 7, 1);

        // return the menu
        return raceMenu;
    }

    @Override
    public void OnClick(InventoryClickEvent e)
    {
        // cancelled the event
        e.setCancelled(true);

        // if the player didn't click a border
        if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(MyNamespaces.RACE.GetNamespacedKey(), PersistentDataType.STRING))
        {
            // get the PersistentDataContainer of the icon clicked
            String racePersistent = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(MyNamespaces.RACE.GetNamespacedKey(), PersistentDataType.STRING);

            // find the race clicked
            for (Race race : Main.GetInstance().GetChooseAbleRaces())
            {
                // if the race name is the same as the race of what the player click, open the new subrace menu
                if (Objects.equals(race.name, racePersistent))
                {
                    // play a button click sound
                    GetOwner().playSound(GetOwner().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5f, 1);

                    MenuManager.AssignGUIState(new SelectSubraceGUI(GetOwner(), this, race), GetOwner());
                }
            }

        }

    }
}
