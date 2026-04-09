package org.rpg.rPGCraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.Definitions.StructureDefinitions;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Structures.TestStructure;

import java.io.IOException;
import java.util.Random;

public class RPGTestStructureCommand implements CommandExecutor
{
    Main main;

    public RPGTestStructureCommand()
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

            try {
                System.out.println(StructureDefinitions.GetStructureFileByID("test_ritual.nbt"));

                Structure structure = Bukkit.getStructureManager().loadStructure(StructureDefinitions.GetStructureFileByID("test_ritual.nbt"));
                structure.place(player.getLocation(), true, StructureRotation.NONE, Mirror.NONE, 0, 1, Main.GetInstance().GetRandom());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            Bukkit.getLogger().info("Only a player can ran this command.");
        }

        return false;
    }

}
