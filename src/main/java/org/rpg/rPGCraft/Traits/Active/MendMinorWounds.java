package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class MendMinorWounds extends ActiveTrait
{
    private final NamespacedKey healAmountKey = new NamespacedKey(Main.GetInstance(), "mend_minor_wounds_amount");
    int baseHealAmount = 5;

    public MendMinorWounds() {
        // add the name and lore
        super(ChatColor.GREEN + ChatColor.BOLD.toString() + "Mend Minor Wounds", "mend minor wounds", 35, Material.TURTLE_SCUTE, false, List.of(
                ChatColor.AQUA.toString() + "   - Shoots a beam that heals whichever entity it hits for five health.",
                ChatColor.AQUA.toString() + "     If the beam doesn't hit anything, heal yourself instead."
        ));
    }

    @Override
    public String GetInputSequence()
    {
        return "111";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        Vector3d direction = new Vector3d(-Math.cos(Math.toRadians(player.getPitch())) * Math.sin(Math.toRadians(player.getYaw())), -Math.sin(Math.toRadians(player.getPitch())), Math.cos(Math.toRadians(player.getPitch())) * Math.cos(Math.toRadians(player.getYaw())));
        Entity lookingAt = RPGraycast.RecastForEntity(100, direction, player.getEyeLocation(), true, player, null, 0);

        if (lookingAt instanceof LivingEntity livingLookingAt)
        {
            RPGutils.HealWithTraits(player,livingLookingAt,player.getPersistentDataContainer().get(healAmountKey, PersistentDataType.INTEGER),EntityRegainHealthEvent.RegainReason.MAGIC,main);
            RPGraycast.RecastForEntity(100, direction, player.getEyeLocation(), true, player, Particle.HAPPY_VILLAGER, 5);
        }
        else if (lookingAt == null)
        {
            RPGutils.HealWithTraits(player,player,player.getPersistentDataContainer().get(healAmountKey, PersistentDataType.INTEGER),EntityRegainHealthEvent.RegainReason.MAGIC,main);
            for (int i = 1; i < 20; i++)
            {
                Vector3d offset = new Vector3d(Math.cos((Math.PI*2)/((double) i /20)), i*0.1, Math.sin((Math.PI*2)/((double) i /20)));
                Location location = new Location(player.getWorld(), player.getLocation().getX() + offset.x, player.getLocation().getY() + offset.y, player.getLocation().getZ() + offset.z);

                player.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, location, 10, 0,0,0,0);
            }
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(healAmountKey))
        {
            player.getPersistentDataContainer().set(healAmountKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(healAmountKey, PersistentDataType.INTEGER) - baseHealAmount);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(healAmountKey))
        {
            player.getPersistentDataContainer().set(healAmountKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(healAmountKey, PersistentDataType.INTEGER) + baseHealAmount);
        }
        else
        {
            player.getPersistentDataContainer().set(healAmountKey, PersistentDataType.INTEGER, baseHealAmount);
        }
    }
}
