package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class StatSheet
{
    private Player player;
    private Main main;

    public Player GetPlayer()
    {
        return player;
    }

    public StatSheet(Player player, Main main)
    {
        this.player = player;
        this.main = main;
    }

    public void AddTraits(Race race)
    {
        // Set the race PersistentDataContainer
        // Find the race script
        // Find the Traits that come from the new race
        // Add the on add effects to the player
    }

    public void SetRacePersistent(String subrace, String parentRace)
    {
        // Find the parent race script
        Race raceOfParent = null;

        for (Race race : main.GetChooseAbleRaces())
        {
            // if the race name is the same as the race of the parentRace
            if (Objects.equals(race.name, parentRace))
            {
                raceOfParent = race;
            }
        }

        // if there isn't a parent race then end the function and throw an error
        if (raceOfParent == null)
        {
            Bukkit.getLogger().info(ChatColor.RED.toString() + "ERROR: invalid parent race");
            return;
        }

        // Set the race
        player.getPersistentDataContainer().set(main.GetRaceKey(), PersistentDataType.STRING, parentRace);
        AddTraits(raceOfParent);

        // If there is a subrace
        if (subrace != null) {
            // Find the subrace script
            Race raceOfSubrace = null;

            for (Race race : raceOfParent.subraces) {
                // if the race name is the same as the race of the subrace
                if (Objects.equals(race.name, subrace)) {
                    raceOfSubrace = race;
                }
            }

            // if there isn't a subrace then end the function and throw an error
            if (raceOfParent == null) {
                Bukkit.getLogger().info(ChatColor.RED.toString() + "ERROR: invalid subrace");
                return;
            }

            // Set the subrace
            player.getPersistentDataContainer().set(main.GetSubraceKey(), PersistentDataType.STRING, subrace);
            AddTraits(raceOfSubrace);
        }
    }
}
