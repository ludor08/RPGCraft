package org.rpg.rPGCraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.Main;

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
                    main.statSheetManager.FindStatSheetByPlayer(player).ResetClassPersistent();

                    // pick a new class
                    player.openInventory(main.menuManager.CreateClassMenu(player, main.GetChooseAbleClasses()));

                    break;

                case "race":
                    main.statSheetManager.FindStatSheetByPlayer(player).ResetRacePersistent();

                    // pick a new race
                    player.openInventory(main.menuManager.CreateRaceMenu(player, main.GetChooseAbleRaces(), 1, "Select a Race!"));
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
