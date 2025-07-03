package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.commands.StatSheetCommand;

public class GameManager implements Listener {

    Main main;

    public GameManager(Main main)
    {
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this,main);

        // commands
        main.getCommand("statSheet").setExecutor(new StatSheetCommand(main));
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        // if there is no stat sheet assigned to a player when they join
        if (main.statSheetManager.FindStatSheetByPlayer(player) == null)
        {
            player.sendMessage("added stat sheet");
            main.statSheetManager.AddStatSheet(new StatSheet(player.getUniqueId(), main));
        }

        main.statSheetManager.FindStatSheetByPlayer(player).ResetRacePersistent();
        main.statSheetManager.FindStatSheetByPlayer(player).ResetClassPersistent();

        // if the player has not yet chosen a race when they join the game, give them a prompt to choose a race
        if (!player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
        {
            player.openInventory(main.menuManager.CreateRaceMenu(player, main.GetChooseAbleRaces(), 1, "Select a Race!"));
        }
        // if the player has not yet chosen a class but has chosen a race when they join the game, give them a prompt to choose a class
        else if (!player.getPersistentDataContainer().has(main.GetClassKey(), PersistentDataType.STRING))
        {
            player.openInventory(main.menuManager.CreateClassMenu(player, main.GetChooseAbleClasses()));
        }

        // if the player doesn't have a level persistent
        if (!player.getPersistentDataContainer().has(main.GetLevelKey(), PersistentDataType.INTEGER))
        {
            // give them one
            player.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, 1);
        }

        // if the player doesn't have a tree progression persistent
        if (!player.getPersistentDataContainer().has(main.GetTreeProgressionKey(), PersistentDataType.STRING))
        {
            player.getPersistentDataContainer().set(main.GetTreeProgressionKey(), PersistentDataType.STRING, "");
        }

    }
}
