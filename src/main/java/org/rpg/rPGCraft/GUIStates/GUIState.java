package org.rpg.rPGCraft.GUIStates;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.rpg.rPGCraft.MenuManager;

public abstract class GUIState
{
    private final Player owner;
    private final String name_id;
    private final GUIState lastState;
    private final int size;
    private Inventory inventory;

    public GUIState(Player owner, String name_id, int size, GUIState lastState)
    {
        this.owner = owner;
        this.name_id = name_id;
        this.lastState = lastState;
        this.size = size;
    }

    public Player GetOwner()
    {
        return owner;
    }

    public String GetNameID()
    {
        return name_id;
    }

    public GUIState GetLastState()
    {
        return lastState;
    }

    public int GetInventorySize()
    {
        return size;
    }

    public void OnClick(InventoryClickEvent e)
    {

    }

    public void Back()
    {
        if (lastState != null)
        {
            // play a button click sound
            GetOwner().playSound(GetOwner().getLocation(), Sound.BLOCK_DISPENSER_FAIL, SoundCategory.PLAYERS, 0.5f, 1);

            MenuManager.AssignGUIState(lastState, GetOwner());
        }
        else
        {
            Bukkit.getLogger().warning("Can not go to the last GUI state, because lastState is null.");
        }

    }

    public Inventory InitializeNewInventoryInstance()
    {
        return Bukkit.createInventory(owner, size);
    }

    public void OnClose()
    {

    }

    public Inventory GetInventory()
    {
        return inventory;
    }

    public void Open()
    {
        inventory = InitializeNewInventoryInstance();
        owner.openInventory(inventory);
    }
}
