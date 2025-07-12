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
    private final NamespacedKey levelKey = new NamespacedKey(this, "level");
    private final NamespacedKey classXPKey = new NamespacedKey(this, "class_xp");
    private final NamespacedKey treeProgressionKey = new NamespacedKey(this, "tree_progression");

    private final NamespacedKey weaponTypeKey = new NamespacedKey(this, "weapon_type");
    private final NamespacedKey customMobKey = new NamespacedKey(this, "custom_mob");
    private final NamespacedKey levelStatModKey = new NamespacedKey(this, "level_hp_mod");

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

    public NamespacedKey GetLevelKey()
    {
        return levelKey;
    }

    public NamespacedKey GetLevelStatModKey()
    {
        return levelStatModKey;
    }

    public NamespacedKey GetClassXPKey()
    {
        return classXPKey;
    }

    public NamespacedKey GetWeaponTypeKey()
    {
        return weaponTypeKey;
    }

    public NamespacedKey GetCustomMobKey()
    {
        return customMobKey;
    }

    public NamespacedKey GetTreeProgressionKey()
    {
        return treeProgressionKey;
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
