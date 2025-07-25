package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class StatSheet
{
    private UUID playerUUID;
    private Main main;

    public BukkitTask manaTimer;

    public Player GetPlayer()
    {
        return Bukkit.getPlayer(playerUUID);
    }

    public StatSheet(UUID playerUUID, Main main)
    {
        this.playerUUID = playerUUID;
        this.main = main;

        // Check the mana
        manaTimer = Bukkit.getScheduler().runTaskTimer(main, () ->
        {
            Player player = GetPlayer();

            // if there's not a player
            if (Bukkit.getOnlinePlayers().contains(player))
            {
                // if the player has less than their max mana
                if (player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER) < player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER))
                {
                    player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER)+1);
                }
            }
        }, 20, 20);
    }

    public void AddTraits(Race race)
    {
        // Get the Traits that come from the race
        List<Trait> traits = race.traits;
        // Add the on add effects to the player
        for (Trait trait : traits)
        {
            trait.OnGainTraitBuff(Bukkit.getPlayer(playerUUID));
        }
    }

    public List<Trait> GetTraits()
    {
        // make a list for all of the traits
        List<Trait> traits = new ArrayList<>();

        // the player
        Player player = Bukkit.getPlayer(playerUUID);

        // if the player has a parent race
        if (player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
        {
            // Find the parent race script
            Race raceOfParent = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING));

            // if there is a parent race
            if (raceOfParent != null)
            {
                // add all of the traits
                traits.addAll(raceOfParent.traits);

                // if the player has a subrace
                if (player.getPersistentDataContainer().has(main.GetSubraceKey(), PersistentDataType.STRING))
                {
                    // Find the parent race script
                    Race raceOfSubrace = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(main.GetSubraceKey(), PersistentDataType.STRING));

                    // if there is a parent race
                    if (raceOfSubrace != null)
                    {
                        // add all of the traits
                        traits.addAll(raceOfSubrace.traits);
                    }
                }
            }
            else
            {
                System.out.println("ERROR: invalid parent race");
            }
        }

        // if the player has a class
        if (player.getPersistentDataContainer().has(main.GetClassKey(), PersistentDataType.STRING))
        {
            // get the traits from said nodes
            for (String traitName : Arrays.stream(Bukkit.getPlayer(playerUUID).getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING).split("_")).toList())
            {
                for (Node node : main.statSheetManager.FindClass(player.getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING)).traitTree.nodes)
                {
                    for (Trait trait : node.traits)
                    {
                        if (traitName.equals(trait.name))
                        {
                            traits.add(trait);
                        }
                    }
                }
            }
        }

        return traits;
    }

    public void RemoveTraits(Race race)
    {
        // Get the Traits that come from the race
        List<Trait> traits = race.traits;
        // Add the on add effects to the player
        for (Trait trait : traits)
        {
            trait.OnRemoveTraitBuff(Bukkit.getPlayer(playerUUID));
        }
    }

    public void RemoveTraits(PlayableClass playableClass)
    {
        // get all of the players selected nodes
        List<Trait> selectedNodes = new ArrayList<>();

        // get the traits from said nodes
        for (String traitName : Arrays.stream(Bukkit.getPlayer(playerUUID).getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING).split("_")).toList())
        {
            for (Node node : playableClass.traitTree.nodes)
            {
                for (Trait trait : node.traits)
                {
                    if (traitName.equals(trait.name_id))
                    {
                        selectedNodes.add(trait);
                    }
                }
            }
        }

        // remove the traits from the player
        for (Trait removeTrait : selectedNodes)
        {
            removeTrait.OnRemoveTraitBuff(Bukkit.getPlayer(playerUUID));
        }
    }

    public void SetClassPersistent(String playableClass)
    {
        // the player
        Player player = Bukkit.getPlayer(playerUUID);

        // Find the parent race script
        PlayableClass classOfClass = main.statSheetManager.FindClass(playableClass);

        // if there isn't a parent race then end the function and throw an error
        if (classOfClass == null)
        {
            System.out.println(ChatColor.RED.toString() + "ERROR: invalid class. " + classOfClass);
            return;
        }

        // Set the class
        player.getPersistentDataContainer().set(main.GetClassKey(), PersistentDataType.STRING, playableClass);
    }

    public void SetRacePersistent(String subrace, String parentRace)
    {
        // the player
        Player player = Bukkit.getPlayer(playerUUID);

        // Find the parent race script
        Race raceOfParent = main.statSheetManager.FindRace(parentRace);

        // if there isn't a parent race then end the function and throw an error
        if (raceOfParent == null)
        {
            System.out.println(ChatColor.RED.toString() + "ERROR: invalid parent race. " + parentRace);
            return;
        }

        // Set the race
        player.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, parentRace);
        AddTraits(raceOfParent);

        // If there is a subrace
        if (subrace != null) {
            // Find the subrace script
            Race raceOfSubrace = main.statSheetManager.FindRace(subrace);

            // if there isn't a subrace then end the function and throw an error
            if (raceOfSubrace == null) {
                System.out.println(ChatColor.RED.toString() + "ERROR: invalid subrace. " + player.getPersistentDataContainer().get(main.GetSubraceKey(), PersistentDataType.STRING));
                return;
            }

            // Set the subrace
            player.getPersistentDataContainer().set(main.GetSubraceKey(), PersistentDataType.STRING, subrace);
            AddTraits(raceOfSubrace);
        }
    }

    public void ResetRacePersistent()
    {
        // the player
        Player player = Bukkit.getPlayer(playerUUID);

        // if the player has a race persistent
        if (player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
        {
            // Find the parent race script
            Race raceOfParent = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING));

            // if there isn't a parent race then end the function and throw an error
            if (raceOfParent == null) {
                System.out.println(ChatColor.RED.toString() + "ERROR: invalid parent race");
                return;
            }

            // if the player has a subrace persistent
            if (player.getPersistentDataContainer().has(main.GetSubraceKey(), PersistentDataType.STRING))
            {
                // Find the subrace script
                Race raceOfSubrace = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(main.GetSubraceKey(), PersistentDataType.STRING));

                // if there isn't a subrace then end the function and throw an error
                if (raceOfSubrace == null) {
                    System.out.println(ChatColor.RED.toString() + "ERROR: invalid subrace");
                }
                else
                {
                    // Reset the subrace
                    player.getPersistentDataContainer().remove(main.GetSubraceKey());
                    RemoveTraits(raceOfSubrace);
                }
            }

            // Reset the race
            player.getPersistentDataContainer().remove(main.GetRaceKey());
            RemoveTraits(raceOfParent);
        }
    }

    public void ResetClassPersistent()
    {
        // the player
        Player player = Bukkit.getPlayer(playerUUID);

        // if the player has a race persistent
        if (player.getPersistentDataContainer().has(main.GetClassKey(), PersistentDataType.STRING))
        {
            // Find the parent race script
            PlayableClass playableClass = main.statSheetManager.FindClass(player.getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING));

            // if there isn't a parent race then end the function and throw an error
            if (playableClass == null)
            {
                System.out.println(ChatColor.RED.toString() + "ERROR: invalid class");
                return;
            }

            // Reset the race
            player.getPersistentDataContainer().remove(main.GetClassKey());
            RemoveTraits(playableClass);

            player.getPersistentDataContainer().set(main.GetTreeProgressionKey(), PersistentDataType.STRING, "");
        }
    }

    public void GiveXP(int value)
    {
        int levelXpNeeded = main.statSheetManager.GetLevelXPRequirements(Bukkit.getPlayer(playerUUID).getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER));
        int currentXp = Bukkit.getPlayer(playerUUID).getPersistentDataContainer().get(main.GetClassXPKey(), PersistentDataType.INTEGER);

        Player player = Bukkit.getPlayer(playerUUID);

        // check if the players xp is more or equal to the xp needed to level up
        if (currentXp + value >= levelXpNeeded)
        {
            // level up message
            player.sendMessage(ChatColor.GREEN + "You leveled up! " + player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + " -> " + (player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER)+1));

            player.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + 1);
            player.getPersistentDataContainer().set(main.GetClassXPKey(), PersistentDataType.INTEGER, 0);

            // give the overflow xp back
            GiveXP((currentXp + value) - levelXpNeeded);
        }
        // if not
        else
        {
            player.getPersistentDataContainer().set(main.GetClassXPKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetClassXPKey(), PersistentDataType.INTEGER) + value);
        }


    }

    public void SetXP(int value)
    {
        int levelXpNeeded = main.statSheetManager.GetLevelXPRequirements(Bukkit.getPlayer(playerUUID).getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER));

        Player player = Bukkit.getPlayer(playerUUID);

        // check if the players xp is more or equal to the xp needed to level up
        if (value >= levelXpNeeded)
        {
            // level up message
            player.sendMessage(ChatColor.GREEN + "You leveled up! " + player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + " -> " + (player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER)+1));

            player.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + 1);
            player.getPersistentDataContainer().set(main.GetClassXPKey(), PersistentDataType.INTEGER, 0);

            // give the overflow xp back
            GiveXP(value - levelXpNeeded);
        }
        // if not
        else
        {
            player.getPersistentDataContainer().set(main.GetClassXPKey(), PersistentDataType.INTEGER, value);
        }


    }
}
