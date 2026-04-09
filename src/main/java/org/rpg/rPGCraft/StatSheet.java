package org.rpg.rPGCraft;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;
import org.rpg.rPGCraft.CustomItemComponents.ItemEnhancement;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;
import org.rpg.rPGCraft.Definitions.TraitDefinitions;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Traits.Trait;

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

    public StatSheet(UUID playerUUID)
    {
        this.playerUUID = playerUUID;
        main = Main.GetInstance();

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

            // Update Inventory Traits
            UpdateInventoryTraits();

            // if this player has an input sequence reset delay
            if (player.getPersistentDataContainer().has(new NamespacedKey(main, "input_sequence_reset_delay")))
            {
                HandleInputSequenceResetDelay(player);
            }

            // if the player has less than their max mana
            RechargeMana(player, tick);

            // check if any of the trait are tick trait
            for (Trait trait : GetActiveTraits())
            {
                if (trait.tickTrait)
                {
                    trait.OnTick(player);
                }
            }
        };

        StartTickTimer();
    }

    private void UpdateInventoryTraits()
    {
        if (GetPlayer().getPersistentDataContainer().has(NamespaceDefinitions.GetCurrentTraitsFromCustomItemsKey()))
        {
            if (!GetPlayer().getPersistentDataContainer().get(NamespaceDefinitions.GetCurrentTraitsFromCustomItemsKey(), PersistentDataType.STRING).equals(GenerateTraitString(GetApplicableItemTraits())))
            {
                List<String> traitIDStrings = new ArrayList<>(Arrays.stream(GetPlayer().getPersistentDataContainer().get(NamespaceDefinitions.GetCurrentTraitsFromCustomItemsKey(), PersistentDataType.STRING).split("_")).toList());

                // find the old and new traits
                List<Trait> newTraits = new ArrayList<>();
                List<Trait> oldTraits = new ArrayList<>();

                for (Trait trait : GetApplicableItemTraits())
                {
                    // if the same trait is in both traitIDStrings and GetApplicableItemTraits()
                    if (traitIDStrings.contains(trait.name_id))
                    {
                        // Go though all of traitIDStrings
                        for (int i = 0; i < traitIDStrings.size(); i++)
                        {
                            // if the ID is the same remove it from traitIDStrings and break
                            if (traitIDStrings.get(i).equals(trait.name_id))
                            {
                                traitIDStrings.remove(i);
                                break;
                            }
                        }
                    }
                    // if not. Add trait to newTraits
                    else
                    {
                        newTraits.add(trait);
                    }
                }

                // add the remaining traits to old traits
                for (String traitIDString : traitIDStrings)
                {
                    if (!Objects.equals(traitIDString, ""))
                    {
                        oldTraits.add(TraitDefinitions.GetTraitByID(traitIDString));
                    }
                }

                // handle the newTraits
                for (Trait newTrait : newTraits)
                {
                    if (newTrait != null)
                    {
                        newTrait.OnGainTraitBuff(GetPlayer());
                    }
                }

                // handle the oldTraits
                for (Trait oldTrait : oldTraits)
                {
                    if (oldTrait != null)
                    {
                        oldTrait.OnRemoveTraitBuff(GetPlayer());
                    }
                }
            }

            GetPlayer().getPersistentDataContainer().set(NamespaceDefinitions.GetCurrentTraitsFromCustomItemsKey(), PersistentDataType.STRING, GenerateTraitString(GetApplicableItemTraits()));
        }
        else
        {
            for (Trait newTrait : GetApplicableItemTraits())
            {
                newTrait.OnGainTraitBuff(GetPlayer());
            }

            GetPlayer().getPersistentDataContainer().set(NamespaceDefinitions.GetCurrentTraitsFromCustomItemsKey(), PersistentDataType.STRING, GenerateTraitString(GetApplicableItemTraits()));
        }
    }

    private String GenerateTraitString(List<Trait> traits)
    {
        StringBuilder inventoryTraits = new StringBuilder();

        for (Trait trait : traits)
        {
            inventoryTraits.append("_");
            inventoryTraits.append(trait.name_id);
        }

        return inventoryTraits.toString();
    }

    private void HandleInputSequenceResetDelay(Player player) {
        // if it is more than 0
        if (player.getPersistentDataContainer().get(new NamespacedKey(main, "input_sequence_reset_delay"), PersistentDataType.INTEGER) > 0)
        {
            // remove 1
            player.getPersistentDataContainer().set(new NamespacedKey(main, "input_sequence_reset_delay"), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(new NamespacedKey(main, "input_sequence_reset_delay"), PersistentDataType.INTEGER)-1); // TODO make use a tick in the config file

            // if it is 0
            if (player.getPersistentDataContainer().get(new NamespacedKey(main, "input_sequence_reset_delay"), PersistentDataType.INTEGER) <= 0)
            {
                int mana = player.getPersistentDataContainer().get(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER);
                int maxMana = player.getPersistentDataContainer().get(NamespaceDefinitions.GetManaMaxKey(), PersistentDataType.INTEGER);

                // reset the input sequence
                player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.statSheetManager.GenerateInputSequenceActionBar("", ChatColor.GREEN) + ChatColor.GRAY.toString() + "    |    " +
                        main.statSheetManager.GenerateManaActionBar(mana, maxMana)));
                player.getPersistentDataContainer().set(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING, "");
            }
        }
    }

    private void RechargeMana(Player player, AtomicInteger tick)
    {
        int startingMana = player.getPersistentDataContainer().get(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER);
        int maxMana = player.getPersistentDataContainer().get(NamespaceDefinitions.GetManaMaxKey(), PersistentDataType.INTEGER);
        int manaRechargeSpeed = player.getPersistentDataContainer().get(NamespaceDefinitions.GetManaRechargeSpeedKey(), PersistentDataType.INTEGER);

        // if the player has less than their max mana
        if (tick.get() == 1 && startingMana < maxMana)
        {
            if (startingMana+manaRechargeSpeed < maxMana)
            {
                player.getPersistentDataContainer().set(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER, startingMana+manaRechargeSpeed);
            }
            else
            {
                player.getPersistentDataContainer().set(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER, maxMana);
            }
        }
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
        if (player.getPersistentDataContainer().has(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING))
        {
            // Find the parent race script
            Race raceOfParent = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING));

            // if there is a parent race
            if (raceOfParent != null)
            {
                // add all of the traits
                traits.addAll(raceOfParent.traits);

                // if the player has a subrace
                if (player.getPersistentDataContainer().has(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING))
                {
                    // Find the parent race script
                    Race raceOfSubrace = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING));

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
        if (player.getPersistentDataContainer().has(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING))
        {
            // for all of the nodes in the traitTree
            for (Node node : main.statSheetManager.FindClass(player.getPersistentDataContainer().get(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING)).traitTree.nodes)
            {
                if (node.IsNodeOwned(player) && node.IsNodeEnabled(player))
                {
                    traits.add(node.GetTraits().get(node.GetNodeLevel(player) - 1));
                }
            }
        }

        traits.addAll(GetApplicableItemTraits());

        return traits;
    }

    public List<Trait> GetActiveClassTraits()
    {
        // make a list for all of the traits
        List<Trait> traits = new ArrayList<>();

        // the player
        Player player = GetPlayer();

        // if the player has a class
        if (player.getPersistentDataContainer().has(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING))
        {
            // for all of the nodes in the traitTree
            for (Node node : main.statSheetManager.FindClass(player.getPersistentDataContainer().get(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING)).traitTree.nodes)
            {
                if (node.IsNodeOwned(player) && node.IsNodeEnabled(player))
                {
                    traits.add(node.GetTraits().get(node.GetNodeLevel(player) - 1));
                }
            }
        }

        return traits;
    }

    public List<Trait> GetActiveRaceTraits()
    {
        // make a list for all of the traits
        List<Trait> traits = new ArrayList<>();

        // the player
        Player player = GetPlayer();

        // if the player has a parent race
        if (player.getPersistentDataContainer().has(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING))
        {
            // Find the parent race script
            Race raceOfParent = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING));

            // if there is a parent race
            if (raceOfParent != null)
            {
                // add all of the traits
                traits.addAll(raceOfParent.traits);

                // if the player has a subrace
                if (player.getPersistentDataContainer().has(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING))
                {
                    // Find the parent race script
                    Race raceOfSubrace = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING));

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

        return traits;
    }

    public List<Trait> GetActiveTraits()
    {
        // make a list for all of the traits
        List<Trait> traits = new ArrayList<>();

        // add all of the traits from the race
        traits.addAll(GetActiveRaceTraits());

        // add all of the traits from items
        traits.addAll(GetApplicableItemTraits());

        // add the traits form the class
        traits.addAll(GetActiveClassTraits());

        return traits;
    }

    public List<Trait> GetApplicableItemTraits()
    {
        List<Trait> traits = new ArrayList<>();

        List<Integer> unusedSlots = new ArrayList<>();
        for (int i = 0; i < GetPlayer().getInventory().getSize(); i++) unusedSlots.add(i);

        for (int slotNum : unusedSlots)
        {
            ItemStack itemStack = GetPlayer().getInventory().getItem(slotNum);
            if (itemStack == null || itemStack.getType() == Material.AIR)
            {
                continue;
            }

            String itemID = itemStack.getPersistentDataContainer().get(NamespaceDefinitions.GetCustomItemKey(), PersistentDataType.STRING);
            if (itemID == null)
            {
                continue;
            }

            // Check if the slot is an equipment slot
            EquipmentSlot equipmentSlot = null;

            if (slotNum == GetPlayer().getInventory().getHeldItemSlot())
            {
                equipmentSlot = EquipmentSlot.HAND;
            } else if (slotNum == 36)
            {
                equipmentSlot = EquipmentSlot.FEET;
            } else if (slotNum == 37)
            {
                equipmentSlot = EquipmentSlot.LEGS;
            } else if (slotNum == 38)
            {
                equipmentSlot = EquipmentSlot.CHEST;
            } else if (slotNum == 39)
            {
                equipmentSlot = EquipmentSlot.HEAD;
            } else if (slotNum == 40)
            {
                equipmentSlot = EquipmentSlot.OFF_HAND;
            }

            for (ItemEnhancement itemEnhancement : CustomItemDefinitions.GetCustomItemByID(itemID).getEnchantments())
            {
                if (ItemEnhancement.IsEnchantmentApplicable(itemEnhancement, equipmentSlot))
                {
                    traits.add(itemEnhancement.GetTrait());
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

    public void RemoveClassTraits()
    {
        // remove the traits from the player
        for (Trait removeTrait : GetActiveClassTraits())
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
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING, player.getPersistentDataContainer().get(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING) + "0");
        }
        // if the click was a right click
        else if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))
        {
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING, player.getPersistentDataContainer().get(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING) + "1");
        }

        // get the players action bar data
        String inputSequence = player.getPersistentDataContainer().get(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING);
        int mana = player.getPersistentDataContainer().get(NamespaceDefinitions.GetManaKey(), PersistentDataType.INTEGER);
        int maxMana = player.getPersistentDataContainer().get(NamespaceDefinitions.GetManaMaxKey(), PersistentDataType.INTEGER);

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
        for (Trait trait : GetActiveTraits())
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
                    player.getPersistentDataContainer().set(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING, "");
                    return;
                }
            }
        }

        // if none of the traits have the same input sequence as the players input sequence
        player.getPersistentDataContainer().set(NamespaceDefinitions.GetActiveTraitInputKey(), PersistentDataType.STRING, "");

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
        player.getPersistentDataContainer().set(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING, playableClass);
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
        player.getPersistentDataContainer().set(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING, parentRace);
        AddTraits(raceOfParent);

        // If there is a subrace
        if (subrace != null) {
            // Find the subrace script
            Race raceOfSubrace = main.statSheetManager.FindRace(subrace);

            // if there isn't a subrace then end the function and throw an error
            if (raceOfSubrace == null) {
                System.out.println(ChatColor.RED.toString() + "ERROR: invalid subrace. " + player.getPersistentDataContainer().get(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING));
                return;
            }

            // Set the subrace
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING, subrace);
            AddTraits(raceOfSubrace);
        }
    }

    public void ResetRacePersistent()
    {
        // the player
        Player player = GetPlayer();

        // if the player has a race persistent
        if (player.getPersistentDataContainer().has(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING))
        {
            // Find the parent race script
            Race raceOfParent = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(NamespaceDefinitions.GetRaceKey(), PersistentDataType.STRING));

            // if there isn't a parent race then end the function and throw an error
            if (raceOfParent == null) {
                System.out.println(ChatColor.RED.toString() + "ERROR: invalid parent race");
                return;
            }

            // if the player has a subrace persistent
            if (player.getPersistentDataContainer().has(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING))
            {
                // Find the subrace script
                Race raceOfSubrace = main.statSheetManager.FindRace(player.getPersistentDataContainer().get(NamespaceDefinitions.GetSubraceKey(), PersistentDataType.STRING));

                // if there isn't a subrace then end the function and throw an error
                if (raceOfSubrace == null) {
                    System.out.println(ChatColor.RED.toString() + "ERROR: invalid subrace");
                }
                else
                {
                    // Reset the subrace
                    player.getPersistentDataContainer().remove(NamespaceDefinitions.GetSubraceKey());
                    RemoveTraits(raceOfSubrace);
                }
            }

            // Reset the race
            player.getPersistentDataContainer().remove(NamespaceDefinitions.GetRaceKey());
            RemoveTraits(raceOfParent);
        }
    }

    public void ResetClassPersistent()
    {
        // the player
        Player player = GetPlayer();

        // if the player has a class persistent
        if (player.getPersistentDataContainer().has(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING))
        {
            // Find the class script
            PlayableClass playableClass = main.statSheetManager.FindClass(player.getPersistentDataContainer().get(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING));

            // if there isn't a class then end the function and throw an error
            if (playableClass == null)
            {
                System.out.println(ChatColor.RED.toString() + "ERROR: invalid class");
                return;
            }

            // Reset the class
            RemoveClassTraits();
            player.getPersistentDataContainer().remove(NamespaceDefinitions.GetClassKey());

            player.getPersistentDataContainer().set(NamespaceDefinitions.GetTreeProgressionKey(), PersistentDataType.STRING, "");

            // clear the deactivated nodes
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetDeactivatedNodesKey(), PersistentDataType.STRING, "");
        }
    }

    public void GiveXP(int value)
    {
        int levelXpNeeded = main.statSheetManager.GetLevelXPRequirements(GetPlayer().getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER));
        int currentXp = GetPlayer().getPersistentDataContainer().get(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER);

        Player player = GetPlayer();

        // check if the players xp is more or equal to the xp needed to level up
        if (currentXp + value >= levelXpNeeded)
        {
            // level up message
            player.sendMessage(ChatColor.GREEN + "You leveled up! " + player.getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER) + " -> " + (player.getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER)+1));

            player.getPersistentDataContainer().set(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER) + 1);
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER, 0);

            // give the overflow xp back
            GiveXP((currentXp + value) - levelXpNeeded);
        }
        // if not
        else
        {
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER) + value);
        }


    }

    public void SetXP(int value)
    {
        int levelXpNeeded = main.statSheetManager.GetLevelXPRequirements(GetPlayer().getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER));

        Player player = GetPlayer();

        // check if the players xp is more or equal to the xp needed to level up
        if (value >= levelXpNeeded)
        {
            // level up message
            player.sendMessage(ChatColor.GREEN + "You leveled up! " + player.getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER) + " -> " + (player.getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER)+1));

            player.getPersistentDataContainer().set(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER) + 1);
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER, 0);

            // give the overflow xp back
            GiveXP(value - levelXpNeeded);
        }
        // if not
        else
        {
            player.getPersistentDataContainer().set(NamespaceDefinitions.GetClassXPKey(), PersistentDataType.INTEGER, value);
        }


    }

    public int GetAvailableTraitPoints()
    {
        int traitPoints = GetPlayer().getPersistentDataContainer().get(NamespaceDefinitions.GetLevelKey(), PersistentDataType.INTEGER);

        List<String> selectedTraits = Arrays.stream(GetPlayer().getPersistentDataContainer().get(NamespaceDefinitions.GetTreeProgressionKey(), PersistentDataType.STRING).split("_")).toList();
        PlayableClass playableClass = main.statSheetManager.FindClass(GetPlayer().getPersistentDataContainer().get(NamespaceDefinitions.GetClassKey(), PersistentDataType.STRING));

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
                        if (traitName.equals(trait.name_id+node.id))
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
