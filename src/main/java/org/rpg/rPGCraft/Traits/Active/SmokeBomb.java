package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class SmokeBomb extends ActiveTrait
{
    NamespacedKey secretTechniqueKey = new NamespacedKey(main, "secret_technique");

    NamespacedKey durationKey = new NamespacedKey(main, "smoke_bomb_duration");
    int baseDuration = 200;

    public SmokeBomb(Main main) {
        // add the name and lore
        super("Smoke Bomb", "smoke bomb", 75, ChatColor.DARK_GRAY, Material.GUNPOWDER, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Throw down a smoke bomb, and disappear for 10 seconds."
        ));


    }

    @Override
    public String GetInputSequence()
    {
        return "010";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        // disappear the player
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,player.getPersistentDataContainer().get(durationKey, PersistentDataType.INTEGER),1,true,false,true));

        // spawn the smoke
        player.getWorld().spawnParticle(Particle.SMOKE, player.getLocation(), 200);

        // if the player has secret technique
        if (player.getPersistentDataContainer().has(secretTechniqueKey) && player.isSneaking())
        {
            Location location = RPGraycast.RecastUntilCollision(100, RPGutils.getFacingDirection(player), player.getEyeLocation(), null, 0);

            player.teleport(location);

            // spawn the smoke
            player.getWorld().spawnParticle(Particle.SMOKE, location, 200);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(durationKey))
        {
            player.getPersistentDataContainer().set(durationKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(durationKey, PersistentDataType.INTEGER) - baseDuration);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(durationKey))
        {
            player.getPersistentDataContainer().set(durationKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(durationKey, PersistentDataType.INTEGER) + baseDuration);
        }
        else
        {
            player.getPersistentDataContainer().set(durationKey, PersistentDataType.INTEGER, baseDuration);
        }
    }
}
