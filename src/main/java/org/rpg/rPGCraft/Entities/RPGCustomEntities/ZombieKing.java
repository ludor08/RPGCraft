package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.damage.DamageSource;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Animation.Animations.ZombieKingIdle;
import org.rpg.rPGCraft.CustomItemComponents.CustomItem;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.LegendaryComponents.ZombieKingLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;
import org.rpg.rPGCraft.EntityManager;
import org.rpg.rPGCraft.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ZombieKing extends RPGCustomEntity
{
    public ZombieKing()
    {
        super(EntityType.ZOMBIE, "Zombie King", "zombie_king", false, 75, 1000, true, new ZombieKingLegendaryComponent(), EntityStates.WEAKEN_ZOMBIE_KING_IDLE.GetEntityState(), new ZombieKingIdle());
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        Zombie zombie = (Zombie) entity;

        zombie.setInvisible(true);

        zombie.getAttribute(Attribute.MAX_HEALTH).addModifier(new AttributeModifier(MyNamespaces.CUSTOM_ENTITY_ATTRIBUTE.GetNamespacedKey(), 6, AttributeModifier.Operation.ADD_SCALAR));
        zombie.setHealth(zombie.getAttribute(Attribute.MAX_HEALTH).getValue());

        zombie.getAttribute(Attribute.MOVEMENT_SPEED).addModifier(new AttributeModifier(MyNamespaces.CUSTOM_ENTITY_ATTRIBUTE.GetNamespacedKey(), 1, AttributeModifier.Operation.ADD_SCALAR));
        return entity;
    }

    public void SetUpArena(Entity entity)
    {
        // get the marker
        Entity marker = Bukkit.getEntity(UUID.fromString(entity.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER.GetNamespacedKey(), PersistentDataType.STRING)));

        Vector3d floorOffset = new Vector3d(
                marker.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER_FLOOR_START_OFFSET_X.GetNamespacedKey(), PersistentDataType.FLOAT),
                marker.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER_FLOOR_START_OFFSET_Y.GetNamespacedKey(), PersistentDataType.FLOAT),
                marker.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER_FLOOR_START_OFFSET_Z.GetNamespacedKey(), PersistentDataType.FLOAT)
        );

        Vector2d floorSize = new Vector2d(
                marker.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER_FLOOR_WIDTH.GetNamespacedKey(), PersistentDataType.INTEGER),
                marker.getPersistentDataContainer().get(MyNamespaces.ARENA_MARKER_FLOOR_LENGTH.GetNamespacedKey(), PersistentDataType.INTEGER)
        );

        // spawn the zombie peasants
        int numberToBeSpawned = 12;
        int numberOfShieldCatalyst = 3;

        List<Zombie> summons = new ArrayList<>();
        ZombiePeasant zombiePeasant = new ZombiePeasant();

        for (int i = 0; i < numberToBeSpawned; i++)
        {
            Location loc = new Location(
                    marker.getWorld(),
                    marker.getX() + floorOffset.x + Main.GetInstance().GetRandom().nextInt(0, (int) floorSize.x),
                    marker.getY() + floorOffset.y,
                    marker.getZ() + floorOffset.z + Main.GetInstance().GetRandom().nextInt(0, (int) floorSize.y)
            );

            Zombie peasant = (Zombie) zombiePeasant.SpawnCustomEntity(loc);
            peasant.getPersistentDataContainer().set(new NamespacedKey(Main.GetInstance(), "zombie_king_commander"), PersistentDataType.STRING, entity.getUniqueId().toString());

            summons.add(peasant);
        }

        // make a number of them shield catalysts
        for (int i = 0; i < numberOfShieldCatalyst; i++)
        {
            int index = Main.GetInstance().GetRandom().nextInt(summons.size());

            Zombie zombie = summons.get(index);
            zombie.getPersistentDataContainer().set(new NamespacedKey(Main.GetInstance(), "zombie_king_shield_catalyst"), PersistentDataType.STRING, entity.getUniqueId().toString());
            zombie.setGlowing(true);

            summons.remove(index);
        }
    }

    @Override
    public List<ItemStack> GetDrops(LivingEntity entity, List<ItemStack> unmodifiedDrops, DamageSource damageSource)
    {
        List<ItemStack> newLoot = new ArrayList<>();
        newLoot.add(CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("ferula_of_the_last_pope")));

        return newLoot;
    }
}
