package org.rpg.rPGCraft;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Commands.*;
import org.rpg.rPGCraft.CustomItemComponents.CustomItem;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameManager implements Listener {

    private Main main;

    Random random = new Random();

    float LEGENDARY_MOB_CHANCE = 0.01f;
    int LEGENDARY_MOB_STAT_MULTIPLIER = 4;

    public GameManager()
    {
        this.main = Main.GetInstance();
        Bukkit.getPluginManager().registerEvents(this,main);

        // commands
        main.getCommand("statSheet").setExecutor(new StatSheetCommand());

        main.getCommand("classXp").setExecutor(new ClassXPCommand());
        main.getCommand("classXp").setTabCompleter(new ClassXPTab());

        main.getCommand("classLevel").setExecutor(new ClassLevelCommand());
        main.getCommand("classLevel").setTabCompleter(new ClassLevelTab());

        main.getCommand("reset").setExecutor(new ResetCommand());
        main.getCommand("reset").setTabCompleter(new ResetTab());

        main.getCommand("party").setExecutor(new PartyCommand());
        main.getCommand("party").setTabCompleter(new PartyTab());

        main.getCommand("rpggive").setExecutor(new RPGGiveCommand());
        main.getCommand("rpggive").setTabCompleter(new RPGGiveTab());

        // set up the OnTick scheduler
        AtomicInteger tick = new AtomicInteger();

        Bukkit.getScheduler().runTaskTimer(main, () -> {
            if (tick.get() >= 20)
            {
                tick.set(0);
            }

            tick.set(tick.get()+1);

            OnTick(tick.get());
        }, 20, 5);
    }

    public int GetLegendaryMobStatMultiplier()
    {
        return LEGENDARY_MOB_STAT_MULTIPLIER;
    }

    private void OnTick(int tick)
    {
        List<Entity> legendaryEntities = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers())
        {
            // show the players input sequence and mana
            String inputSequence = player.getPersistentDataContainer().get(main.GetActiveTraitInputKey(), PersistentDataType.STRING);
            int mana = player.getPersistentDataContainer().get(main.GetManaKey(), PersistentDataType.INTEGER);
            int maxMana = player.getPersistentDataContainer().get(main.GetManaMaxKey(), PersistentDataType.INTEGER);

            TextComponent actionBar = new TextComponent(main.statSheetManager.GenerateInputSequenceActionBar(inputSequence, ChatColor.GREEN) + ChatColor.GRAY + "    |    " +
                    main.statSheetManager.GenerateManaActionBar(mana, maxMana));

            player.sendMessage(ChatMessageType.ACTION_BAR, actionBar);


            // find all of the legendary mobs that need to be given visuals
            for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), 50,50,50))
            {
                if (legendaryEntities.contains(entity))
                {
                    continue;
                }

                if (entity.getPersistentDataContainer().has(main.GetLegendaryMobKey())
                    && entity.getPersistentDataContainer().get(main.GetLegendaryMobKey(), PersistentDataType.BOOLEAN))
                {
                    legendaryEntities.add(entity);
                }
            }

            //Test(tick);
        }

        for (Entity entity : legendaryEntities)
        {
            Vector3d offset = new Vector3d(Math.cos((Math.PI*2)/((double) tick /20)), tick*0.1, Math.sin((Math.PI*2)/((double) tick /20)));
            Location location = new Location(entity.getWorld(), entity.getLocation().getX() + offset.x, entity.getLocation().getY() + offset.y, entity.getLocation().getZ() + offset.z);

            entity.getWorld().spawnParticle(Particle.SOUL, location, 10, 0,0,0,0);
        }

    }

    private void Test(int tick)
    {
        for (int i = 0; i < 100; i++)
        {
            RPGparticles.SpawnParticle(Bukkit.getWorlds().get(0), 1, new Location(Bukkit.getWorlds().get(0), random.nextGaussian()*1, random.nextGaussian()*1 + 124, random.nextGaussian()*1), new Vector3d(), Particle.CRIT, 0f);
        }

        RPGparticles.SpawnParticle(Bukkit.getWorlds().get(0), 100, new Location(Bukkit.getWorlds().get(0), 10, 124, 0), new Vector3d(1,1,1), Particle.CRIT, 0f);

    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        // if there is no stat sheet assigned to a player when they join
        if (main.statSheetManager.FindStatSheetByPlayer(player) == null)
        {
            main.statSheetManager.AddStatSheet(new StatSheet(player.getUniqueId()));
        }

        // Check the persistents and add them if needed
        CheckPersistent(player);

        // send the resource pack
        ///player.setResourcePack("https://limewire.com/d/djpm2#KHAZePzhCt");

    }

    private void CheckPersistent(Player player)
    {
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

        // if the player doesn't have a tree progression persistent
        if (!player.getPersistentDataContainer().has(main.GetDeactivatedNodesKey(), PersistentDataType.STRING))
        {
            player.getPersistentDataContainer().set(main.GetDeactivatedNodesKey(), PersistentDataType.STRING, "");
        }

        // if the player doesn't have an active trait input persistent
        if (!player.getPersistentDataContainer().has(main.GetActiveTraitInputKey(), PersistentDataType.STRING))
        {
            player.getPersistentDataContainer().set(main.GetActiveTraitInputKey(), PersistentDataType.STRING, "");
        }

        // if the player doesn't have a mana persistent
        if (!player.getPersistentDataContainer().has(main.GetManaKey(), PersistentDataType.INTEGER))
        {
            player.getPersistentDataContainer().set(main.GetManaKey(), PersistentDataType.INTEGER, 100);
        }

        // if the player doesn't have a mana recharge speed persistent
        if (!player.getPersistentDataContainer().has(main.GetManaRechargeSpeedKey(), PersistentDataType.INTEGER))
        {
            player.getPersistentDataContainer().set(main.GetManaRechargeSpeedKey(), PersistentDataType.INTEGER, 1);
        }

        // if the player doesn't have a max mana persistent
        if (!player.getPersistentDataContainer().has(main.GetManaMaxKey(), PersistentDataType.INTEGER))
        {
            player.getPersistentDataContainer().set(main.GetManaMaxKey(), PersistentDataType.INTEGER, 100);
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

                    // generate the level
                    int level = (int) livingEntity.getAttribute(Attribute.MAX_HEALTH).getValue()  / 4;
                    String customName = "";

                    switch (entity.getType())
                    {
                        case WITHER :
                            level = 200;

                            entity.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, 200);
                            customName = ChatColor.BLACK + ChatColor.BOLD.toString() + entity.getName() + ChatColor.RED + " [lvl:" + entity.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + "]";
                            break;

                        case ENDER_DRAGON :
                            level = 200;

                            entity.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, 200);
                            customName = ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + entity.getName() + ChatColor.RED + " [lvl:" + entity.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + "]";
                            break;
                    }

                    // if this mob should be legendary
                    if (random.nextFloat(0, 1) <= LEGENDARY_MOB_CHANCE)
                    {
                        // give it LEGENDARY_MOB_STAT_MULTIPLIER times the damage
                        livingEntity.getAttribute(Attribute.ATTACK_DAMAGE).addModifier(new AttributeModifier(main.GetLevelStatModKey(), livingEntity.getAttribute(Attribute.ATTACK_DAMAGE).getValue()*LEGENDARY_MOB_STAT_MULTIPLIER-1, AttributeModifier.Operation.ADD_NUMBER));

                        // give it LEGENDARY_MOB_STAT_MULTIPLIER times the hp and LEGENDARY_MOB_STAT_MULTIPLIER times the level
                        livingEntity.getAttribute(Attribute.MAX_HEALTH).addModifier(new AttributeModifier(main.GetLevelStatModKey(), livingEntity.getAttribute(Attribute.MAX_HEALTH).getValue()*LEGENDARY_MOB_STAT_MULTIPLIER-1, AttributeModifier.Operation.ADD_NUMBER));
                        livingEntity.setHealth(livingEntity.getAttribute(Attribute.MAX_HEALTH).getValue());
                        level *= 4;

                        // make it look legendary
                        entity.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, level);
                        entity.getPersistentDataContainer().set(main.GetLegendaryMobKey(), PersistentDataType.BOOLEAN, true);

                        if (customName.isEmpty()) entity.setCustomName(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "LEGENDARY " + entity.getName() + " [lvl:" + entity.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + "]");
                        else entity.setCustomName(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "LEGENDARY " + customName);
                    }
                    else
                    {
                        entity.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, level);
                        if (customName.isEmpty()) entity.setCustomName(entity.getName() + " [lvl:" + entity.getPersistentDataContainer().get(main.GetLevelKey(), PersistentDataType.INTEGER) + "]");
                    }

                    entity.getPersistentDataContainer().set(main.GetLevelKey(), PersistentDataType.INTEGER, level);
                }
            }
        }
    }

    @EventHandler
    public void OnBrewEvent(BrewEvent e)
    {
        List<ItemStack> results = e.getResults();
        for (ItemStack result : results)
        {
            // if there is a result
            if (result.getType() != Material.AIR)
            {
                ItemMeta resultMeta = result.getItemMeta();
                resultMeta.getPersistentDataContainer().set(new NamespacedKey(main, "wasJustBrewed"), PersistentDataType.BOOLEAN, true);

                result.setItemMeta(resultMeta);
            }
        }
    }

    @EventHandler
    public void OnDeath(EntityDeathEvent e)
    {
        LivingEntity killer = e.getEntity().getKiller();
        if (killer == null) return;

        // TODO make this better
        if (e.getEntity().getPersistentDataContainer().has(Main.GetInstance().GetCustomMobKey()))
        {
            // do other things
            return;
        }

        if (e.getEntity() instanceof CaveSpider)
        {
            double chance = 0.15;

            int looting = killer.getEquipment().getItem(EquipmentSlot.HAND).getEnchantmentLevel(Enchantment.LOOTING);
            chance += looting * 0.01;

            if (random.nextFloat() <= chance)
            {
                e.getDrops().add(CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("poison_gland")));
            }
        }
    }
}
