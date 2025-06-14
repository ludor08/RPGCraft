package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.rpg.rPGCraft.Races.*;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements Listener
{
    // managers
    public MenuManager menuManager;

    // NamespacedKeys
    private final NamespacedKey raceKey = new NamespacedKey(this, "race");
    private final NamespacedKey subraceKey = new NamespacedKey(this, "subrace");
    private final NamespacedKey UIKey = new NamespacedKey(this, "ui");

    // choose able races
    private final List<Race> chooseAbleRaces = List.of(new Furoid());

    // stat sheets
    private List<StatSheet> statSheets = new ArrayList<>();

    // Getters
    public NamespacedKey GetRaceKey()
    {
        return raceKey;
    }

    public NamespacedKey GetSubraceKey()
    {
        return subraceKey;
    }

    public NamespacedKey GetUIKey()
    {
        return UIKey;
    }

    public List<Race> GetChooseAbleRaces()
    {
        return chooseAbleRaces;
    }

    public List<StatSheet> GetStatSheets()
    {
        return statSheets;
    }

    // Setter/Adders
    public void AddStatSheet(StatSheet statSheet)
    {
        this.statSheets.add(statSheet);
    }

    @Override
    public void onEnable()
    {
        menuManager = new MenuManager(this);
    }
}
