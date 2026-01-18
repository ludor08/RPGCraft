package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class ToxicSpores extends Trait
{

    public ToxicSpores()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Toxic Spores", "toxic spores", Material.SEAGRASS, false, List.of(
                ChatColor.AQUA.toString() + "   - Upon getting hit releases a cloud  of toxic spores",
                ChatColor.AQUA.toString() + "     that give the nausea condition"
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (e.isCancelled())
        {
            return;
        }

        for (Entity entity : e.getEntity().getNearbyEntities(1, 2, 1))
        {
            if (entity instanceof LivingEntity living)
            {
                living.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 1));
            }
        }

        e.getEntity().getWorld().spawnParticle(Particle.SPORE_BLOSSOM_AIR, e.getEntity().getLocation().add(Vector.fromJOML(new Vector3d(0,1,0))), 100, 0.5,0.5,0.5);
    }
}
