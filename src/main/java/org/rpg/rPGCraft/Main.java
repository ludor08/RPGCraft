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
    public StatSheetManager statSheetManager;
    public GameManager gameManager;

    // NamespacedKeys
    private final NamespacedKey raceKey = new NamespacedKey(this, "race");
    private final NamespacedKey subraceKey = new NamespacedKey(this, "subrace");
    private final NamespacedKey UIKey = new NamespacedKey(this, "ui");

    // choose able races
    private final List<Race> chooseAbleRaces = List.of(new Furoid(this));

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



    @Override
    public void onEnable()
    {
        menuManager = new MenuManager(this);
        statSheetManager = new StatSheetManager(this);
        gameManager = new GameManager(this);


    }
}
