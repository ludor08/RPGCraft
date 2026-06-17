package org.rpg.rPGCraft;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.rpg.rPGCraft.Classes.Archer;
import org.rpg.rPGCraft.Classes.Berserker;
import org.rpg.rPGCraft.Classes.Rogue;
import org.rpg.rPGCraft.Classes.Sage;
import org.rpg.rPGCraft.Definitions.*;
import org.rpg.rPGCraft.Races.*;

import java.util.List;
import java.util.Random;

public final class Main extends JavaPlugin implements Listener
{
    // random
    private Random random = new Random();;

    // managers
    public MenuManager menuManager;
    public EntityManager entityManager;
    public StatSheetManager statSheetManager;
    public GameManager gameManager;
    public ItemManager itemManager;
    public PartyManager partyManager;
    public RecipeManager recipeManager;
    public WorldManager worldManager;

    // choose able races
    private List<Race> chooseAbleRaces;

    // choose able classes
    private List<PlayableClass> chooseAbleClasses;

    // Getters
    public Random GetRandom()
    {
        return random;
    }

    public List<Race> GetChooseAbleRaces()
    {
        return chooseAbleRaces;
    }

    public List<PlayableClass> GetChooseAbleClasses()
    {
        return chooseAbleClasses;
    }

    private static Main instance;

    @Override
    public void onEnable()
    {
        instance = this;

        // TODO make this a .json file
        chooseAbleRaces = List.of(new Furoid(), new Arthropod(), new Fungoid(), new Crystalloid());
        chooseAbleClasses = List.of(new Berserker(), new Sage(), new Archer(), new Rogue());

        menuManager = new MenuManager();

        statSheetManager = new StatSheetManager();
        statSheetManager.InitializeStatSheets();

        itemManager = new ItemManager();
        partyManager = new PartyManager();
        entityManager = new EntityManager();
        worldManager = new WorldManager();

        // initialize the hash maps
        TraitDefinitions.Initialize();
        CustomItemDefinitions.Initialize();
        EntityDefinitions.Initialize();
        StructureDefinitions.Initialize();
        AnimationDefinitions.Initialize();

        gameManager = new GameManager();
        recipeManager = new RecipeManager();
    }

    public static Main GetInstance()
    {
        return instance;
    }

}
