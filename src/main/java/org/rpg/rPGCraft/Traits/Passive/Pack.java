package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.ArrayList;
import java.util.List;

public class Pack extends Trait
{
    private NamespacedKey packModKey;

    public Pack() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Pack", "pack", Material.BONE, true, List.of(
                ChatColor.AQUA.toString() + "   - For every tamed wolf or a player (these can be any race)",
                ChatColor.AQUA.toString() + "     within 10 blocks that hasn’t the last damage source and is in the same party as you,",
                ChatColor.AQUA.toString() + "     you gain +2 base defense (to a max of +10), and +1 damage (to a max of +5),",
                ChatColor.AQUA.toString() + "     and the wolf(s) and the player(s) gain resistance one."
        ));

        packModKey = new NamespacedKey(Main.GetInstance(), "pack");
    }

    @Override
    public void OnTick(Player player)
    {
        List<Entity> packMembers = new ArrayList<>();

        for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), 10,10,10))
        {
            if (player.getLastDamageCause() != null)
            {
                if (player.getLastDamageCause().getEntity() != entity)
                {
                    if (Main.GetInstance().partyManager.IsInTheSameParty(player, entity))
                    {
                        packMembers.add(entity);
                    }
                }
            }
            else
            {
                if (Main.GetInstance().partyManager.IsInTheSameParty(player, entity))
                {
                    packMembers.add(entity);
                }
            }
        }

        // if the player has a pack
        if (!packMembers.isEmpty())
        {
            // if the player has an armor mod from pack, remove it
            if (player.getAttribute(Attribute.ARMOR).getModifier(packModKey) != null)
            {
                player.getAttribute(Attribute.ARMOR).removeModifier(packModKey);
            }

            // if the player has an attack damage mod from pack, remove it
            if (player.getAttribute(Attribute.ATTACK_DAMAGE).getModifier(packModKey) != null)
            {
                player.getAttribute(Attribute.ATTACK_DAMAGE).removeModifier(packModKey);
            }

            // add the new pack mods
            player.getAttribute(Attribute.ARMOR).addModifier(new AttributeModifier(packModKey, Math.min(packMembers.size(), 5)*2, AttributeModifier.Operation.ADD_NUMBER));
            player.getAttribute(Attribute.ATTACK_DAMAGE).addModifier(new AttributeModifier(packModKey, Math.min(packMembers.size(), 5), AttributeModifier.Operation.ADD_NUMBER));

            // give buffs to all other pack members
            for (Entity entity : packMembers)
            {
                // if the entity is a living entity
                if (entity instanceof LivingEntity living)
                {
                    living.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 0));
                }
            }
        }
        else
        {
            // if the player has an armor mod from pack, remove it
            if (player.getAttribute(Attribute.ARMOR).getModifier(packModKey) != null)
            {
                player.getAttribute(Attribute.ARMOR).removeModifier(packModKey);
            }

            // if the player has an attack damage mod from pack, remove it
            if (player.getAttribute(Attribute.ATTACK_DAMAGE).getModifier(packModKey) != null)
            {
                player.getAttribute(Attribute.ATTACK_DAMAGE).removeModifier(packModKey);
            }
        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getAttribute(Attribute.ARMOR).removeModifier(packModKey);
        player.getAttribute(Attribute.ATTACK_DAMAGE).removeModifier(packModKey);
    }
}
