package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.List;

public class TeleportingGrapple extends Trait
{
    private final NamespacedKey teleportingGrappleKey = new NamespacedKey(Main.GetInstance(), "teleporting_grapple");

    public TeleportingGrapple() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Teleporting Grapple", "teleporting grapple", Material.ENDER_EYE, false, List.of(
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
