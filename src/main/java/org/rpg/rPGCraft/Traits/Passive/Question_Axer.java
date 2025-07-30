package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;
import java.util.Objects;

public class Question_Axer extends Trait
{
    Main main;

    public Question_Axer(Main main) {
        // add the name and lore
        super("Question Axer", "question axer", ChatColor.AQUA, Material.IRON_AXE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Does 10% more damage with axes."
        ));

        this.main = main;
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
    }

    @Override
    public void OnTick(Player player)
    {

    }

    @Override
    public void OnRemoveTraitBuff(Player player)
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
        float DAMAGE_MOD = 1.10f;

        ItemStack weapon = ((Player) e.getDamager()).getInventory().getItem(EquipmentSlot.HAND);

        // if the player was using an axe or its name contains the word "axe"
        if (main.gameManager.GetAxeTypes().contains(weapon.getType()) ||
                Objects.equals(weapon.getPersistentDataContainer().get(main.GetWeaponTypeKey(), PersistentDataType.STRING), "axe"))
        {
            // do AXE_DAMAGE_MOD times more damage
            e.setDamage(e.getDamage()*DAMAGE_MOD);
        }
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
