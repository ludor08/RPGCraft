package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.rpg.rPGCraft.Races.*;

import java.util.List;

public final class Main extends JavaPlugin implements Listener
{
    // managers
    private final MenuManager menuManager = new MenuManager(this);

    // NamespacedKeys
    private final NamespacedKey raceKey = new NamespacedKey(this, "race");
    private final NamespacedKey UIKey = new NamespacedKey(this, "ui");

    // choose able races
    private final List<Race> chooseAbleRaces = List.of(new Furoid());

    // Getters
    public NamespacedKey GetRaceKey()
    {
        return raceKey;
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
        Bukkit.getPluginManager().registerEvents(menuManager,this);
    }
}
