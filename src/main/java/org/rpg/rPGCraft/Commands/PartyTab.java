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
                return StringUtil.copyPartialMatches(args[0], Arrays.asList("create", "invite", "accept", "leave", "permissions", "friendlyFire", "shouldShareClassXP"), new ArrayList<>());

            case 2:
                switch (args[0])
                {
                    case "invite":
                        List<String> playerToInvite = new ArrayList<>();
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) playerToInvite.add(onlinePlayer.getName());

                        return StringUtil.copyPartialMatches(args[1], playerToInvite, new ArrayList<>());

                    case "leave", "friendlyFire", "shouldShareClassXP":
                        return StringUtil.copyPartialMatches(args[1], main.partyManager.GetPartiesWithPlayer(player), new ArrayList<>());

                    case "permissions":
                        List<String> playerToSetPermissions = new ArrayList<>();
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                        {
                            if (main.partyManager.IsInTheSameParty(player,onlinePlayer)) playerToSetPermissions.add(onlinePlayer.getName());
                        }

                        return StringUtil.copyPartialMatches(args[1], playerToSetPermissions, new ArrayList<>());
                }
                break;

            case 3:
                switch (args[0])
                {
                    case "invite":
                        return StringUtil.copyPartialMatches(args[2], main.partyManager.GetPartiesWithPlayer(player), new ArrayList<>());

                    case "friendlyFire", "shouldShareClassXP":
                        return StringUtil.copyPartialMatches(args[2], Arrays.asList("true", "false"), new ArrayList<>());

                    case "permissions":
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
