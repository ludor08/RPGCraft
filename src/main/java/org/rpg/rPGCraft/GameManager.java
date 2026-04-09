package org.rpg.rPGCraft;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Commands.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameManager implements Listener {

    private Main main;

    public GameManager()
    {
        this.main = Main.GetInstance();
        Bukkit.getPluginManager().registerEvents(this,main);

        // commands
        main.getCommand("statsheet").setExecutor(new StatSheetCommand());

        main.getCommand("classxp").setExecutor(new ClassXPCommand());
        main.getCommand("classxp").setTabCompleter(new ClassXPTab());

        main.getCommand("classlevel").setExecutor(new ClassLevelCommand());
        main.getCommand("classlevel").setTabCompleter(new ClassLevelTab());

        main.getCommand("reset").setExecutor(new ResetCommand());
        main.getCommand("reset").setTabCompleter(new ResetTab());

        main.getCommand("party").setExecutor(new PartyCommand());
        main.getCommand("party").setTabCompleter(new PartyTab());

        main.getCommand("rpggive").setExecutor(new RPGGiveCommand());
        main.getCommand("rpggive").setTabCompleter(new RPGGiveTab());

        main.getCommand("rpgspawn").setExecutor(new RPGSpawnCommand());
        main.getCommand("rpgspawn").setTabCompleter(new RPGSpawnTab());

        main.getCommand("test_add_structure").setExecutor(new RPGTestStructureCommand());

        // set up the OnTick scheduler
        //AtomicInteger tick = new AtomicInteger();

        Bukkit.getScheduler().runTaskTimer(main, () -> {
            RefreshActionBar();
            Main.GetInstance().worldManager.OnTick();
            }, 1, 0);
    }

    private void RefreshActionBar()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            // show the players input sequence and mana
            String inputSequence = player.getPersistentDataContainer().get(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING);
            int mana = player.getPersistentDataContainer().get(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER);
            int maxMana = player.getPersistentDataContainer().get(NamespaceDefinitions.GetManaMaxKey(), PersistentDataType.INTEGER);

            TextComponent actionBar = new TextComponent(main.statSheetManager.GenerateInputSequenceActionBar(inputSequence, ChatColor.GREEN) + ChatColor.GRAY + "    |    " +
                    main.statSheetManager.GenerateManaActionBar(mana, maxMana));

            player.sendMessage(ChatMessageType.ACTION_BAR, actionBar);
        }
    }

    // only for testing purposes. should not be used if not testing
    private void Test(int tick)
    {

    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        // if there is no stat sheet assigned to a player when they join
        if (main.statSheetManager.FindStatSheetByPlayer(player) == null)
        {
            main.statSheetManager.AddStatSheet(new StatSheet(player.getUniqueId()));
        }

        // Check the persistents and add them if needed
        CheckPersistent(player);

        // send the resource pack
        ///player.setResourcePack("https://limewire.com/d/djpm2#KHAZePzhCt");

    }

    private void CheckPersistent(Player player)
    {
        // if the player has not yet chosen a race when they join the game, give them a prompt to choose a race
        if (!player.getPersistentDataContainer().has(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING))
        {
            //player.openInventory(main.menuManager.CreateRaceMenu(player, main.GetChooseAbleRaces(), 1, "Select a Race!"));
            Bukkit.getLogger().warning("Race persistent not being checked.");
        }

        // if the player has not yet chosen a class but has chosen a race when they join the game, give them a prompt to choose a class
        else if (!player.getPersistentDataContainer().has(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING))
        {
            //player.openInventory(main.menuManager.CreateClassMenu(player, main.GetChooseAbleClasses()));
            Bukkit.getLogger().warning("Class persistent not being checked.");
        }

        // if the player doesn't have a level persistent
        if (!player.getPersistentDataContainer().has(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER))
        {
            // give them one
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER, 1);
        }

        // if the player doesn't have a level persistent
        if (!player.getPersistentDataContainer().has(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER))
        {
            // give them one
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER, 0);
        }

        // if the player doesn't have a tree progression persistent
        if (!player.getPersistentDataContainer().has(NamespaceDefinitions.GetTreeProgressionKey(), PersistentDataType.STRING))
        {
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetTreeProgressionKey(), PersistentDataType.STRING, "");
        }

        // if the player doesn't have a tree progression persistent
        if (!player.getPersistentDataContainer().has(NamespaceDefinitions.GetDeactivatedNodesKey(), PersistentDataType.STRING))
        {
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetDeactivatedNodesKey(), PersistentDataType.STRING, "");
        }

        // if the player doesn't have an active trait input persistent
        if (!player.getPersistentDataContainer().has(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING))
        {
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING, "");
        }

        // if the player doesn't have a mana persistent
        if (!player.getPersistentDataContainer().has(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER))
        {
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER, 100);
        }

        // if the player doesn't have a mana recharge speed persistent
        if (!player.getPersistentDataContainer().has(NamespaceDefinitions.GetManaRechargeSpeedKey(), PersistentDataType.INTEGER))
        {
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetManaRechargeSpeedKey(), PersistentDataType.INTEGER, 1);
        }

        // if the player doesn't have a max mana persistent
        if (!player.getPersistentDataContainer().has(NamespaceDefinitions.GetManaMaxKey(), PersistentDataType.INTEGER))
        {
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetManaMaxKey(), PersistentDataType.INTEGER, 100);
        }
    }

    @EventHandler
    public void OnBrewEvent(BrewEvent e)
    {
        List<ItemStack> results = e.getResults();
        for (ItemStack result : results)
        {
            // if there is a result
            if (result.getType() != Material.AIR)
            {
                ItemMeta resultMeta = result.getItemMeta();
                resultMeta.getPersistentDataContainer().set(new NamespacedKey(main, "wasJustBrewed"), PersistentDataType.BOOLEAN, true);

                result.setItemMeta(resultMeta);
            }
        }
    }
}
