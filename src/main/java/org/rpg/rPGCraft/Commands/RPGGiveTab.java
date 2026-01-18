package org.rpg.rPGCraft.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RPGGiveTab implements TabCompleter
{
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args)
    {
        if (args.length == 1)
        {
            return StringUtil.copyPartialMatches(args[0], CustomItemDefinitions.GetCustomItemIdMap().keySet(), new ArrayList<>());
        }

        return List.of();
    }
}
