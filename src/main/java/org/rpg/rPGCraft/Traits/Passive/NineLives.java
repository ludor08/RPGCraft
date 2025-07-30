package org.rpg.rPGCraft.Traits.Passive;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

public class NineLives extends Trait
{
    Main main;

    public NineLives(Main main) {
        // add the name and lore
        super("Nine Lives", "nine lives", ChatColor.AQUA, Material.RED_DYE, false, main, List.of(
                ChatColor.AQUA.toString() + "   - Once per life when the Feloid is dropped to 0 hp, they drop to 2 hp instead."
        ));

        this.main = main;
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        player.getPersistentDataContainer().set(main.statSheetManager.nineLivesKey, PersistentDataType.BOOLEAN, true);
    }

    @Override
    public void OnTick(Player player) {

    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        player.getPersistentDataContainer().remove(main.statSheetManager.nineLivesKey);
    }

    @Override
    public void OnRespawnBuffs(PlayerRespawnEvent e)
    {
        e.getPlayer().getPersistentDataContainer().set(main.statSheetManager.nineLivesKey, PersistentDataType.BOOLEAN, true);
    }

    @Override
    public void OnTakeDamage(EntityDamageEvent e)
    {
        if (((Player)e.getEntity()).getHealth()-e.getDamage() < 1 &&
                e.getEntity().getPersistentDataContainer().get(main.statSheetManager.nineLivesKey, PersistentDataType.BOOLEAN))
        {
            e.setCancelled(true);
            ((Player)e.getEntity()).setHealth(2);

            e.getEntity().getWorld().spawnParticle(Particle.CRIT, e.getEntity().getLocation(), 100, 0,0,0,1);
            ((Player) e.getEntity()).playSound(e.getEntity().getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);

            e.getEntity().getPersistentDataContainer().set(main.statSheetManager.nineLivesKey, PersistentDataType.BOOLEAN, false);
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
