package org.rpg.rPGCraft;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;
import org.rpg.rPGCraft.CustomItemComponents.ItemEnhancement;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Definitions.TraitDefinitions;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StatSheet
{
    private Player player;

    private BukkitTask tickTimer = null;
    private final Runnable tickRunnable;

    public Player GetPlayer()
    {
        return player;
    }

    public void SetPlayer(Player player)
    {
        this.player = player;
    }

    public Race GetRace()
    {
        return Main.GetInstance().statSheetManager.FindRace(GetPlayer().getPersistentDataContainer().get(MyNamespaces.RACE.GetNamespacedKey(), PersistentDataType.STRING));
    }

    public Race GetSubrace()
    {
        return Main.GetInstance().statSheetManager.FindRace(GetPlayer().getPersistentDataContainer().get(MyNamespaces.SUBRACE.GetNamespacedKey(), PersistentDataType.STRING));
    }

    public PlayableClass GetClass()
    {
        return Main.GetInstance().statSheetManager.FindClass(GetPlayer().getPersistentDataContainer().get(MyNamespaces.CLASS.GetNamespacedKey(), PersistentDataType.STRING));
    }

    public StatSheet(Player player)
    {
        this.player = player;

        // set up the tick runnable // if this becomes too legging move to StatSheetManger and make work for all player at once
        AtomicInteger tick = new AtomicInteger();
        tickRunnable = () ->
        {
            if (tick.get() >= 10)
            {
                tick.set(0);
            }

            tick.set(tick.get()+1);

            // Update Inventory Traits
            UpdateInventoryTraits();

            // if this player has an input sequence reset delay
            if (GetPlayer().getPersistentDataContainer().has(new NamespacedKey(Main.GetInstance(), "input_sequence_reset_delay")))
            {
                HandleInputSequenceResetDelay(GetPlayer());
            }

            // if the player has less than their max mana
            RechargeMana(GetPlayer(), tick);

            // check if any of the trait are tick trait
            for (Trait trait : GetActiveTraits())
            {
                if (trait.tickTrait)
                {
                    trait.OnTick(GetPlayer());
                }
            }
        };

        StartTickTimer();
    }

    private void UpdateInventoryTraits()
    {
        if (GetPlayer().getPersistentDataContainer().has(MyNamespaces.CURRENT_TRAITS_FROM_CUSTOM_ITEMS.GetNamespacedKey()))
        {
            if (!GetPlayer().getPersistentDataContainer().get(MyNamespaces.CURRENT_TRAITS_FROM_CUSTOM_ITEMS.GetNamespacedKey(), PersistentDataType.STRING).equals(GenerateTraitString(GetApplicableItemTraits())))
            {
                List<String> traitIDStrings = new ArrayList<>(Arrays.stream(GetPlayer().getPersistentDataContainer().get(MyNamespaces.CURRENT_TRAITS_FROM_CUSTOM_ITEMS.GetNamespacedKey(), PersistentDataType.STRING).split("_")).toList());

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

            GetPlayer().getPersistentDataContainer().set(MyNamespaces.CURRENT_TRAITS_FROM_CUSTOM_ITEMS.GetNamespacedKey(), PersistentDataType.STRING, GenerateTraitString(GetApplicableItemTraits()));
        }
        else
        {
            for (Trait newTrait : GetApplicableItemTraits())
            {
                newTrait.OnGainTraitBuff(GetPlayer());
            }

            GetPlayer().getPersistentDataContainer().set(MyNamespaces.CURRENT_TRAITS_FROM_CUSTOM_ITEMS.GetNamespacedKey(), PersistentDataType.STRING, GenerateTraitString(GetApplicableItemTraits()));
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
        if (player.getPersistentDataContainer().get(new NamespacedKey(Main.GetInstance(), "input_sequence_reset_delay"), PersistentDataType.INTEGER) > 0)
        {
            // remove 1
            player.getPersistentDataContainer().set(new NamespacedKey(Main.GetInstance(), "input_sequence_reset_delay"), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(new NamespacedKey(Main.GetInstance(), "input_sequence_reset_delay"), PersistentDataType.INTEGER)-1); // TODO make use a tick in the config file

            // if it is 0
            if (player.getPersistentDataContainer().get(new NamespacedKey(Main.GetInstance(), "input_sequence_reset_delay"), PersistentDataType.INTEGER) <= 0)
            {
                int mana = player.getPersistentDataContainer().get(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER);
                int maxMana = player.getPersistentDataContainer().get(MyNamespaces.MANA_MAX.GetNamespacedKey(), PersistentDataType.INTEGER);

                // reset the input sequence
                player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Main.GetInstance().statSheetManager.GenerateInputSequenceActionBar("", ChatColor.GREEN) + ChatColor.GRAY.toString() + "    |    " +
                        Main.GetInstance().statSheetManager.GenerateManaActionBar(mana, maxMana)));
                player.getPersistentDataContainer().set(MyNamespaces.ACTIVE_TRAIT_INPUT.GetNamespacedKey(), PersistentDataType.STRING, "");
            }
        }
    }

    private void RechargeMana(Player player, AtomicInteger tick)
    {
        int startingMana = player.getPersistentDataContainer().get(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER);
        int maxMana = player.getPersistentDataContainer().get(MyNamespaces.MANA_MAX.GetNamespacedKey(), PersistentDataType.INTEGER);
        int manaRechargeSpeed = player.getPersistentDataContainer().get(MyNamespaces.MANA_RECHARGE_SPEED.GetNamespacedKey(), PersistentDataType.INTEGER);

        // if the player has less than their max mana
        if (tick.get() == 1 && startingMana < maxMana)
        {
            if (startingMana+manaRechargeSpeed < maxMana)
            {
                player.getPersistentDataContainer().set(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER, startingMana+manaRechargeSpeed);
            }
            else
            {
                player.getPersistentDataContainer().set(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER, maxMana);
            }
        }
    }

    public void StartTickTimer()
    {
        tickTimer = Bukkit.getScheduler().runTaskTimer(Main.GetInstance(), tickRunnable, 2, 2);
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

    public List<Trait> GetTraits()
    {
        // make a list for all of the traits
        List<Trait> traits = new ArrayList<>();

        // add all of the race traits to the list
        traits.addAll(GetActiveRaceTraits());

        // if the player has a class
        if (GetClass() != null)
        {
            // for all of the nodes in the traitTree
            for (Node node : GetClass().traitTree.nodes)
            {
                if (node.IsNodeOwned(GetPlayer()) && node.IsNodeEnabled(GetPlayer()))
                {
                    traits.add(node.GetTraits().get(node.GetNodeLevel(GetPlayer()) - 1));
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

        // if the player has a class
        if (GetClass() != null)
        {
            // for all of the nodes in the traitTree
            for (Node node : GetClass().traitTree.nodes)
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

        // if there is a parent race
        if (GetRace() != null)
        {
            // add all of the traits
            traits.addAll(GetRace().traits);

            // if the player has a subrace
            if (GetSubrace() != null)
            {
                // add all of the traits
                traits.addAll(GetSubrace().traits);
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

            String itemID = itemStack.getPersistentDataContainer().get(MyNamespaces.CUSTOM_ITEM.GetNamespacedKey(), PersistentDataType.STRING);
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
        player.getPersistentDataContainer().set(new NamespacedKey(Main.GetInstance(), "input_sequence_reset_delay"), PersistentDataType.INTEGER, 50); // TODO make use a tick in the config file

        // if the click was a left click
        if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))
        {
            player.getPersistentDataContainer().set(MyNamespaces.ACTIVE_TRAIT_INPUT.GetNamespacedKey(), PersistentDataType.STRING, player.getPersistentDataContainer().get(MyNamespaces.ACTIVE_TRAIT_INPUT.GetNamespacedKey(), PersistentDataType.STRING) + "0");
        }
        // if the click was a right click
        else if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))
        {
            player.getPersistentDataContainer().set(MyNamespaces.ACTIVE_TRAIT_INPUT.GetNamespacedKey(), PersistentDataType.STRING, player.getPersistentDataContainer().get(MyNamespaces.ACTIVE_TRAIT_INPUT.GetNamespacedKey(), PersistentDataType.STRING) + "1");
        }

        // get the players action bar data
        String inputSequence = player.getPersistentDataContainer().get(MyNamespaces.ACTIVE_TRAIT_INPUT.GetNamespacedKey(), PersistentDataType.STRING);
        int mana = player.getPersistentDataContainer().get(MyNamespaces.MANA.GetNamespacedKey(), PersistentDataType.INTEGER);
        int maxMana = player.getPersistentDataContainer().get(MyNamespaces.MANA_MAX.GetNamespacedKey(), PersistentDataType.INTEGER);

        // create the action bar
        TextComponent actionBar = new TextComponent(Main.GetInstance().statSheetManager.GenerateInputSequenceActionBar(inputSequence, ChatColor.GREEN) + ChatColor.GRAY + "    |    " +
                Main.GetInstance().statSheetManager.GenerateManaActionBar(mana, maxMana));

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
                    player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Main.GetInstance().statSheetManager.GenerateInputSequenceActionBar("", ChatColor.GREEN) + ChatColor.GRAY.toString() + "    |    " +
                            Main.GetInstance().statSheetManager.GenerateManaActionBar(mana, maxMana)));
                    player.getPersistentDataContainer().set(MyNamespaces.ACTIVE_TRAIT_INPUT.GetNamespacedKey(), PersistentDataType.STRING, "");
                    return;
                }
            }
        }

        // if none of the traits have the same input sequence as the players input sequence
        player.getPersistentDataContainer().set(MyNamespaces.ACTIVE_TRAIT_INPUT.GetNamespacedKey(), PersistentDataType.STRING, "");

    }

    public void SetClass(PlayableClass newPlayableClass)
    {
        // if there isn't a parent race then end the function and throw an error
        if (newPlayableClass == null)
        {
            GetPlayer().getPersistentDataContainer().remove(MyNamespaces.CLASS.GetNamespacedKey());
            return;
        }

        // Set the class
        GetPlayer().getPersistentDataContainer().set(MyNamespaces.CLASS.GetNamespacedKey(), PersistentDataType.STRING, newPlayableClass.name);
    }

    public void SetRace(Race newSubrace, Race newRace)
    {
        // the player
        Player player = GetPlayer();

        // if there isn't a parent race then end the function and throw an error
        if (newRace == null)
        {
            GetPlayer().getPersistentDataContainer().remove(MyNamespaces.RACE.GetNamespacedKey());
        }
        else
        {
            // Run the on gain traits for all of the traits in the race
            GetPlayer().getPersistentDataContainer().set(MyNamespaces.RACE.GetNamespacedKey(), PersistentDataType.STRING, newRace.name);

            newRace.GainTraits(player);

            // If there is a subrace
            if (newSubrace != null)
            {
                // Run the on gain traits for all of the traits in the subrace
                GetPlayer().getPersistentDataContainer().set(MyNamespaces.SUBRACE.GetNamespacedKey(), PersistentDataType.STRING, newSubrace.name);

                newSubrace.GainTraits(player);
            }
            else
            {
                GetPlayer().getPersistentDataContainer().remove(MyNamespaces.SUBRACE.GetNamespacedKey());
            }
        }
    }

    public void ResetRace()
    {
        Race subrace = GetSubrace();

        // if the player has a race
        if (GetRace() != null)
        {
            // if the player has a subrace persistent
            if (subrace != null)
            {
                // Reset the subrace
                GetSubrace().LoseTraits(GetPlayer());

                subrace = null;
            }

            // Reset the race
            GetRace().LoseTraits(GetPlayer());

            SetRace(subrace, null);
        }
    }

    public void ResetClass()
    {
        // if the player has a class
        if (GetClass() != null)
        {
            // Reset the class
            RemoveClassTraits();

            SetClass(null);

            player.getPersistentDataContainer().set(MyNamespaces.TREE_PROGRESSION.GetNamespacedKey(), PersistentDataType.STRING, "");

            // clear the deactivated nodes
            player.getPersistentDataContainer().set(MyNamespaces.DEACTIVATED_NODES.GetNamespacedKey(), PersistentDataType.STRING, "");
        }
    }

    public void GiveXP(int value, boolean shouldShare)
    {
        Player player = GetPlayer();

        // if the player should share the xp with party members
        if (shouldShare)
        {
            List<Player> playersToBeSharedWith = Main.GetInstance().partyManager.GetPlayersToShareClassXpWith(player);

            for (Player playerToBeSharedWith : playersToBeSharedWith)
            {
                Main.GetInstance().statSheetManager.FindStatSheetByPlayer(playerToBeSharedWith).GiveXP(value/playersToBeSharedWith.size(), false);
            }
        }
        else
        {
            int levelXpNeeded = Main.GetInstance().statSheetManager.GetLevelXPRequirements(GetPlayer().getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER));
            int currentXp = GetPlayer().getPersistentDataContainer().get(MyNamespaces.CLASS_XP.GetNamespacedKey(), PersistentDataType.INTEGER);

            // check if the players xp is more or equal to the xp needed to level up
            if (currentXp + value >= levelXpNeeded)
            {
                // level up message
                player.sendMessage(ChatColor.GREEN + "You leveled up! " + player.getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER) + " -> " + (player.getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER)+1));

                player.getPersistentDataContainer().set(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER) + 1);
                player.getPersistentDataContainer().set(MyNamespaces.CLASS_XP.GetNamespacedKey(), PersistentDataType.INTEGER, 0);

                // give the overflow xp back
                GiveXP((currentXp + value) - levelXpNeeded, false);
            }
            // if not
            else
            {
                player.getPersistentDataContainer().set(MyNamespaces.CLASS_XP.GetNamespacedKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(MyNamespaces.CLASS_XP.GetNamespacedKey(), PersistentDataType.INTEGER) + value);
            }
        }
    }

    public void SetXP(int value)
    {
        int levelXpNeeded = Main.GetInstance().statSheetManager.GetLevelXPRequirements(GetPlayer().getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER));

        Player player = GetPlayer();

        // check if the players xp is more or equal to the xp needed to level up
        if (value >= levelXpNeeded)
        {
            // level up message
            player.sendMessage(ChatColor.GREEN + "You leveled up! " + player.getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER) + " -> " + (player.getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER)+1));

            player.getPersistentDataContainer().set(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER) + 1);
            player.getPersistentDataContainer().set(MyNamespaces.CLASS_XP.GetNamespacedKey(), PersistentDataType.INTEGER, 0);

            // give the overflow xp back
            GiveXP(value - levelXpNeeded, false);
        }
        // if not
        else
        {
            player.getPersistentDataContainer().set(MyNamespaces.CLASS_XP.GetNamespacedKey(), PersistentDataType.INTEGER, value);
        }


    }

    public int GetAvailableTraitPoints()
    {
        int traitPoints = GetPlayer().getPersistentDataContainer().get(MyNamespaces.LEVEL.GetNamespacedKey(), PersistentDataType.INTEGER);

        List<String> selectedTraits = Arrays.stream(GetPlayer().getPersistentDataContainer().get(MyNamespaces.TREE_PROGRESSION.GetNamespacedKey(), PersistentDataType.STRING).split("_")).toList();

        // if the player has a class
        if (GetClass() != null)
        {
            // get the traits from said nodes
            for (String traitName : selectedTraits)
            {
                for (Node node : GetClass().traitTree.nodes)
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
