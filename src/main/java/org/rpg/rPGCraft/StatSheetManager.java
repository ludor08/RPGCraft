package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatSheetManager implements Listener
{
    Main main;

    // stat sheets
    private List<StatSheet> statSheets = new ArrayList<>();

    int[] levelXPRequirements = {
            50,
            75,
            115,
            175,
            265,
            400,
            600
    };

    public int GetLevelXPRequirements(int level)
    {
        return levelXPRequirements[level-1];
    }

    public StatSheet FindStatSheetByPlayer(Player player)
    {
        for (StatSheet statSheet : statSheets)
        {
            if (statSheet.GetPlayer().equals(player))
            {
                return statSheet;
            }
        }

        // if the player does not have a stat sheet give them one
        return AddStatSheet(new StatSheet(player.getUniqueId(), main));
    }

    // Adder
    public StatSheet AddStatSheet(StatSheet statSheet)
    {
        this.statSheets.add(statSheet);
        return statSheet;
    }

    public Race FindRace(String persistent)
    {
        // go through all of the races
        for (Race race : main.GetChooseAbleRaces())
        {
            // if the race name is the same as the persistent
            if (Objects.equals(race.name, persistent)) {
                return race;
            }

            // go through all of the subraces
            for (Race subrace : race.subraces)
            {
                // if the subrace name is the same as the persistent
                if (Objects.equals(subrace.name, persistent)) {
                    return subrace;
                }
            }
        }

        return null;
    }

    public PlayableClass FindClass(String persistent)
    {
        // go through all of the classes
        for (PlayableClass playableClass : main.GetChooseAbleClasses())
        {
            // if the race name is the same as the persistent
            if (Objects.equals(playableClass.name, persistent)) {
                return playableClass;
            }
        }

        return null;
    }

    public StatSheetManager(Main main)
    {
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this,main);
    }

    public String GenerateInputSequenceActionBar(String inputSequence, ChatColor color)
    {
        StringBuilder actionBar = new StringBuilder();

        for (String part : inputSequence.split(""))
        {
            // if the actionBar is empty
            if (actionBar.toString().isEmpty())
            {
                if (part.equals("0"))
                {
                    actionBar.append("[LEFT]");
                }
                // if the click was a right click
                else if (part.equals("1"))
                {
                    actionBar.append("[RIGHT]");
                }
            }
            // if it's not
            else
            {
                if (part.equals("0"))
                {
                    actionBar.append(" [LEFT]");
                }
                // if the click was a right click
                else if (part.equals("1"))
                {
                    actionBar.append(" [RIGHT]");
                }
            }
        }

        // add the blank inputs
        if (3 - inputSequence.length() > 0)
        {
            actionBar.append(" [___]".repeat(Math.max(0, 3 - inputSequence.length())));
        }

        return color + actionBar.toString();
    }

    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent e)
    {
        // if they clicked with a weapon
        if (e.getHand() == EquipmentSlot.HAND
            && main.gameManager.GetWeaponTypes().contains(e.getPlayer().getInventory().getItem(e.getHand()).getType()))
        {
            // update the input sequence with the new action
            FindStatSheetByPlayer(e.getPlayer()).UpdateInputSequence(e.getAction());
        }
    }

    @EventHandler
    public void OnRespawn(PlayerRespawnEvent e)
    {
        // if the player has a stat sheet
        if (FindStatSheetByPlayer(e.getPlayer()) != null)
        {
            for (Trait trait : FindStatSheetByPlayer(e.getPlayer()).GetTraits())
            {
                trait.OnRespawnBuffs(e);
            }
        }
        // if they do not have one
        else
        {
            // give them one :)
            AddStatSheet(new StatSheet(e.getPlayer().getUniqueId(), main));
        }
    }

    @EventHandler
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                for (Trait trait : FindStatSheetByPlayer(player).GetTraits())
                {
                    trait.OnTakeDamage(e);
                }
            }
            // if they do not have one
            else
            {
                // give them one :)
                AddStatSheet(new StatSheet(player.getUniqueId(), main));
            }
        }
    }

    @EventHandler
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                for (Trait trait : FindStatSheetByPlayer(player).GetTraits())
                {
                    trait.OnDealDamage(e);
                }
            }
            // if they do not have one
            else
            {
                // give them one :)
                AddStatSheet(new StatSheet(player.getUniqueId(), main));
            }
        }
    }

    @EventHandler
    public void OnFoodLevelChangeDamage(FoodLevelChangeEvent e)
    {
        if (e.getEntity() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                for (Trait trait : FindStatSheetByPlayer(player).GetTraits())
                {
                    trait.OnFoodLevelChange(e);
                }
            }
            // if they do not have one
            else
            {
                // give them one :)
                AddStatSheet(new StatSheet(player.getUniqueId(), main));
            }
        }
    }

    @EventHandler
    public void OnPlayerKillEvent(EntityDeathEvent e)
    {
        // if the entity was killed by a player
        if (e.getEntity().getKiller() != null)
        {
            // if the entity has a level
            if (e.getEntity().getPersistentDataContainer().has(main.GetLevelKey(), PersistentDataType.INTEGER))
            {

                // if the entity was a MONSTER
                if (e.getEntity().getSpawnCategory().equals(SpawnCategory.MONSTER))
                {
                    // if the monster is not a custom entity
                    if (!e.getEntity().getPersistentDataContainer().has(main.GetCustomMobKey(), PersistentDataType.STRING))
                    {
                        Entity entity = e.getEntity();

                        Player player = e.getEntity().getKiller();

                        switch (entity.getType())
                        {
                            case WITHER:
                                FindStatSheetByPlayer(player).GiveXP(1400);
                                break;

                            default:
                                FindStatSheetByPlayer(player).GiveXP(e.getDroppedExp());
                                break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void OnLeaveEvent(PlayerQuitEvent e)
    {
        FindStatSheetByPlayer(e.getPlayer()).manaTimer.cancel();
    }

    public String GenerateManaActionBar(int mana, int maxMana)
    {
        return ChatColor.AQUA.toString() + mana + "/" + maxMana;
    }
}
