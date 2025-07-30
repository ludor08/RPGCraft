package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class SandCrawler extends Trait
{
    private final AttributeModifier badLandSpeedMod;
    private final AttributeModifier goodLandSpeedMod;
    private final AttributeModifier waterSpeedMod;
    private final AttributeModifier gravityMod;

    public SandCrawler(Main main) {
        // add the name and lore
        super("Sand Crawler", "sand crawler", ChatColor.AQUA, Material.SAND, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Move 25% slower on land.",
                ChatColor.AQUA.toString() + "   - Move 2x faster when walking on the sea floor.",
                ChatColor.AQUA.toString() + "   - Fall faster in water when sneaking."
        ));

        badLandSpeedMod = new AttributeModifier(new NamespacedKey(main, "bad_land_sc"), -0.10d, AttributeModifier.Operation.ADD_SCALAR);
        goodLandSpeedMod = new AttributeModifier(new NamespacedKey(main, "good_land_sc"), 2, AttributeModifier.Operation.ADD_SCALAR);
        waterSpeedMod = new AttributeModifier(new NamespacedKey(main, "water_sc"), 1, AttributeModifier.Operation.ADD_NUMBER);
        gravityMod = new AttributeModifier(new NamespacedKey(main, "gravity_sc"), 15, AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getAttribute(Attribute.MOVEMENT_SPEED).addModifier(badLandSpeedMod);
    }

    @Override
    public void OnTick(Player player)
    {
        if (player.isSneaking())
        {
            if (!player.getAttribute(Attribute.GRAVITY).getModifiers().contains(gravityMod))
            {
                player.getAttribute(Attribute.GRAVITY).addModifier(gravityMod);
            }
        }
        else
        {
            if (player.getAttribute(Attribute.GRAVITY).getModifiers().contains(gravityMod))
            {
                player.getAttribute(Attribute.GRAVITY).removeModifier(gravityMod);
            }
        }

        Material footMaterial = player.getWorld().getBlockAt(player.getLocation()).getType();

        if (footMaterial.equals(Material.WATER) || footMaterial.equals(Material.KELP_PLANT) || footMaterial.equals(Material.SEAGRASS) || footMaterial.equals(Material.TALL_SEAGRASS))
        {
            Material floorMaterial = player.getWorld().getBlockAt(player.getLocation().add(new Vector(0, -0.1, 0))).getType();

            if (!floorMaterial.equals(Material.AIR) && !floorMaterial.equals(Material.WATER) && !floorMaterial.equals(Material.KELP_PLANT) && !floorMaterial.equals(Material.SEAGRASS) && !floorMaterial.equals(Material.TALL_SEAGRASS))
            {
                if (!player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).getModifiers().contains(waterSpeedMod))
                {
                    player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).addModifier(waterSpeedMod);
                }

                if (!player.getAttribute(Attribute.MOVEMENT_SPEED).getModifiers().contains(goodLandSpeedMod))
                {
                    player.getAttribute(Attribute.MOVEMENT_SPEED).addModifier(goodLandSpeedMod);
                }
            }

            return;
        }

        if (player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).getModifiers().contains(waterSpeedMod))
        {
            player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).removeModifier(waterSpeedMod);
        }

        if (player.getAttribute(Attribute.MOVEMENT_SPEED).getModifiers().contains(goodLandSpeedMod))
        {
            player.getAttribute(Attribute.MOVEMENT_SPEED).removeModifier(goodLandSpeedMod);
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getAttribute(Attribute.MOVEMENT_SPEED).getModifiers().contains(badLandSpeedMod))
        {
            player.getAttribute(Attribute.MOVEMENT_SPEED).removeModifier(badLandSpeedMod);
        }

        if (player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).getModifiers().contains(waterSpeedMod))
        {
            player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).removeModifier(waterSpeedMod);
        }

        if (player.getAttribute(Attribute.GRAVITY).getModifiers().contains(gravityMod))
        {
            player.getAttribute(Attribute.GRAVITY).removeModifier(gravityMod);
        }
    }

    @Override
    public void OnJump(PlayerJumpEvent e)
    {

    }

    @Override
    public void OnRespawnBuffs(PlayerRespawnEvent e)
    {

    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {

    }

    @Override
    public void OnDealDamage(EntityDamageByEntityEvent e)
    {

    }

    @Override
    public void OnFoodLevelChange(FoodLevelChangeEvent e)
    {

    }

    @Override
    public void OnSneak(PlayerToggleSneakEvent e)
    {

    }
}
