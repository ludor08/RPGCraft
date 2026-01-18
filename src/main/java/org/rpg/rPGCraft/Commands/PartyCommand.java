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

public class PartyCommand implements CommandExecutor
{
    Main main;

    public PartyCommand()
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
                case "create":
                    if (args.length > 1)
                    {
                        // if the party exists
                        if (main.partyManager.PartyExists(args[1]))
                        {
                            player.sendMessage(ChatColor.DARK_RED + "The party name must be unique.");
                        }
                        else
                        {
                            main.partyManager.CreateParty(player, args[1]);
                            player.sendMessage(ChatColor.GREEN + "You have successfully created the " + args[1] + " party!");
                        }
                    }
                    else
                    {
                        player.sendMessage(ChatColor.DARK_RED + "Please provide a name for this party.");
                    }
                    break;

                case "invite":
                    if (args.length > 1)
                    {
                        if (args.length > 2)
                        {
                            // if the player doesn't exist
                            if (Bukkit.getPlayer(args[1]) == null)
                            {
                                player.sendMessage(ChatColor.DARK_RED + "Please provide a valid player name.");
                                break;
                            }

                            // if the party doesn't exist
                            if (!main.partyManager.PartyExists(args[2]))
                            {
                                player.sendMessage(ChatColor.DARK_RED + "This party does not exist.");
                                break;
                            }

                            // if the player is in this party
                            if (main.partyManager.IsInParty(Bukkit.getPlayer(args[1]), args[2]))
                            {
                                player.sendMessage(ChatColor.DARK_RED + "This player is already in this party.");
                                break;
                            }

                            // if the player has permissions less than 1 (member)
                            if (main.partyManager.GetPermissionsForParty(player, args[2]) < 1)
                            {
                                player.sendMessage(ChatColor.DARK_RED + "You do not have permission to do this. You need to have permission level one (member) or more.");
                                break;
                            }

                            main.partyManager.InvitePlayerToParty(Bukkit.getPlayer(args[1]), args[2]);
                            player.sendMessage(ChatColor.GREEN + "You have successfully invited " + args[1] + " to " + args[2] + "!");
                        }
                        else
                        {
                            player.sendMessage(ChatColor.DARK_RED + "Please provide the name of the party you want to invite this player to.");
                        }
                    }
                    else
                    {
                        player.sendMessage(ChatColor.DARK_RED + "Please provide the name of the player you want to invite.");
                    }
                    break;

                case "accept":
                    if (player.getPersistentDataContainer().get(main.GetLastPartyInviteKey(), PersistentDataType.STRING) != null)
                    {
                        if (main.partyManager.PartyExists(player.getPersistentDataContainer().get(main.GetLastPartyInviteKey(), PersistentDataType.STRING)))
                        {
                            main.partyManager.AddToParty(player, player.getPersistentDataContainer().get(main.GetLastPartyInviteKey(), PersistentDataType.STRING), 0);

                            player.sendMessage(ChatColor.GREEN + "You are now a part of the " + player.getPersistentDataContainer().get(main.GetLastPartyInviteKey(), PersistentDataType.STRING) + " party!");

                            player.getPersistentDataContainer().remove(main.GetLastPartyInviteKey());
                        }
                        else
                        {
                            player.sendMessage(ChatColor.DARK_RED + "Party + " + player.getPersistentDataContainer().get(main.GetLastPartyInviteKey(), PersistentDataType.STRING) + " + does not exist.");
                        }



                    }
                    break;

                case "leave":

                    if (args.length > 1)
                    {
                        // if the party doesn't exist
                        if (!main.partyManager.PartyExists(args[1]))
                        {
                            player.sendMessage(ChatColor.DARK_RED + "This party does not exist.");
                            break;
                        }

                        // if the player is not in this party
                        if (!main.partyManager.IsInParty(player, args[1]))
                        {
                            player.sendMessage(ChatColor.DARK_RED + "You are not in this party.");
                            break;
                        }

                        main.partyManager.RemoveFromParty(player, args[1]);
                        player.sendMessage(ChatColor.GREEN + "You are no longer a part of the " + args[1] + " party.");

                        if (main.partyManager.GetPlayersInParty(args[1]).isEmpty())
                        {
                            main.partyManager.DisbandParty(args[1]);
                        }

                    }
                    else
                    {
                        player.sendMessage(ChatColor.DARK_RED + "Please enter the party name.");
                    }
                    break;

                case "permissions":
                    if (args.length > 1)
                    {
                        if (args.length > 2)
                        {
                            if (args.length > 3)
                            {
                                // if the player doesn't exist
                                if (Bukkit.getPlayer(args[1]) == null)
                                {
                                    player.sendMessage(ChatColor.DARK_RED + "Please provide a valid player name.");
                                    break;
                                }

                                // if the party doesn't exist
                                if (!main.partyManager.PartyExists(args[2]))
                                {
                                    player.sendMessage(ChatColor.DARK_RED + "This party does not exist.");
                                    break;
                                }

                                // if the player is not in this party
                                if (!main.partyManager.IsInParty(player, args[2]))
                                {
                                    player.sendMessage(ChatColor.DARK_RED + "You are not in this party.");
                                    break;
                                }

                                // if the player has permissions less than 2 (owner/moderator)
                                if (main.partyManager.GetPermissionsForParty(player, args[2]) < 2)
                                {
                                    player.sendMessage(ChatColor.DARK_RED + "You do not have permission to do this. You need to have permission level two (owner/moderator) or more.");
                                    break;
                                }

                                try
                                {
                                    main.partyManager.SetPermissionsForParty(Bukkit.getPlayer(args[1]), args[2], Integer.parseInt(args[3]));
                                } catch (NumberFormatException e)
                                {
                                    player.sendMessage(ChatColor.DARK_RED + args[3] + " is not a valid number.");
                                }
                            }
                            else
                            {
                                player.sendMessage(ChatColor.DARK_RED + "Please provide the permission level (0-2).");
                            }
                        }
                        else
                        {
                            player.sendMessage(ChatColor.DARK_RED + "Please provide the party name.");
                        }
                    }
                    else
                    {
                        player.sendMessage(ChatColor.DARK_RED + "Please provide the name of the player you want to set the permissions of.");
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
