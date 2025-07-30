package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.List;

public class Arthropod_trait extends Trait
{

    public Arthropod_trait(Main main) {
        // add the name and lore
        super("Arthropod", "arthropod trait", ChatColor.AQUA, Material.FERMENTED_SPIDER_EYE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Half weak to Bane of Arthropods."
        ));
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {

    }

    @Override
    public void OnTick(Player player) {

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
        DamageCause damageCause = e.getCause();

        // the damage is coming from an entity
        if ((damageCause.equals(DamageCause.ENTITY_SWEEP_ATTACK) || damageCause.equals(DamageCause.ENTITY_ATTACK) || damageCause.equals(DamageCause.ENTITY_EXPLOSION)))
        {
            ItemStack weapon = ((LivingEntity)e.getDamageSource().getCausingEntity()).getEquipment().getItem(EquipmentSlot.HAND);

            // if the weapon has Bane of Arthropods
            if (weapon.getType() != Material.AIR && weapon.getItemMeta().getEnchants().containsKey(Enchantment.BANE_OF_ARTHROPODS))
            {
                e.setDamage(e.getDamage()+(weapon.getItemMeta().getEnchants().get(Enchantment.BANE_OF_ARTHROPODS).byteValue()+1));
            }
        }
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
