package org.rpg.rPGCraft;

import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.rpg.rPGCraft.Classes.Berserker;
import org.rpg.rPGCraft.Races.*;

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
    private final NamespacedKey classKey = new NamespacedKey(this, "class");
    private final NamespacedKey UIKey = new NamespacedKey(this, "ui");
    private final NamespacedKey traitKey = new NamespacedKey(this, "trait");

    // choose able races
    private final List<Race> chooseAbleRaces = List.of(new Furoid(this));

    // choose able classes
    private final List<PlayableClass> chooseAbleClasses = List.of(new Berserker(this));

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

    public NamespacedKey GetClassKey()
    {
        return classKey;
    }

    public NamespacedKey GetTraitKey()
    {
        return traitKey;
    }

    public List<Race> GetChooseAbleRaces()
    {
        return chooseAbleRaces;
    }

    public List<PlayableClass> GetChooseAbleClasses()
    {
        return chooseAbleClasses;
    }



    @Override
    public void onEnable()
    {
        menuManager = new MenuManager(this);
        statSheetManager = new StatSheetManager(this);
        gameManager = new GameManager(this);
    }
}
