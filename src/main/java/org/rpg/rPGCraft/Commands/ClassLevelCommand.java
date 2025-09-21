package org.rpg.rPGCraft.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.Main;

public class ClassLevelCommand implements CommandExecutor
{
    Main main;

    public ClassLevelCommand(Main main)
    {
        this.main = main;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args)
    {
        if (commandSender instanceof Player player)
        {
            if (player.isOp())
            {
                if (args.length == 0)
                {
                    player.sendMessage(ChatColor.DARK_RED + "ERROR : No arguments given. Please give a argument");
                    return false;
                }

                switch (args[0])
                {
                    case "get":
                        player.sendMessage(player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER).toString());
                        break;

                    case "set":
                        if (args.length > 1)
                        {
                            try
                            {
                                // if the player would have too much xp when the level is changed
                                if (player.getPersistentDataContainer().get(main.GetClassXPKey(), PersistentDataType.INTEGER) > main.statSheetManager.GetLevelXPRequirements(Integer.parseInt(args[1])))
                                {
                                    // set the players xp to 1 less than the max xp for that level
                                    player.getPersistentDataContainer().set(main.GetClassXPKey(), PersistentDataType.INTEGER, main.statSheetManager.GetLevelXPRequirements(Integer.parseInt(args[1]))-1);
                                }

                                player.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, Integer.parseInt(args[1]));

                                player.sendMessage(ChatColor.GREEN + "class level set to " + Integer.parseInt(args[1]));
                            }
                            catch (NumberFormatException e)
                            {
                                player.sendMessage(ChatColor.DARK_RED + "ERROR : " + args[1] + " is not a valid integer.");
                            }
                            catch (IndexOutOfBoundsException e)
                            {
                                player.sendMessage(ChatColor.DARK_RED + "ERROR : " + args[1] + " is not a valid level.");
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
                                // if the player would have too much xp when the level is changed
                                if (player.getPersistentDataContainer().get(main.GetClassXPKey(), PersistentDataType.INTEGER) > main.statSheetManager.GetLevelXPRequirements(Integer.parseInt(args[1]) + player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER)))
                                {
                                    // set the players xp to 1 less than the max xp for that level
                                    player.getPersistentDataContainer().set(main.GetClassXPKey(), PersistentDataType.INTEGER, main.statSheetManager.GetLevelXPRequirements(Integer.parseInt(args[1]) + player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER)-1));
                                }

                                player.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER,  player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + Integer.parseInt(args[1]));

                                player.sendMessage(ChatColor.GREEN.toString() + args[1] + " added to your level. your total class level is now " + player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER));
                            }
                            catch (NumberFormatException e)
                            {
                                player.sendMessage(ChatColor.DARK_RED + "ERROR : " + args[1] + " is not a valid integer.");
                            }
                            catch (IndexOutOfBoundsException e)
                            {
                                player.sendMessage(ChatColor.DARK_RED + "ERROR : " + args[1] + " is not a valid level.");
                            }
                        }
                        else
                        {
                            player.sendMessage(ChatColor.DARK_RED + "ERROR : No second argument given. Please give a second argument.");
                        }
                        break;
                }
            }
            else
            {
                player.sendMessage(net.md_5.bungee.api.ChatColor.DARK_RED + "You do not have permission to use this command.");
            }
        }

        return false;
    }

}
