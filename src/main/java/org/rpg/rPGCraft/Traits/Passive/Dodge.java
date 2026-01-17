package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;
import java.util.Random;

public class Dodge extends Trait
{
    NamespacedKey dodgeChanceKey = new NamespacedKey(Main.GetInstance(), "dodge_chance");
    float baseDodgeChance = 0.11f;

    public Dodge()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Dodge", "dodge", Material.ELYTRA, false, List.of(
                ChatColor.AQUA.toString() + "   - Gives you a 11% chance to dodge incoming damage. (May be negated by certain abilities/enemies)"
        ));
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (new Random().nextFloat() <= e.getEntity().getPersistentDataContainer().get(dodgeChanceKey, PersistentDataType.FLOAT))
        {

        }
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(dodgeChanceKey))
        {
            player.getPersistentDataContainer().set(dodgeChanceKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(dodgeChanceKey, PersistentDataType.FLOAT) - baseDodgeChance);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(dodgeChanceKey))
        {
            player.getPersistentDataContainer().set(dodgeChanceKey, PersistentDataType.FLOAT, player.getPersistentDataContainer().get(dodgeChanceKey, PersistentDataType.FLOAT) + baseDodgeChance);
        }
        else
        {
            player.getPersistentDataContainer().set(dodgeChanceKey, PersistentDataType.FLOAT, baseDodgeChance);
        }
    }
}
