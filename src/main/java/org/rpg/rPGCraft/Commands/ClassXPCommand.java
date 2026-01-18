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

public class ClassXPCommand implements CommandExecutor
{
    Main main;

    public ClassXPCommand()
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
                case "get":
                    player.sendMessage(player.getPersistentDataContainer().get(main.GetClassXPKey(), PersistentDataType.INTEGER).toString());
                    break;

                case "set":
                    if (args.length > 1)
                    {
                        try
                        {
                            main.statSheetManager.FindStatSheetByPlayer(player).SetXP(Integer.parseInt(args[1]));
                            player.sendMessage(ChatColor.GREEN + "class xp set to " + Integer.parseInt(args[1]));
                        }
                        catch (NumberFormatException e)
                        {
                            player.sendMessage(ChatColor.DARK_RED + "ERROR : " + Integer.parseInt(args[1]) + " is not a valid integer.");
                        }
                    }
                    else
                    {
                        player.sendMessage(ChatColor.DARK_RED + "ERROR : No second argument given. Please give a second argument");
                    }

                    break;

                case "add":
                    if (args.length > 1)
                    {
                        try
                        {
                            main.statSheetManager.FindStatSheetByPlayer(player).GiveXP(Integer.parseInt(args[1]));
                            player.sendMessage(ChatColor.GREEN.toString() + Integer.parseInt(args[1]) + " class xp added. your total class xp is now " + player.getPersistentDataContainer().get(main.GetClassXPKey(), PersistentDataType.INTEGER));
                        }
                        catch (NumberFormatException e)
                        {
                            player.sendMessage(ChatColor.DARK_RED + "ERROR : " + Integer.parseInt(args[1]) + " is not a valid integer. Please give a valid integer");
                        }
                    }
                    else
                    {
                        player.sendMessage(ChatColor.DARK_RED + "ERROR : No second argument given. Please give a second argument");
                    }
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
