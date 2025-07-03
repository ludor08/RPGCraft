package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class StatSheet
{
    private UUID playerUUID;
    private Main main;

    public Player GetPlayer()
    {
        return Bukkit.getPlayer(playerUUID);
    }

    public StatSheet(UUID playerUUID, Main main)
    {
        this.playerUUID = playerUUID;
        this.main = main;
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
                    if (traitName.equals(node.trait.name))
                    {
                        traits.add(node.trait);
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
}
