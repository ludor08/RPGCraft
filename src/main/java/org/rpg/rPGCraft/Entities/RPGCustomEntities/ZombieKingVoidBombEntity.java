package org.rpg.rPGCraft.Entities.RPGCustomEntities;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.rpg.rPGCraft.Definitions.EntityStates;
import org.rpg.rPGCraft.Entities.LegendaryComponents.BaseLegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGCustomEntity;

public class ZombieKingVoidBombEntity extends RPGCustomEntity
{
    public ZombieKingVoidBombEntity()
    {
        super(EntityType.HUSK, "Void Bomb", "void_bomb", false, 0, 0, false, new BaseLegendaryComponent(), EntityStates.VOID_BOMB_FALLING.GetEntityState(), null);
    }

    @Override
    public Entity InitilizeCustomEntity(Entity entity)
    {
        Husk husk = (Husk) entity;
        husk.setSilent(true);
        husk.setInvisible(true);
        husk.setCollidable(false);
        husk.setInvulnerable(true);

        return husk;
    }
}
