package org.rpg.rPGCraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.GUIStates.ResetStates.SelectClassGUI;
import org.rpg.rPGCraft.GUIStates.ResetStates.SelectRaceGUI;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.MenuManager;

public class ResetCommand implements CommandExecutor
{
    Main main;

    public ResetCommand()
    {
        main = Main.GetInstance();
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args)
    {
        if (commandSender instanceof Player player)
        {
            if (args.length == 0)
            {
                player.sendMessage(ChatColor.DARK_RED + "ERROR : No arguments given. Please give a argument");
                return false;
            }

            switch (args[0])
            {
                case "class":
                    main.statSheetManager.FindStatSheetByPlayer(player).ResetClass();

                    // pick a new class
                    MenuManager.AssignGUIState(new SelectClassGUI(player, null), player);
                    break;

                case "race":
                    main.statSheetManager.FindStatSheetByPlayer(player).ResetRace();

                    // pick a new race
                    MenuManager.AssignGUIState(new SelectRaceGUI(player, null), player);
                    break;
            }
        }
        else
        {
            Bukkit.getLogger().info("Only a player can ran this command.");
        }

        return false;
    }

}
