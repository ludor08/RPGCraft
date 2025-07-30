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
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Size_Change_Tiny extends Trait
{
    private AttributeModifier sizeMod;

    public Size_Change_Tiny(Main main) {
        // add the name and lore
        super("Size Change:Tiny", "size change:tiny", ChatColor.AQUA, Material.IRON_NUGGET, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes the player a lot smaller (1.5 blocks)."
        ));

        sizeMod = new AttributeModifier(new NamespacedKey(main, "tiny"), -0.5d, AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getAttribute(Attribute.SCALE).addModifier(sizeMod);
    }

    @Override
    public void OnTick(Player player)
    {

    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getAttribute(Attribute.SCALE).removeModifier(sizeMod);
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

    @Override
    public void OnJump(PlayerJumpEvent e)
    {

    }
}
