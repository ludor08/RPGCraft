package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.structure.Structure;
import org.rpg.rPGCraft.Classes.Archer;
import org.rpg.rPGCraft.Classes.Berserker;
import org.rpg.rPGCraft.Classes.Rogue;
import org.rpg.rPGCraft.Classes.Sage;
import org.rpg.rPGCraft.Definitions.*;
import org.rpg.rPGCraft.Races.*;

import java.io.IOException;
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
        itemManager = new ItemManager();
        partyManager = new PartyManager();
        entityManager = new EntityManager();
        worldManager = new WorldManager();

        // initialize the hash maps
        TraitDefinitions.Initialize();
        CustomItemDefinitions.Initialize();
        EntityDefinitions.Initialize();
        StateDefinitions.Initialize();
        StructureDefinitions.Initialize();

        gameManager = new GameManager();
        recipeManager = new RecipeManager();

        Structure structure = null;
        try {
            structure = Bukkit.getStructureManager().loadStructure(StructureDefinitions.GetStructureFileByID("test_ritual.nbt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        structure.place(new Location(Bukkit.getWorld("world"), 0,0,0), true, StructureRotation.NONE, Mirror.NONE, 0, 1, Main.GetInstance().GetRandom());
    }

    public static Main GetInstance()
    {
        return instance;
    }

}
