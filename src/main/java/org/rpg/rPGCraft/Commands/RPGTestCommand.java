package org.rpg.rPGCraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.rpg.rPGCraft.Main;

public class RPGTestCommand implements CommandExecutor
{
    Main main;

    public RPGTestCommand()
    {
        main = Main.GetInstance();
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args)
    {
        if (commandSender instanceof Player player)
        {
            ItemStack item = new ItemStack(Material.BEEF);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setFood(new ItemStack(Material.IRON_NUGGET).getItemMeta().getFood());

            // add the custom model data
            itemMeta.setCustomModelData(21);

            // set the item meta
            item.setItemMeta(itemMeta);

            player.give(item);
        }
        else
        {
            Bukkit.getLogger().info("Only a player can ran this command.");
        }

        return false;
    }

}
