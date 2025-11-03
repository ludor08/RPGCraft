package org.rpg.rPGCraft;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.Container;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatSheetManager implements Listener
{
    Main main;

    NamespacedKey attackInputCanceledKey;

    // stat sheets
    private List<StatSheet> statSheets = new ArrayList<>();

    public int GetLevelXPRequirements(int level)
    {
        // set up the starting xp needed for a level up
        int neededXp = 25;

        for (int i = 1; i < level; i++)
        {
            neededXp = (int) Math.floor(neededXp*1.25);
        }

        return neededXp;
    }

    public StatSheet FindStatSheetByPlayer(Player player)
    {
        for (StatSheet statSheet : statSheets)
        {
            if (statSheet.GetPlayer() == null)
            {
                continue;
            }

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

        attackInputCanceledKey = new NamespacedKey(main, "attack_input_canceled");

        for (Player player : Bukkit.getOnlinePlayers())
        {
            // if there is no stat sheet assigned to a player when they join
            if (FindStatSheetByPlayer(player) == null)
            {
                AddStatSheet(new StatSheet(player.getUniqueId(), main));
            }
        }
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
    public void OnPlayerInteractEvent(PlayerInteractEvent e)
    {
        // if they didn't click with a block, an empty hand, or food
        if (e.getHand() == EquipmentSlot.HAND && !e.isBlockInHand() && e.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getType() != Material.AIR && !main.itemManager.IsVanillaFood(e.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getType())
        && e.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getType() != Material.POTION && e.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getType() != Material.LINGERING_POTION && e.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getType() != Material.SPLASH_POTION)
        {
            // if they interacted with a block
            if (e.getClickedBlock() != null)
            {
                BlockState state = e.getClickedBlock().getState();

                // if they interacted with an interactable block
                if (e.getClickedBlock().getType().isInteractable() || (state instanceof InventoryHolder || state instanceof Barrel || state instanceof Beacon || state instanceof Bed || state instanceof Beehive || state instanceof Bell || state instanceof BrewingStand || state instanceof Campfire
                        || state instanceof Chest || state instanceof ChiseledBookshelf || state instanceof CommandBlock || state instanceof Comparator || state instanceof Container || state instanceof Crafter || state instanceof DaylightDetector || state instanceof Dispenser || state instanceof DoubleChest || state instanceof Dropper
                        || state instanceof EnchantingTable || state instanceof EnderChest || state instanceof Furnace || state instanceof HangingSign || state instanceof Hopper || state instanceof Jigsaw || state instanceof Jukebox || state instanceof Lockable || state instanceof Sign || state instanceof Smoker))
                {
                    return;
                }
            }

            if (e.getAction() == Action.LEFT_CLICK_BLOCK)
            {
                return;
            }

            // update the input sequence with the new action
            FindStatSheetByPlayer(e.getPlayer()).UpdateInputSequence(e.getAction());
        }
    }

    @EventHandler
    public void OnRespawnEvent(PlayerRespawnEvent e)
    {
        // if the player has a stat sheet
        if (FindStatSheetByPlayer(e.getPlayer()) != null)
        {
            for (Trait trait : FindStatSheetByPlayer(e.getPlayer()).GetActiveTraits())
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
    public void OnTakeDamageEvent(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
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
    public void OnDealDamageEvent(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                if (!InputCanceled(player))
                {
                    for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
                    {
                        trait.OnDealDamage(e);
                    }

                    if (player.getInventory().getItem(EquipmentSlot.HAND).getType() != Material.AIR && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
                    {
                        // update the input sequence with the new action
                        FindStatSheetByPlayer(player).UpdateInputSequence(Action.LEFT_CLICK_AIR);

                    }
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

    private boolean InputCanceled(Player player)
    {
        if (player.getPersistentDataContainer().has(attackInputCanceledKey))
        {
            return true;
        }

        return false;
    }

    @EventHandler
    public void OnProjectileHitEvent(ProjectileHitEvent e)
    {
        if (e.getEntity().getShooter() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
                {
                    trait.OnShotProjectileHit(e);
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
    public void OnLaunchProjectileEvent(ProjectileLaunchEvent e)
    {
        if (e.getEntity().getShooter() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
                {
                    trait.OnLaunchProjectile(e);
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
    public void OnInventoryClickEvent(InventoryClickEvent e)
    {
        if (e.getWhoClicked() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                // if the player clicked in an inventory
                if (e.getClickedInventory() != null)
                {
                    NamespacedKey wasJustBrewed = new NamespacedKey(main, "wasJustBrewed");

                    for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
                    {
                        trait.OnInventoryClick(e);
                    }

                    // if the player clicked an item
                    if (e.getClickedInventory().getItem(e.getSlot()) != null)
                    {
                        // if the item is newly brewed
                        if (e.getCurrentItem().getPersistentDataContainer().has(wasJustBrewed))
                        {
                            for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
                            {
                                trait.OnTakePotionFromBrewingStand(e);
                            }

                            ItemMeta newlyBrewedMeta = e.getCurrentItem().getItemMeta();
                            newlyBrewedMeta.getPersistentDataContainer().remove(wasJustBrewed);

                            e.getCurrentItem().setItemMeta(newlyBrewedMeta);
                        }
                    }
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
    public void OnFoodLevelChangeDamageEvent(FoodLevelChangeEvent e)
    {
        if (e.getEntity() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
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
    public void OnSneakEvent(PlayerToggleSneakEvent e)
    {
        Player player = e.getPlayer();

        // if the player has a stat sheet
        if (FindStatSheetByPlayer(player) != null)
        {
            for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
            {
                trait.OnToggleSneak(e);
            }
        }
        // if they do not have one
        else
        {
            // give them one :)
            AddStatSheet(new StatSheet(player.getUniqueId(), main));
        }
    }

    @EventHandler
    public void OnJumpEvent(PlayerJumpEvent e)
    {
        Player player = e.getPlayer();

        // if the player has a stat sheet
        if (FindStatSheetByPlayer(player) != null)
        {
            for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
            {
                trait.OnJump(e);
            }
        }
        // if they do not have one
        else
        {
            // give them one :)
            AddStatSheet(new StatSheet(player.getUniqueId(), main));
        }
    }

    @EventHandler
    public void OnGainXPEvent(PlayerPickupExperienceEvent e)
    {
        Player player = e.getPlayer();

        // if the player has a stat sheet
        if (FindStatSheetByPlayer(player) != null)
        {
            for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
            {
                trait.OnPickUpXP(e);
            }
        }
        // if they do not have one
        else
        {
            // give them one :)
            AddStatSheet(new StatSheet(player.getUniqueId(), main));
        }
    }

    @EventHandler
    public void OnItemConsumeEvent(PlayerItemConsumeEvent e)
    {
        Player player = e.getPlayer();

        // if the player has a stat sheet
        if (FindStatSheetByPlayer(player) != null)
        {
            for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
            {
                trait.OnPlayerItemConsume(e);
            }
        }
        // if they do not have one
        else
        {
            // give them one :)
            AddStatSheet(new StatSheet(player.getUniqueId(), main));
        }
    }

    @EventHandler
    public void OnClickEvent(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();

        // if the player has a stat sheet
        if (FindStatSheetByPlayer(player) != null)
        {
            for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
            {
                trait.OnClick(e);
            }
        }
        // if they do not have one
        else
        {
            // give them one :)
            AddStatSheet(new StatSheet(player.getUniqueId(), main));
        }
    }

    @EventHandler
    public void OnGainEffectEvent(EntityPotionEffectEvent e)
    {
        if (e.getEntity() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
                {
                    trait.OnGainEffect(e);
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
    public void OnEntityTargetEvent(EntityTargetEvent e)
    {
        // if a player is being targeted
        if (e.getTarget() instanceof Player player)
        {
            // if the player has a stat sheet
            if (FindStatSheetByPlayer(player) != null)
            {
                for (Trait trait : FindStatSheetByPlayer(player).GetActiveTraits())
                {
                    trait.OnTargeted(e);
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
            if (e.getEntity().getKiller().getPersistentDataContainer().has(main.GetLevelKey(), PersistentDataType.INTEGER))
            {
                // if the entity wasn't an MISC entity
                if (!e.getEntity().getSpawnCategory().equals(SpawnCategory.MISC))
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
        FindStatSheetByPlayer(e.getPlayer()).StopTickTimer();
    }

    @EventHandler
    public void OnJoinEvent(PlayerJoinEvent e)
    {
        if (!FindStatSheetByPlayer(e.getPlayer()).IsTickTimerRunning())
        {
            FindStatSheetByPlayer(e.getPlayer()).StartTickTimer();
        }
    }

    public String GenerateManaActionBar(int mana, int maxMana)
    {
        return ChatColor.AQUA.toString() + mana + "/" + maxMana;
    }
}
