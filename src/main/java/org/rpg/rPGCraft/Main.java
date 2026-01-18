package org.rpg.rPGCraft;

import com.google.gson.GsonBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.rpg.rPGCraft.Classes.Archer;
import org.rpg.rPGCraft.Classes.Berserker;
import org.rpg.rPGCraft.Classes.Rogue;
import org.rpg.rPGCraft.Classes.Sage;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;
import org.rpg.rPGCraft.Definitions.TraitDefinitions;
import org.rpg.rPGCraft.Races.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class Main extends JavaPlugin implements Listener
{
    // managers
    public MenuManager menuManager;
    public StatSheetManager statSheetManager;
    public GameManager gameManager;
    public ItemManager itemManager;
    public PartyManager partyManager;
    public RecipeManager recipeManager;

    // NamespacedKeys
    private final NamespacedKey currentTraitsFromCustomItemsKey = new NamespacedKey(this, "current_traits_from_custom_items");

    private final NamespacedKey lastPartyInviteKey = new NamespacedKey(this, "last_party_invite");
    private final NamespacedKey raceKey = new NamespacedKey(this, "race");
    private final NamespacedKey subraceKey = new NamespacedKey(this, "subrace");
    private final NamespacedKey classKey = new NamespacedKey(this, "class");
    private final NamespacedKey UIKey = new NamespacedKey(this, "ui");
    private final NamespacedKey traitKey = new NamespacedKey(this, "trait");
    private final NamespacedKey levelKey = new NamespacedKey(this, "level");
    private final NamespacedKey classXPKey = new NamespacedKey(this, "class_xp");
    private final NamespacedKey treeProgressionKey = new NamespacedKey(this, "tree_progression");
    private final NamespacedKey deactivatedNodesKey = new NamespacedKey(this, "deactivated_nodes");
    private final NamespacedKey activeTraitInputKey = new NamespacedKey(this, "active_trait_input");
    private final NamespacedKey manaKey = new NamespacedKey(this, "mana");
    private final NamespacedKey manaRechargeSpeedKey = new NamespacedKey(this, "mana_recharge_speed");
    private final NamespacedKey manaMaxKey = new NamespacedKey(this, "mana_max");

    private final NamespacedKey weaponTypeKey = new NamespacedKey(this, "weapon_type");
    private final NamespacedKey customMobKey = new NamespacedKey(this, "custom_mob");
    private final NamespacedKey customItemKey = new NamespacedKey(this, "custom_item_id");
    private final NamespacedKey customItemAttributeKey = new NamespacedKey(this, "custom_item_attribute");
    private final NamespacedKey levelStatModKey = new NamespacedKey(this, "level_hp_mod");
    private final NamespacedKey legendaryMobKey = new NamespacedKey(this, "legendary_mob");

    // choose able races
    private List<Race> chooseAbleRaces;

    // choose able classes
    private List<PlayableClass> chooseAbleClasses;

    // Getters
    public NamespacedKey GetCurrentTraitsFromCustomItemsKey()
    {
        return currentTraitsFromCustomItemsKey;
    }

    public NamespacedKey GetLastPartyInviteKey()
    {
        return lastPartyInviteKey;
    }

    public NamespacedKey GetRaceKey()
    {
        return raceKey;
    }

    public NamespacedKey GetManaKey()
    {
        return manaKey;
    }

    public NamespacedKey GetManaRechargeSpeedKey()
    {
        return manaRechargeSpeedKey;
    }

    public NamespacedKey GetManaMaxKey()
    {
        return manaMaxKey;
    }

    public NamespacedKey GetActiveTraitInputKey()
    {
        return activeTraitInputKey;
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

    public NamespacedKey GetCustomItemKey()
    {
        return customItemKey;
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

    public NamespacedKey GetItemAttributeKey()
    {
        return customItemAttributeKey;
    }

    public NamespacedKey GetCustomMobKey()
    {
        return customMobKey;
    }

    public NamespacedKey GetTreeProgressionKey()
    {
        return treeProgressionKey;
    }

    public NamespacedKey GetDeactivatedNodesKey()
    {
        return deactivatedNodesKey;
    }

    public NamespacedKey GetLegendaryMobKey()
    {
        return legendaryMobKey;
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

        // initialize the hash maps
        TraitDefinitions.Initialize();
        CustomItemDefinitions.Initialize();

        gameManager = new GameManager();
        recipeManager = new RecipeManager();
    }

    public static Main GetInstance()
    {
        return instance;
    }
}
