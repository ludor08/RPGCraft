package org.rpg.rPGCraft.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;
import org.rpg.rPGCraft.Definitions.EntityDefinitions;

import java.util.ArrayList;
import java.util.List;

public class RPGSpawnTab implements TabCompleter
{
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args)
    {
        switch (args.length)
        {
            case 1:
                return StringUtil.copyPartialMatches(args[0], EntityDefinitions.GetEntityIdMap().keySet(), new ArrayList<>());

            case 2:
                return StringUtil.copyPartialMatches(args[1], List.of("~ ~ ~"), new ArrayList<>());

            case 3:
                return StringUtil.copyPartialMatches(args[2], List.of("~ ~"), new ArrayList<>());

            case 4:
                return StringUtil.copyPartialMatches(args[3], List.of("~"), new ArrayList<>());

            case 5:
                return StringUtil.copyPartialMatches(args[4], List.of("true", "false", "not_set"), new ArrayList<>());
        }

        return List.of();
    }
}
