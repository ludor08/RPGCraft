package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.commands.ClassLevelCommand;
import org.rpg.rPGCraft.commands.ClassXPCommand;
import org.rpg.rPGCraft.commands.ClassXPTab;
import org.rpg.rPGCraft.commands.StatSheetCommand;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

public class GameManager implements Listener {

    Main main;
    Random random = new Random();

    float LEGENDARY_MOB_CHANCE = 0.01f;
    int LEGENDARY_MOB_STAT_MULTIPLIER = 4;

    // weapon lists
    private final List<Material> swordTypes = new ArrayList<>();
    private final List<Material> axeTypes = new ArrayList<>();
    private final List<Material> bowTypes = new ArrayList<>();
    private final List<Material> otherTypes = new ArrayList<>();
    private final List<Material> weaponTypes = new ArrayList<>();

    public GameManager(Main main)
    {
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this,main);

        // commands
        main.getCommand("statSheet").setExecutor(new StatSheetCommand(main));

        main.getCommand("classXp").setExecutor(new ClassXPCommand(main));
        main.getCommand("classXp").setTabCompleter(new ClassXPTab());

        main.getCommand("classLevel").setExecutor(new ClassLevelCommand(main));
    private void GenerateWeaponTypeLists()
    {
        // swords
        swordTypes.add(Material.WOODEN_SWORD);
        swordTypes.add(Material.STONE_SWORD);
        swordTypes.add(Material.GOLDEN_SWORD);
        swordTypes.add(Material.IRON_SWORD);
        swordTypes.add(Material.DIAMOND_SWORD);
        swordTypes.add(Material.NETHERITE_SWORD);

        // axes
        axeTypes.add(Material.WOODEN_AXE);
        axeTypes.add(Material.STONE_AXE);
        axeTypes.add(Material.GOLDEN_AXE);
        axeTypes.add(Material.IRON_AXE);
        axeTypes.add(Material.DIAMOND_AXE);
        axeTypes.add(Material.NETHERITE_AXE);

        // bows
        bowTypes.add(Material.CROSSBOW);
        bowTypes.add(Material.BOW);

        // others
        otherTypes.add(Material.TRIDENT);
        otherTypes.add(Material.MACE);

        // all weapons
        weaponTypes.addAll(swordTypes);
        weaponTypes.addAll(axeTypes);
        weaponTypes.addAll(bowTypes);
        weaponTypes.addAll(otherTypes);
    }

    public List<Material> GetSwordTypes()
    {
        return swordTypes;
    }

    public List<Material> GetAxeTypes()
    {
        return axeTypes;
    }

    public List<Material> GetBowTypes()
    {
        return bowTypes;
    }

    public List<Material> GetOtherTypes()
    {
        return otherTypes;
    }

    public List<Material> GetWeaponTypes()
    {
        return weaponTypes;
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        // if there is no stat sheet assigned to a player when they join
        if (main.statSheetManager.FindStatSheetByPlayer(player) == null)
        {
            player.sendMessage("added stat sheet");
            main.statSheetManager.AddStatSheet(new StatSheet(player.getUniqueId(), main));
        }

        // TODO remove all of this
        main.statSheetManager.FindStatSheetByPlayer(player).ResetRacePersistent();
        main.statSheetManager.FindStatSheetByPlayer(player).ResetClassPersistent();

        // if the player has not yet chosen a race when they join the game, give them a prompt to choose a race
        if (!player.getPersistentDataContainer().has(main.GetRaceKey(), PersistentDataType.STRING))
        {
            player.openInventory(main.menuManager.CreateRaceMenu(player, main.GetChooseAbleRaces(), 1, "Select a Race!"));
        }
        // if the player has not yet chosen a class but has chosen a race when they join the game, give them a prompt to choose a class
        else if (!player.getPersistentDataContainer().has(main.GetClassKey(), PersistentDataType.STRING))
        {
            player.openInventory(main.menuManager.CreateClassMenu(player, main.GetChooseAbleClasses()));
        }

        // if the player doesn't have a level persistent
        if (!player.getPersistentDataContainer().has(main.GetLevelKey(), PersistentDataType.INTEGER))
        {
            // give them one
            player.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, 1);
        }

        // if the player doesn't have a level persistent
        if (!player.getPersistentDataContainer().has(main.GetClassXPKey(), PersistentDataType.INTEGER))
        {
            // give them one
            player.getPersistentDataContainer().set(main.GetClassXPKey(), PersistentDataType.INTEGER, 0);
        }

        // if the player doesn't have a tree progression persistent
        if (!player.getPersistentDataContainer().has(main.GetTreeProgressionKey(), PersistentDataType.STRING))
        {
            player.getPersistentDataContainer().set(main.GetTreeProgressionKey(), PersistentDataType.STRING, "");
        }

    }

    @EventHandler
    public void OnEntitySpawn(EntitySpawnEvent e)
    {
        // if the mob is not a custom mob
        if (!e.getEntity().getPersistentDataContainer().has(main.GetCustomMobKey()))
        {
            Entity entity = e.getEntity();

            // if the mob is a MONSTER
            if (entity.getSpawnCategory().equals(SpawnCategory.MONSTER))
            {
                // if the mob does not have a level yet
                if (!entity.getPersistentDataContainer().has(main.GetLevelKey()))
                {
                    LivingEntity livingEntity = (LivingEntity)entity;

                    switch (entity.getType())
                    {
                        case WITHER :
                            entity.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, 200);
                            entity.setCustomName(ChatColor.BLACK + ChatColor.BOLD.toString() + entity.getName() + ChatColor.RED + " [lvl:" + entity.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + "]");
                            break;

                        case ENDER_DRAGON :
                            entity.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, 200);
                            entity.setCustomName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + entity.getName() + ChatColor.RED + " [lvl:" + entity.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + "]");
                            break;

                        default:
                            // generate the level
                            int level = (int) livingEntity.getAttribute(Attribute.MAX_HEALTH).getValue()  / 4;

                            // if this mob should be legendary
                            if (random.nextFloat(0, 1) <= LEGENDARY_MOB_CHANCE)
                            {
                                for (Player player : Bukkit.getOnlinePlayers()) player.sendMessage("done");

                                // give it LEGENDARY_MOB_STAT_MULTIPLIER times the damage
                                livingEntity.getAttribute(Attribute.ATTACK_DAMAGE).addModifier(new AttributeModifier(main.GetLevelStatModKey(), livingEntity.getAttribute(Attribute.ATTACK_DAMAGE).getValue()*LEGENDARY_MOB_STAT_MULTIPLIER-1, AttributeModifier.Operation.ADD_NUMBER));

                                // give it LEGENDARY_MOB_STAT_MULTIPLIER times the hp and LEGENDARY_MOB_STAT_MULTIPLIER times the level
                                livingEntity.getAttribute(Attribute.MAX_HEALTH).addModifier(new AttributeModifier(main.GetLevelStatModKey(), livingEntity.getAttribute(Attribute.MAX_HEALTH).getValue()*LEGENDARY_MOB_STAT_MULTIPLIER-1, AttributeModifier.Operation.ADD_NUMBER));
                                livingEntity.setHealth(livingEntity.getAttribute(Attribute.MAX_HEALTH).getValue());
                                level *= 4;

                                // make it look legendary
                                entity.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, level);
                                entity.setCustomName(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "LEGENDARY " + entity.getName() + " [lvl:" + entity.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + "]");
                            }
                            else
                            {
                                entity.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, level);
                                entity.setCustomName(entity.getName() + " [lvl:" + entity.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + "]");
                            }

                            entity.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, level);

                            break;
                    }
                }
            }
        }
    }
}
