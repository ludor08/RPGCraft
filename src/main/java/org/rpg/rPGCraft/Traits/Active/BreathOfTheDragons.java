package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class BreathOfTheDragons extends ActiveTrait
{
    private final NamespacedKey breathKey = new NamespacedKey(main, "breath_of_the_dragons");

    public BreathOfTheDragons(Main main) {
        // add the name and lore
        super("Breath Of The Dragons", "breath of the dragons", 65, ChatColor.DARK_PURPLE, Material.DRAGON_BREATH, false, main, List.of(
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
        DragonFireball dragonFireball = player.launchProjectile(DragonFireball.class);
        dragonFireball.setVelocity(new Vector(dragonFireball.getVelocity().getX()/2,dragonFireball.getVelocity().getY()/2,dragonFireball.getVelocity().getZ()/2));
        dragonFireball.getPersistentDataContainer().set(breathKey, PersistentDataType.BOOLEAN, true);
    }
}
