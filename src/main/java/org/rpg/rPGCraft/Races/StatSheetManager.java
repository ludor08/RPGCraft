package org.rpg.rPGCraft.Races;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Race;
import org.rpg.rPGCraft.StatSheet;
import org.rpg.rPGCraft.Trait;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatSheetManager implements Listener
{
    Main main;

    // stat sheets
    private List<StatSheet> statSheets = new ArrayList<>();

    // Getter
    public List<StatSheet> GetStatSheets()
    {
        return statSheets;
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

        return null;
    }

    // Adder
    public void AddStatSheet(StatSheet statSheet)
    {
        this.statSheets.add(statSheet);
    }

    public Race FindParentRace(String parentPersistent)
    {
        for (Race race : main.GetChooseAbleRaces())
        {
            // if the race name is the same as the name of the parent race
            if (Objects.equals(race.name, parentPersistent)) {
                return race;
            }
        }
        return null;
    }

    public Race FindSubrace(String subracePersistent, Race parentRace)
    {
        for (Race race : parentRace.subraces)
        {
            // if the race name is the same as the name of the parent race
            if (Objects.equals(race.name, subracePersistent)) {
                return race;
            }
        }
        return null;
    }

    public StatSheetManager(Main main)
    {
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this,main);
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
    public void OnDealDamage(FoodLevelChangeEvent e)
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

}
