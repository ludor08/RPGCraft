package org.rpg.rPGCraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.Main;

public class StatSheetCommand implements CommandExecutor
{
    Main main;

    public StatSheetCommand(Main main)
    {
        this.main = main;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings)
    {
        // if a player run this command
        if (commandSender instanceof Player player)
        {
            player.openInventory(main.menuManager.CreateStatSheetMenu(player));
        }
        // if not
        else
        {
            Bukkit.getLogger().info("Only a player can ran this command.");
        }

        return false;
    }

}
