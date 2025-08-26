package org.rpg.rPGCraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassXPTab implements TabCompleter
{
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args)
    {
        if (args.length == 1)
        {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("get", "set", "add"), new ArrayList<>());
        }

        return List.of();
    }
}
