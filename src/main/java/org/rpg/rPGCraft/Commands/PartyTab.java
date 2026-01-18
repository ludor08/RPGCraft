package org.rpg.rPGCraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rpg.rPGCraft.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartyTab implements TabCompleter
{
    Main main;

    public PartyTab()
    {
        main = Main.GetInstance();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args)
    {
        if (!(commandSender instanceof Player))
        {
            return List.of();
        }

        Player player = (Player)commandSender;

        switch (args.length)
        {
            case 1:
                return StringUtil.copyPartialMatches(args[0], Arrays.asList("create", "invite", "accept", "leave", "permissions"), new ArrayList<>());

            case 2:
                if (args[0].equals("invite"))
                {
                    List<String> playerNames = new ArrayList<>();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) playerNames.add(onlinePlayer.getName());

                    return StringUtil.copyPartialMatches(args[1], playerNames, new ArrayList<>());
                }
                else if (args[0].equals("leave"))
                {
                    return StringUtil.copyPartialMatches(args[1], main.partyManager.GetPartiesWithPlayer(player), new ArrayList<>());
                }
                else if (args[0].equals("permissions"))
                {
                    List<String> playerNames = new ArrayList<>();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                    {
                        if (main.partyManager.IsInTheSameParty(player,onlinePlayer)) playerNames.add(onlinePlayer.getName());
                    }

                    return StringUtil.copyPartialMatches(args[1], playerNames, new ArrayList<>());
                }
                break;

            case 3:
                if (args[0].equals("invite"))
                {
                    return StringUtil.copyPartialMatches(args[2], main.partyManager.GetPartiesWithPlayer(player), new ArrayList<>());
                }
                else if (args[0].equals("permissions"))
                {
                    return StringUtil.copyPartialMatches(args[2], main.partyManager.GetPartiesWithPlayer(player), new ArrayList<>());
                }
                break;

            case 4:
                if (args[0].equals("permissions"))
                {
                    return StringUtil.copyPartialMatches(args[3], Arrays.asList("0","1","2"), new ArrayList<>());
                }
                break;

        }

        return List.of();
    }
}
