package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.*;
import org.rpg.rPGCraft.Traits.ActiveTrait;

import java.util.List;

public class SmokeBomb extends ActiveTrait
{
    NamespacedKey secretTechniqueKey = new NamespacedKey(Main.GetInstance(), "secret_technique");

    NamespacedKey durationKey = new NamespacedKey(Main.GetInstance(), "smoke_bomb_duration");
    int baseDuration = 200;

    public SmokeBomb() {
        // add the name and lore

        super(ChatColor.DARK_GRAY + ChatColor.BOLD.toString() + "Smoke Bomb", "smoke bomb", 75, Material.GUNPOWDER, false, List.of(
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
        RPGparticles.SpawnParticle(200, player.getLocation(), new Vector3d(0,0,0), Particle.SMOKE, 0.5f);

        // if the player has secret technique
        if (player.getPersistentDataContainer().has(secretTechniqueKey) && player.isSneaking())
        {
            Location location = RPGraycast.RecastUntilCollision(100, RPGutils.getFacingDirection(player), player.getEyeLocation(), null, 0);

            player.teleport(location);

            // spawn the smoke
            RPGparticles.SpawnParticle(200, location, new Vector3d(0,0,0), Particle.SMOKE, 0.5f);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, durationKey, 0, -baseDuration);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.AddToNamespacedKey(player, durationKey, 0, baseDuration);
    }
}
