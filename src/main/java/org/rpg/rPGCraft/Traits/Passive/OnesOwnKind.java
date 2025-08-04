package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OnesOwnKind extends Trait
{
    public OnesOwnKind(Main main) {
        // add the name and lore
        super("Ones Own Kind", "ones own kind", ChatColor.AQUA, Material.WARDEN_SPAWN_EGG, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Warden aren't aggressive towards you."
        ));
    }

    @Override
    public void OnTargeted(EntityTargetEvent e)
    {
        ((Player)e.getTarget()).sendMessage(e.getEntity() + "");
        if (e.getEntity() instanceof Warden)
        {
            ((Player)e.getTarget()).sendMessage("done");
            e.setCancelled(true);
        }
    }
}
