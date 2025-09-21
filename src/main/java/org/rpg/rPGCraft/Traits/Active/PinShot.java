package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class PinShot extends ActiveTrait
{
    NamespacedKey pinShotKey = new NamespacedKey(main, "pin_shot");

    public PinShot(Main main) {
        // add the name and lore
        super("Pin Shot", "pin shot", 35, ChatColor.GRAY, Material.POINTED_DRIPSTONE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Launch an arrow that pins your enemy to ground for five seconds."
        ));


    }

    @Override
    public String GetInputSequence()
    {
        return "100";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        Arrow arrow = player.launchProjectile(Arrow.class);
        arrow.getPersistentDataContainer().set(pinShotKey, PersistentDataType.BOOLEAN, true);
    }

    @Override
    public void OnShotProjectileHit(ProjectileHitEvent e)
    {
        if (e.getEntity().getPersistentDataContainer().has(pinShotKey))
        {
            if (e.getHitEntity() instanceof LivingEntity living)
            {
                living.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 255,true,false));
                living.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 100, 255,true,false));
            }
        }
    }
}
