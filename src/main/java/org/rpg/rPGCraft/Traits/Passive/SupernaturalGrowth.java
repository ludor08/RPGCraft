package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;
import org.rpg.rPGCraft.Trait;

import java.util.List;
import java.util.Objects;

public class SupernaturalGrowth extends Trait
{
    private final NamespacedKey sizeMod;
    private final NamespacedKey damageMod;

    public SupernaturalGrowth(Main main) {
        // add the name and lore
        super("Supernatural Growth", "supernatural growth", ChatColor.AQUA, Material.RED_MUSHROOM_BLOCK, true, main, List.of(
                ChatColor.AQUA.toString() + "   - Starts off 1 block tall but standing in water causes",
                ChatColor.AQUA.toString() + "     the player to grow in size, up to 3 blocks tall.",
                ChatColor.AQUA.toString() + "   - Gives three damage per block over one"
        ));

        sizeMod = new NamespacedKey(main, "supernatural_growth_size");
        damageMod = new NamespacedKey(main, "supernatural_growth_damage");
    }

    @Override
    public void OnTick(Player player)
    {
        Material footMaterial = player.getWorld().getBlockAt(player.getLocation()).getType();

        // if they are in water
        if (footMaterial.equals(Material.WATER) || footMaterial.equals(Material.KELP_PLANT) || footMaterial.equals(Material.SEAGRASS) || footMaterial.equals(Material.TALL_SEAGRASS))
        {
            RPGutils.SafeAttributeAdd(Attribute.SCALE, new AttributeModifier(sizeMod, 0.05, AttributeModifier.Operation.ADD_NUMBER), player, 0.55f);
            RPGutils.SafeAttributeAdd(Attribute.ATTACK_DAMAGE, new AttributeModifier(damageMod, 0.3, AttributeModifier.Operation.ADD_NUMBER), player, 6);
        }
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        RPGutils.SafeAttributeRemove(Attribute.SCALE, new AttributeModifier(sizeMod, (e.getDamage()/10), AttributeModifier.Operation.ADD_NUMBER), ((Player)e.getEntity()), -0.45f);
        RPGutils.SafeAttributeRemove(Attribute.ATTACK_DAMAGE, new AttributeModifier(damageMod, (e.getDamage()/60), AttributeModifier.Operation.ADD_NUMBER), ((Player)e.getEntity()), 0);
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getAttribute(Attribute.SCALE).getModifier(sizeMod) != null)
        {
            player.getAttribute(Attribute.SCALE).removeModifier(sizeMod);
        }

        if (player.getAttribute(Attribute.ATTACK_DAMAGE).getModifier(damageMod) != null)
        {
            player.getAttribute(Attribute.ATTACK_DAMAGE).removeModifier(damageMod);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getAttribute(Attribute.SCALE).getModifier(sizeMod) == null)
        {
            player.getAttribute(Attribute.SCALE).addModifier(new AttributeModifier(sizeMod, -0.45, AttributeModifier.Operation.ADD_NUMBER));
        }
    }
}
