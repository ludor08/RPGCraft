package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

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
        // Find the parent race script
        Race raceOfParent = null;

        // the player
        Player player = Bukkit.getPlayer(playerUUID);

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
            if (raceOfSubrace == null) {
                Bukkit.getLogger().info(ChatColor.RED.toString() + "ERROR: invalid subrace");
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

        player.sendMessage("am i real?");

        // if the player has a race persistent
        if (player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
        {

            player.sendMessage("i did a thing");
            // Find the parent race script
            Race raceOfParent = null;

            for (Race race : main.GetChooseAbleRaces()) {
                // if the race name is the same as the race of the parent race
                if (Objects.equals(race.name, player.getPersistentDataContainer().get(main.GetRaceKey(), PersistentDataType.STRING))) {
                    raceOfParent = race;
                }
            }

            // if there isn't a parent race then end the function and throw an error
            if (raceOfParent == null) {
                Bukkit.getLogger().info(ChatColor.RED.toString() + "ERROR: invalid parent race");
                return;
            }

            // if the player has a subrace persistent
            if (player.getPersistentDataContainer().has(main.GetSubraceKey(), PersistentDataType.STRING))
            {
                // Find the subrace script
                Race raceOfSubrace = null;

                for (Race race : raceOfParent.subraces) {
                    // if the race name is the same as the race of the subrace
                    if (Objects.equals(race.name, player.getPersistentDataContainer().get(main.GetSubraceKey(), PersistentDataType.STRING))) {
                        raceOfSubrace = race;
                    }
                }

                // if there isn't a subrace then end the function and throw an error
                if (raceOfSubrace == null) {
                    Bukkit.getLogger().info(ChatColor.RED.toString() + "ERROR: invalid subrace");
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
