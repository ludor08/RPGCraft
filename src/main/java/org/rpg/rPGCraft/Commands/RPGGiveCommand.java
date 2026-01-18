package org.rpg.rPGCraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.CustomItemComponents.CustomItem;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;
import org.rpg.rPGCraft.Main;

public class RPGGiveCommand implements CommandExecutor
{
    Main main;

    public RPGGiveCommand()
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

            int amount = 1;

            if (args.length >= 2)
            {
                try
                {
                    if (Integer.parseInt(args[1]) > 0)
                    {
                        amount = Integer.parseInt(args[1]);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.DARK_RED + "ERROR : Can not give less than one.");
                    }

                } catch (NumberFormatException e)
                {
                    player.sendMessage(ChatColor.DARK_RED + "ERROR : Valid integer value not given.");
                }

            }

            if (CustomItemDefinitions.GetCustomItemByID(args[0]) != null)
            {
                player.getInventory().addItem(CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID(args[0]), amount));
            }
            else
            {
                player.sendMessage(ChatColor.DARK_RED + "ERROR : Not a valid custom item id.");
            }
        }
        else
        {
            Bukkit.getLogger().info("Only a player can ran this command.");
        }

        return false;
    }

}
