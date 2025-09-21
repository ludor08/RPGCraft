package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class TeleportingGrapple extends Trait
{
    private final NamespacedKey teleportingGrappleKey = new NamespacedKey(main, "teleporting_grapple");

    public TeleportingGrapple(Main main) {
        // add the name and lore
        super("Teleporting Grapple", "teleporting grapple", ChatColor.AQUA, Material.ENDER_EYE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Grappling Hook teleport you to the arrow, instead of launching you."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        if (player.getPersistentDataContainer().has(teleportingGrappleKey))
        {
            player.getPersistentDataContainer().remove(teleportingGrappleKey);
        }
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getPersistentDataContainer().set(teleportingGrappleKey, PersistentDataType.BOOLEAN, true);
    }
}
