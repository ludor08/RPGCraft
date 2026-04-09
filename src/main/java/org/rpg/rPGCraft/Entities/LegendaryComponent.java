package org.rpg.rPGCraft.Entities;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.joml.Vector3d;

import java.util.HashMap;

public abstract class LegendaryComponent
{
    private float legendaryChance;
    private HashMap<Attribute, AttributeModifier> attributeChanges;
    private float xpMultiplier;
    private float levelMultiplier;

    public LegendaryComponent(float legendaryChance, HashMap<Attribute, AttributeModifier> attributeChanges, float xpMultiplier, float levelMultiplier)
    {
        this.legendaryChance = legendaryChance;
        this.attributeChanges = attributeChanges;
        this.xpMultiplier = xpMultiplier;
        this.levelMultiplier = levelMultiplier;
    }

    public float GetLegendaryChance()
    {
        return legendaryChance;
    }

    public HashMap<Attribute, AttributeModifier> GetAttributeChanges()
    {
        return attributeChanges;
    }

    public float GetXpMultiplier()
    {
        return xpMultiplier;
    }

    public float GetLevelMultiplier()
    {
        return levelMultiplier;
    }

    public String GetLegendaryName(String name)
    {
        return ChatColor.DARK_RED + ChatColor.BOLD.toString() + "LEGENDARY " + name;
    }

    public void ShowTickOfLegendary(int tick, Entity entity)
    {
        Vector3d offset = new Vector3d(Math.cos((Math.PI*2)/((double) tick /20)), tick *0.1, Math.sin((Math.PI*2)/((double) tick /20)));
        Location location = new Location(entity.getWorld(), entity.getLocation().getX() + offset.x, entity.getLocation().getY() + offset.y, entity.getLocation().getZ() + offset.z);

        entity.getWorld().spawnParticle(Particle.SOUL, location, 10, 0,0,0,0);
    }

    public void ApplyLegendaryAttributeMods(LivingEntity entity)
    {
        for (Attribute attribute : attributeChanges.keySet())
        {
            if (entity.getAttribute(attribute) != null)
            {
                entity.getAttribute(attribute).addModifier(attributeChanges.get(attribute));

                // check if the attribute needs to have something special happen
                switch (attribute.getKey().getKey())
                {
                    case "max_health":
                        entity.setHealth(entity.getAttribute(attribute).getValue());
                        break;
                }
            }
        }
    }
}
