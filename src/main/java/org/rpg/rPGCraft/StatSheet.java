package org.rpg.rPGCraft;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StatSheet
{
    private UUID playerUUID;
    private Main main;

    private BukkitTask tickTimer = null;
    private final Runnable tickRunnable;

    public Player GetPlayer()
    {
        return Bukkit.getPlayer(playerUUID);
    }

    public StatSheet(UUID playerUUID, Main main)
    {
        this.playerUUID = playerUUID;
        this.main = main;

        // set up the tick runnable // if this becomes too legging move to StatSheetManger and make work for all player at once
        AtomicInteger tick = new AtomicInteger();
        tickRunnable = () ->
        {
            if (tick.get() >= 10)
            {
                tick.set(0);
            }

            tick.set(tick.get()+1);

            Player player = GetPlayer();

            // if this player has a input sequence reset delay
            if (player.getPersistentDataContainer().has(new NamespacedKey(main, "input_sequence_reset_delay")))
            {
                // if it is more than 0
                if (player.getPersistentDataContainer().get(new NamespacedKey(main, "input_sequence_reset_delay"), PersistentDataType.INTEGER) > 0)
                {
                    // remove 1
                    player.getPersistentDataContainer().set(new NamespacedKey(main, "input_sequence_reset_delay"), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(new NamespacedKey(main, "input_sequence_reset_delay"), PersistentDataType.INTEGER)-1); // TODO make use a tick in the config file

                    // if it is 0
                    if (player.getPersistentDataContainer().get(new NamespacedKey(main, "input_sequence_reset_delay"), PersistentDataType.INTEGER) <= 0)
                    {
                        int mana = player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER);
                        int maxMana = player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER);

                        // reset the input sequence
                        player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.statSheetManager.GenerateInputSequenceActionBar("", ChatColor.GREEN) + ChatColor.GRAY.toString() + "    |    " +
                                main.statSheetManager.GenerateManaActionBar(mana, maxMana)));
                        player.getPersistentDataContainer().set(main.GetActiveTraitInputKey(), PersistentDataType.STRING, "");
                    }
                }
            }

            // if the player has less than their max mana
            int startingMana = player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER);
            int maxMana = player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER);
            int manaRechargeSpeed = player.getPersistentDataContainer().get(main.GetManaRechargeSpeedKey(), PersistentDataType.INTEGER);

            if (tick.get() == 1 && startingMana < maxMana)
            {
                if (startingMana+manaRechargeSpeed < maxMana)
                {
                    player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, startingMana+manaRechargeSpeed);
                }
                else
                {
                    player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, maxMana);
                }
            }

            // check if any of the trait are tick trait
            for (Trait trait : GetTraits())
            {
                if (trait.tickTrait)
                {
                    trait.OnTick(player);
                }
            }
        };

        StartTickTimer();
    }

    public void StartTickTimer()
    {
        tickTimer = Bukkit.getScheduler().runTaskTimer(main, tickRunnable, 2, 2);
    }

    public void StopTickTimer()
    {
        if (!tickTimer.isCancelled())
        {
            tickTimer.cancel();
        }
    }

    public boolean IsTickTimerRunning()
    {
        if (tickTimer == null || tickTimer.isCancelled())
        {
            return false;
        }

        return true;
    }

    public void AddTraits(Race race)
    {
        // Get the Traits that come from the race
        List<Trait> traits = race.traits;
        // Add the on add effects to the player
        for (Trait trait : traits)
        {
            trait.OnGainTraitBuff(GetPlayer());
        }
    }

    public List<Trait> GetTraits()
    {
        // make a list for all of the traits
        List<Trait> traits = new ArrayList<>();

        // the player
        Player player = GetPlayer();

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
            for (String traitName : Arrays.stream(GetPlayer().getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING).split("_")).toList())
            {
                for (Node node : main.statSheetManager.FindClass(player.getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING)).traitTree.nodes)
                {
                    for (Trait trait : node.traits)
                    {
                        if (traitName.equals((trait.name_id+node.id)))
                        {
                            traits.add(trait);
                        }
                    }
                }
            }
        }

        return traits;
    }

    public List<Trait> GetActiveTraits()
    {
        // make a list for all of the traits
        List<Trait> traits = new ArrayList<>();

        // the player
        Player player = GetPlayer();

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

        // get the deactivated
        List<String> deactivatedNodes = Arrays.stream(player.getPersistentDataContainer().get(main.GetDeactivatedNodesKey(), PersistentDataType.STRING).split("_")).toList();

        // if the player has a class
        if (player.getPersistentDataContainer().has(main.GetClassKey(), PersistentDataType.STRING))
        {
            // get the traits from said nodes
            for (String traitName : Arrays.stream(GetPlayer().getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING).split("_")).toList())
            {
                for (Node node : main.statSheetManager.FindClass(player.getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING)).traitTree.nodes)
                {
                    for (Trait trait : node.traits)
                    {
                        if (traitName.equals(trait.name_id+node.id) && !deactivatedNodes.contains(trait.name_id+node.id))
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
            trait.OnRemoveTraitBuff(GetPlayer());
        }
    }

    public void RemoveTraits(PlayableClass playableClass)
    {
        // get all of the players selected nodes
        List<Trait> selectedNodes = new ArrayList<>();

        // get the traits from said nodes
        for (String traitName : Arrays.stream(GetPlayer().getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING).split("_")).toList())
        {
            for (Node node : playableClass.traitTree.nodes)
            {
                for (Trait trait : node.traits)
                {
                    if (traitName.equals(trait.name_id+node.id))
                    {
                        selectedNodes.add(trait);
                    }
                }
            }
        }

        // remove the traits from the player
        for (Trait removeTrait : selectedNodes)
        {
            removeTrait.OnRemoveTraitBuff(GetPlayer());
        }
    }

    public void UpdateInputSequence(Action action)
    {
        Player player = GetPlayer();
        player.getPersistentDataContainer().set(new NamespacedKey(main, "input_sequence_reset_delay"), PersistentDataType.INTEGER, 50); // TODO make use a tick in the config file

        // if the click was a left click
        if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))
        {
            player.getPersistentDataContainer().set(main.GetActiveTraitInputKey(), PersistentDataType.STRING, player.getPersistentDataContainer().get(main.GetActiveTraitInputKey(), PersistentDataType.STRING) + "0");
        }
        // if the click was a right click
        else if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))
        {
            player.getPersistentDataContainer().set(main.GetActiveTraitInputKey(), PersistentDataType.STRING, player.getPersistentDataContainer().get(main.GetActiveTraitInputKey(), PersistentDataType.STRING) + "1");
        }

        // get the players action bar data
        String inputSequence = player.getPersistentDataContainer().get(main.GetActiveTraitInputKey(), PersistentDataType.STRING);
        int mana = player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER);
        int maxMana = player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER);

        // create the action bar
        TextComponent actionBar = new TextComponent(main.statSheetManager.GenerateInputSequenceActionBar(inputSequence, ChatColor.GREEN) + ChatColor.GRAY + "    |    " +
                main.statSheetManager.GenerateManaActionBar(mana, maxMana));

        player.sendMessage(ChatMessageType.ACTION_BAR, actionBar);

        // if the player has less than 3 inputs store return
        if (inputSequence.length() < 3)
        {
            return;
        }

        // go though and check the players traits
        for (Trait trait : GetTraits())
        {
            // check if the trait is an instanceof ActiveTrait
            if (trait instanceof ActiveTrait activeTrait)
            {
                // if the activeTraits input sequence is the same as the input sequence of the player
                if (activeTrait.GetInputSequence().equals(inputSequence))
                {
                    activeTrait.OnInputSequence(player);

                    // reset the input sequence
                    player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.statSheetManager.GenerateInputSequenceActionBar("", ChatColor.GREEN) + ChatColor.GRAY.toString() + "    |    " +
                            main.statSheetManager.GenerateManaActionBar(mana, maxMana)));
                    player.getPersistentDataContainer().set(main.GetActiveTraitInputKey(), PersistentDataType.STRING, "");
                    return;
                }
            }
        }

        // if none of the traits have the same input sequence as the players input sequence
        player.getPersistentDataContainer().set(main.GetActiveTraitInputKey(), PersistentDataType.STRING, "");

    }

    public void SetClassPersistent(String playableClass)
    {
        // the player
        Player player = GetPlayer();

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
        Player player = GetPlayer();

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
        Player player = GetPlayer();

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
        Player player = GetPlayer();

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
        int levelXpNeeded = main.statSheetManager.GetLevelXPRequirements(GetPlayer().getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER));
        int currentXp = GetPlayer().getPersistentDataContainer().get(main.GetClassXPKey(), PersistentDataType.INTEGER);

        Player player = GetPlayer();

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
        int levelXpNeeded = main.statSheetManager.GetLevelXPRequirements(GetPlayer().getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER));

        Player player = GetPlayer();

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

    public int GetAvailableTraitPoints()
    {
        int traitPoints = GetPlayer().getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER);

        List<String> selectedTraits = Arrays.stream(GetPlayer().getPersistentDataContainer().get(main.GetTreeProgressionKey(), PersistentDataType.STRING).split("_")).toList();
        PlayableClass playableClass = main.statSheetManager.FindClass(GetPlayer().getPersistentDataContainer().get(main.GetClassKey(), PersistentDataType.STRING));

        // if the player has a class
        if (playableClass != null)
        {
            // get the traits from said nodes
            for (String traitName : selectedTraits)
            {
                for (Node node : playableClass.traitTree.nodes)
                {
                    for (Trait trait : node.traits)
                    {
                        if (traitName.equals(trait.name_id))
                        {
                            traitPoints -= node.traits.indexOf(trait)+1;
                        }
                    }
                }
            }
        }

        return traitPoints;
    }
}
