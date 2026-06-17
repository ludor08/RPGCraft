package org.rpg.rPGCraft.Entities.LegendaryComponents;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Definitions.MyNamespaces;
import org.rpg.rPGCraft.Entities.LegendaryComponent;
import org.rpg.rPGCraft.Entities.RPGEntity;

import java.util.List;

public class WitherLegendaryComponent extends LegendaryComponent
{
    public WitherLegendaryComponent()
    {
        super(
                0.05f,
                RPGEntity.ConstructAttributeHashMap(List.of(Attribute.MAX_HEALTH), List.of(new AttributeModifier(MyNamespaces.LEGENDARY_MOB_ATTRIBUTE.GetNamespacedKey(), 2d, AttributeModifier.Operation.ADD_SCALAR))),
                2,
                2
        );
    }

    @Override
    public void ShowTickOfLegendary(int tick, Entity entity)
    {
        for (int i = 0; i < 3; i++)
        {
            Vector3d offset = new Vector3d(Math.cos((Math.PI*2)/((double) tick /20)), i, Math.sin((Math.PI*2)/((double) tick /20)));
            Location location = new Location(entity.getWorld(), entity.getLocation().getX() + offset.x, entity.getLocation().getY() + offset.y, entity.getLocation().getZ() + offset.z);

            entity.getWorld().spawnParticle(Particle.FLAME, location, 10, 0,0,0,0);
        }
    }

    @Override
    public String GetLegendaryName(String name)
    {
        return ChatColor.BLACK + ChatColor.BOLD.toString() + "LEGENDARY " + name;
    }
}
