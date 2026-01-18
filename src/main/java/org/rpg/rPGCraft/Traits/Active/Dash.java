package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGraycast;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class Dash extends ActiveTrait
{
    NamespacedKey distanceKey = new NamespacedKey(Main.GetInstance(), "dash_distance");
    int baseDistance = 5;

    public Dash() {
        // add the name and lore
        super(ChatColor.WHITE + ChatColor.BOLD.toString() + "Dash", "dash", 35, Material.SUGAR, false, List.of(
                ChatColor.AQUA.toString() + "   - Dash forward 5 blocks, hitting any entities you collide with."
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
        Main main = Main.GetInstance();

        float mul = 4;

        Location location = RPGraycast.RecastUntilCollision(player.getPersistentDataContainer().get(distanceKey, PersistentDataType.INTEGER),direction,player.getEyeLocation(), Particle.CRIT, 5);
        List<Entity> entities = RPGraycast.RecastForEntities(player.getPersistentDataContainer().get(distanceKey, PersistentDataType.INTEGER),direction,player.getEyeLocation(), true, player, null, 0,new Vector3d(0.5,0.5,0.5));

        List<Entity> feetEntities = RPGraycast.RecastForEntities(player.getPersistentDataContainer().get(distanceKey, PersistentDataType.INTEGER),direction,player.getLocation(), false, player, null, 0,new Vector3d(0.5,0.5,0.5));

        for (Entity entity : feetEntities)
        {
            if (!entities.contains(entity))
            {
                entities.add(entity);
            }
        }

        location.setPitch(player.getPitch());
        location.setYaw(player.getYaw());

        player.teleport(location);
        for (Entity entity : entities)
        {
            if (main.partyManager.IsInTheSameParty(player, entity))
            {
                continue;
            }

            RPGutils.AttackWithTrait(entity, player, false);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(distanceKey))
        {
            player.getPersistentDataContainer().set(distanceKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(distanceKey, PersistentDataType.INTEGER) - baseDistance);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(distanceKey))
        {
            player.getPersistentDataContainer().set(distanceKey, PersistentDataType.INTEGER, player.getPersistentDataContainer().get(distanceKey, PersistentDataType.INTEGER) + baseDistance);
        }
        else
        {
            player.getPersistentDataContainer().set(distanceKey, PersistentDataType.INTEGER, baseDistance);
        }
    }
}
