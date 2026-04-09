package org.rpg.rPGCraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.CustomItemComponents.CustomItem;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;
import org.rpg.rPGCraft.Definitions.EntityDefinitions;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.Entities.RPGEntity;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

public class RPGSpawnCommand implements CommandExecutor
{
    Main main;

    public RPGSpawnCommand()
    {
        main = Main.GetInstance();
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args)
    {
        if (commandSender instanceof Player player)
        {
            // if there was not a custom mob type given. return
            if (args.length == 0)
            {
                player.sendMessage(ChatColor.DARK_RED + "ERROR : No arguments given. Please give a argument");
                return false;
            }

            Location loc = player.getLocation();
            boolean hasCustomIsLegendarySet = false;
            boolean isLegendary = false;

            // set cords
            if (args.length == 2 || args.length == 3)
            {
                player.sendMessage(ChatColor.DARK_RED + "ERROR : Not valid coordinates.");
                return false;
            }
            else if (args.length >= 4)
            {
                try
                {
                    RPGutils.ConvertTextCoordinateToLocationCoordinate(args[1], player.getX());
                }
                catch (NumberFormatException e)
                {
                    player.sendMessage(ChatColor.DARK_RED + "ERROR : Not a valid X coordinate.");
                    return false;
                }

                try
                {
                    RPGutils.ConvertTextCoordinateToLocationCoordinate(args[2], player.getY());
                }
                catch (NumberFormatException e)
                {
                    player.sendMessage(ChatColor.DARK_RED + "ERROR : Not a valid Y coordinate.");
                    return false;
                }

                try
                {
                    RPGutils.ConvertTextCoordinateToLocationCoordinate(args[3], player.getZ());
                }
                catch (NumberFormatException e)
                {
                    player.sendMessage(ChatColor.DARK_RED + "ERROR : Not a valid Z coordinate.");
                    return false;
                }
            }

            // check if it has the isLegendary set
            if (args.length >= 5)
            {
                if (args[4].equals("true") || args[4].equals("false"))
                {
                    hasCustomIsLegendarySet = true;
                    isLegendary = Boolean.parseBoolean(args[4]);
                    System.out.println(isLegendary);
                }
                else if (!args[4].equals("not_set"))
                {
                    player.sendMessage(ChatColor.DARK_RED + "ERROR : Not a valid value for isLegendary.");
                }
            }

            if (EntityDefinitions.HasDefinitionWithID(args[0]))
            {
                RPGEntity rpgEntity = EntityDefinitions.GetRPGEntityByID(args[0]);
                Entity summonedEntity;

                if (rpgEntity instanceof RPGCustomEntity customEntity)
                {
                    summonedEntity = customEntity.SpawnCustomEntity(loc);
                }
                else
                {
                    summonedEntity = loc.getWorld().spawnEntity(loc, rpgEntity.GetBaseEntityType());
                }

                if (hasCustomIsLegendarySet)
                {
                    if (isLegendary)
                    {
                        EntityManager.MakeEntityLegendary((LivingEntity) summonedEntity);
                        EntityManager.SetEntityLevel(summonedEntity);
                        EntityManager.SetName(summonedEntity, rpgEntity, true);
                    }
                }
            }
            else
            {
                player.sendMessage(ChatColor.DARK_RED + "ERROR : Not a valid custom entity id.");
            }
        }
        else
        {
            Bukkit.getLogger().info("Only a player can ran this command.");
        }

        return false;
    }

}
