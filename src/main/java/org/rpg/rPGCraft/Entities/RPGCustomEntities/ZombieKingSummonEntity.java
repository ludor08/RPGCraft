package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;

public class ZombieKingSummonEntity extends RPGCustomEntity
{
    public ZombieKingSummonEntity()
    {
        super(EntityType.HUSK, "Zombie King Summon", "zombie_king_summon", false, 0, 0, false, new BaseLegendaryComponent(), EntityStates.ZOMBIE_KING_SUMMONER.GetEntityState(), null);
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        Husk husk = (Husk) entity;
        husk.getEquipment().setItem(EquipmentSlot.HEAD, new ItemStack(Material.RED_STAINED_GLASS));
        return husk;
    }
}
