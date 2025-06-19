package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class GameManager implements Listener {

    Main main;

    public GameManager(Main main)
    {
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this,main);
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        // TODO remove this
        player.sendMessage(String.valueOf(!player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING)));

        player.sendMessage(String.valueOf(main.statSheetManager.FindStatSheetByPlayer(player) != null));

        if (main.statSheetManager.FindStatSheetByPlayer(player) != null)
        {
            main.statSheetManager.FindStatSheetByPlayer(player).ResetRacePersistent();
        }

        // if there is no stat sheet assigned to a player when they join
        if (main.statSheetManager.FindStatSheetByPlayer(player) == null)
        {
            player.sendMessage("added stat sheet");
            main.statSheetManager.AddStatSheet(new StatSheet(player.getUniqueId(), main));
        }

        // if the player has not yet chosen a race when they join the game, give them a prompt to choose a race
        if (!player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
        {
            player.openInventory(main.menuManager.CreateRaceMenu(player, main.GetChooseAbleRaces(), 1, "Select a Race!"));
        }

    }
}
