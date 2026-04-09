package org.rpg.rPGCraft.GUIStates.StatSheetStates;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.GUIStates.GUIState;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.ArrayList;
import java.util.List;

public class ShownStatsSheetGUI extends GUIState
{
    public ShownStatsSheetGUI(Player owner, GUIState lastState)
    {
        super(owner, "shown_stat_sheet", 45, lastState);
    }

    @Override
    public Inventory InitializeNewInventoryInstance()
    {
        // create the stat menu
        Inventory statMenu = Bukkit.createInventory(GetOwner(), GetInventorySize(),
                ChatColor.BOLD.toString() + "Stats");

        // add the borders
        statMenu = MenuManager.AddBorder(Material.LIME_STAINED_GLASS_PANE, statMenu);

        // add the back button to the bottom left corner
        ItemStack statBackButton = MenuManager.GetBackButton();

        statMenu.setItem(MenuManager.FULL_ROW_SIZE*4, statBackButton);

        // generate the max health icon
        ItemStack maxHealthIcon = MenuManager.InitializeItemStack(Material.POTION, ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Max Health : " + GetOwner().getAttribute(Attribute.MAX_HEALTH).getValue());

        // set the max health icon color
        PotionMeta maxHealthPotion = (PotionMeta) maxHealthIcon.getItemMeta();

        maxHealthPotion.setColor(Color.RED);
        maxHealthIcon.setItemMeta(maxHealthPotion);

        // place the icon one to the side and two down
        statMenu.setItem(MenuManager.FULL_ROW_SIZE*2+1, maxHealthIcon);

        // generate the armor mod icon
        double armorMod = 0;

        for (AttributeModifier modifier : GetOwner().getAttribute(Attribute.ARMOR).getModifiers())
        {
            if (!modifier.getKey().namespace().contains("minecraft"))
            {
                armorMod += modifier.getAmount();
            }
        }

        ItemStack armorModIcon = MenuManager.InitializeItemStack(Material.IRON_CHESTPLATE, ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Armor Mod : " + armorMod);

        // place the icon two to the side and two down
        statMenu.setItem(MenuManager.FULL_ROW_SIZE*2+2, armorModIcon);

        // generate the trait icon
        List<Trait> traits = Main.GetInstance().statSheetManager.FindStatSheetByPlayer(GetOwner()).GetTraits();
        List<String> traitsLore = new ArrayList<>();

        for (Trait trait : traits)
        {
            traitsLore.add("- " + trait.name);
        }

        ItemStack traitIcon = MenuManager.InitializeItemStack(Material.PAPER, ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Traits", traitsLore);
        MenuManager.AddPersistentDataContainerToItemStack(traitIcon, NamespaceDefinitions.GetUIKey(), "traits");

        // place the icon four to the side and two down
        statMenu.setItem(MenuManager.FULL_ROW_SIZE*2+4, traitIcon);

        // generate the speed mod icon
        double speedMod = 0;

        for (AttributeModifier modifier : GetOwner().getAttribute(Attribute.MOVEMENT_SPEED).getModifiers())
        {
            if (!modifier.getKey().namespace().contains("minecraft"))
            {
                speedMod += modifier.getAmount();
            }
        }

        ItemStack speedModIcon = MenuManager.InitializeItemStack(Material.SUGAR, ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "Speed Mod : " + speedMod);

        // place the icon six to the side and two down
        statMenu.setItem(MenuManager.FULL_ROW_SIZE*2+6, speedModIcon);

        // generate the jump mod icon
        double jumpMod = 0;

        for (AttributeModifier modifier : GetOwner().getAttribute(Attribute.JUMP_STRENGTH).getModifiers())
        {
            if (!modifier.getKey().namespace().contains("minecraft"))
            {
                jumpMod += modifier.getAmount();
            }
        }

        ItemStack jumpModIcon = MenuManager.InitializeItemStack(Material.RABBIT_FOOT, ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Jump Mod : " + jumpMod);

        // place the icon seven to the side and two down
        statMenu.setItem(MenuManager.FULL_ROW_SIZE*2+7, jumpModIcon);

        // return the menu
        return statMenu;
    }

    @Override
    public void OnClick(InventoryClickEvent e)
    {
        // cancel the event so the player can't take items
        e.setCancelled(true);

        if (e.getCurrentItem().getPersistentDataContainer().has(NamespaceDefinitions.GetUIKey()))
        {
            if (e.getCurrentItem().getPersistentDataContainer().get(NamespaceDefinitions.GetUIKey(), PersistentDataType.STRING).equals("traits"))
            {
                // play a button click sound
                GetOwner().playSound(GetOwner().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5f, 1);

                MenuManager.AssignGUIState(new TraitMenuGUI(GetOwner(), this), GetOwner());
            }
        }
    }
}
