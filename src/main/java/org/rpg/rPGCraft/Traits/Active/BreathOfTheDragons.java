package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGparticles;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class BreathOfTheDragons extends ActiveTrait
{
    private final NamespacedKey breathKey = new NamespacedKey(Main.GetInstance(), "breath_of_the_dragons");

    public BreathOfTheDragons() {
        // add the name and lore
        super(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Breath Of The Dragons", "breath of the dragons", 65, Material.DRAGON_BREATH, false, List.of(
                ChatColor.AQUA.toString() + "   - Shoot a small dragons fire ball."
        ));
    }

    @Override
    public String GetInputSequence()
    {
        return "001";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        // spawn the fireball
        DragonFireball dragonFireball = player.launchProjectile(DragonFireball.class);
        dragonFireball.setVelocity(new Vector(dragonFireball.getVelocity().getX()/2,dragonFireball.getVelocity().getY()/2,dragonFireball.getVelocity().getZ()/2));
        dragonFireball.getPersistentDataContainer().set(breathKey, PersistentDataType.BOOLEAN, true);

        // spawn the particle
        Location loc = player.getEyeLocation().add(RPGutils.getFacingDirection(player).x, RPGutils.getFacingDirection(player).y, RPGutils.getFacingDirection(player).z);

        RPGparticles.SpawnParticleDoughnut(player.getWorld(), 5, loc, new Vector3d(0,0,0), Particle.DRAGON_BREATH, 0, 0.5f, 20, RPGutils.getFacingDirection(player));
    }
}
