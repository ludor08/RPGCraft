package org.rpg.rPGCraft.Entities.EntityStates.ZombieKing;

import org.bukkit.*;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Definitions.StructureDefinitions;
import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.Entities.RPGCustomEntities.ZombieKing;
import org.rpg.rPGCraft.Entities.RPGCustomEntities.ZombiePeasant;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Structures.ZombieKingArena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZombieKingSummonState extends EntityState
{
    public ZombieKingSummonState()
    {
        super("zombie_king_summon_state");
    }

    NamespacedKey damageMilestone = new NamespacedKey(Main.GetInstance(), "zombie_king_damage_milestone");

    @Override
    public EntityState OnInteractedWith(PlayerInteractEntityEvent event, Entity thisEntity)
    {
        Player player = event.getPlayer();
        World bossWorld = Main.GetInstance().worldManager.GetBossWorld();

        // tp the player
        Location tpLocation = new Location(bossWorld, 0, 0, 0);
        player.teleport(tpLocation);

        // place the arena
        File structureFile = StructureDefinitions.GetStructureFileByID(new ZombieKingArena().GetNameID());
        Location placeLocation = new Location(bossWorld, -10, -2, -10);

        try {
            Bukkit.getStructureManager().loadStructure(structureFile).place(placeLocation, true, StructureRotation.NONE, Mirror.NONE, 0, 1, Main.GetInstance().GetRandom());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /// TODO look into converting the marker to a .yml file
        // spawn the arena marker
        ItemDisplay marker = (ItemDisplay) bossWorld.spawnEntity(placeLocation, EntityType.ITEM_DISPLAY);

        Vector3d floorOffset = new Vector3d(4,1,4);
        Vector2d floorSize = new Vector2d(14,14);

        marker.getPersistentDataContainer().set(MyNamespaces.ARENA_MARKER_FLOOR_START_OFFSET_X.GetNamespacedKey(), PersistentDataType.FLOAT, (float) floorOffset.x);
        marker.getPersistentDataContainer().set(MyNamespaces.ARENA_MARKER_FLOOR_START_OFFSET_Y.GetNamespacedKey(), PersistentDataType.FLOAT, (float) floorOffset.y);
        marker.getPersistentDataContainer().set(MyNamespaces.ARENA_MARKER_FLOOR_START_OFFSET_Z.GetNamespacedKey(), PersistentDataType.FLOAT, (float) floorOffset.z);

        marker.getPersistentDataContainer().set(MyNamespaces.ARENA_MARKER_FLOOR_WIDTH.GetNamespacedKey(), PersistentDataType.INTEGER, (int) floorSize.x);
        marker.getPersistentDataContainer().set(MyNamespaces.ARENA_MARKER_FLOOR_LENGTH.GetNamespacedKey(), PersistentDataType.INTEGER, (int) floorSize.y);

        marker.setItemStack(new ItemStack(Material.RED_STAINED_GLASS_PANE));

        // spawn the king
        Location spawnLocation = tpLocation;
        spawnLocation.add(4, 0, 0);

        ZombieKing customEntity = new ZombieKing();
        Entity zombieKing = customEntity.SpawnCustomEntity(spawnLocation);

        zombieKing.getPersistentDataContainer().set(MyNamespaces.ARENA_MARKER.GetNamespacedKey(), PersistentDataType.STRING, marker.getUniqueId().toString());
        zombieKing.getPersistentDataContainer().set(damageMilestone, PersistentDataType.FLOAT, 0.75f);

        customEntity.SetUpArena(zombieKing);
        return this;
    }

}
