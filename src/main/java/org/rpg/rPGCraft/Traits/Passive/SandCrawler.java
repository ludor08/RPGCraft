package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class SandCrawler extends Trait
{
    private final AttributeModifier badLandSpeedMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "bad_land_sc"), -0.10d, AttributeModifier.Operation.ADD_SCALAR);;
    private final AttributeModifier goodLandSpeedMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "good_land_sc"), 2, AttributeModifier.Operation.ADD_SCALAR);;
    private final AttributeModifier waterSpeedMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "water_sc"), 1, AttributeModifier.Operation.ADD_NUMBER);;
    private final AttributeModifier gravityMod = new AttributeModifier(new NamespacedKey(Main.GetInstance(), "gravity_sc"), 15, AttributeModifier.Operation.ADD_NUMBER);;

    public SandCrawler() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Sand Crawler", "sand crawler", Material.SAND, true, List.of(
                ChatColor.AQUA.toString() + "   - Move 25% slower on land.",
                ChatColor.AQUA.toString() + "   - Move 2x faster when walking on the sea floor.",
                ChatColor.AQUA.toString() + "   - Fall faster in water when sneaking."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getAttribute(Attribute.MOVEMENT_SPEED).addModifier(badLandSpeedMod);
    }

    @Override
    public void OnTick(Player player)
    {


        Material footMaterial = player.getWorld().getBlockAt(player.getLocation()).getType();

        if (footMaterial.equals(Material.WATER) || footMaterial.equals(Material.KELP_PLANT) || footMaterial.equals(Material.SEAGRASS) || footMaterial.equals(Material.TALL_SEAGRASS))
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

            if (player.getWorld().getBlockAt(player.getLocation().add(new Vector(0, -0.1, 0))).isSolid())
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
        else
        {
            if (player.getAttribute(Attribute.GRAVITY).getModifiers().contains(gravityMod))
            {
                player.getAttribute(Attribute.GRAVITY).removeModifier(gravityMod);
            }
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
}
