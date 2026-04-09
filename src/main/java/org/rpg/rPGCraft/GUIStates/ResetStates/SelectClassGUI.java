package org.rpg.rPGCraft.GUIStates.ResetStates;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.GUIStates.GUIState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectClassGUI extends GUIState
{
    public SelectClassGUI(Player owner, GUIState lastState)
    {
        super(owner, "select_class", 45, lastState);
    }

    @Override
    public Inventory InitializeNewInventoryInstance()
    {
        // create the menu
        Inventory classMenu = Bukkit.createInventory(GetOwner(), GetInventorySize(),
                ChatColor.BOLD.toString() + "Select a Class!");

        // add the borders
        MenuManager.AddBorder(Material.RED_STAINED_GLASS_PANE, classMenu);

        // extract all of the icons from races
        List<ItemStack> classIcons = new ArrayList<>();
        for (PlayableClass playableClass : Main.GetInstance().GetChooseAbleClasses())
        {
            ItemStack icon = playableClass.GetClassIcon();

            // add the PersistentDataContainer to the icon
            ItemMeta iconMeta = icon.getItemMeta();
            iconMeta.getPersistentDataContainer().set(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING, playableClass.name);

            icon.setItemMeta(iconMeta);

            classIcons.add(icon);
        }

        // generate the icon positions and place them
        MenuManager.AddIconInPositions(classMenu, classIcons, 7, 1);

        // return the menu
        return classMenu;
    }

    @Override
    public void OnClick(InventoryClickEvent e)
    {
        // cancelled the event
        e.setCancelled(true);

        // if the player didn't click a border
        if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING))
        {
            // get the PersistentDataContainer of the icon clicked
            String classPersistent = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING);

            // find the race clicked
            for (PlayableClass playableClass : Main.GetInstance().GetChooseAbleClasses())
            {
                // if the race name is the same as the race of what the player click, open the new subrace menu
                if (Objects.equals(playableClass.name, classPersistent))
                {
                    // play a button click sound
                    GetOwner().playSound(GetOwner().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5f, 1);

                    MenuManager.AssignGUIState(new ConfirmClassGUI(GetOwner(), this, playableClass), GetOwner());
                }
            }

        }

    }
}
