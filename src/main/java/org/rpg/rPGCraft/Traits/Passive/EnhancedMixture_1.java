package org.rpg.rPGCraft.Traits.Passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.rpg.rPGCraft.Traits.Trait;

import java.util.ArrayList;
import java.util.List;

public class EnhancedMixture_1 extends Trait
{

    public EnhancedMixture_1()
    {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Enhanced Mixture", "enhanced mixture 1", Material.POTION, false, List.of(
                ChatColor.AQUA.toString() + "   - The duration of potion brewed by you is increased",
                ChatColor.AQUA.toString() + "     by 30 seconds if the duration was already more than one second."
        ));
    }

    @Override
    public void OnTakePotionFromBrewingStand(InventoryClickEvent e)
    {
        // get the potion meta
        PotionMeta potionMeta = (PotionMeta) e.getClickedInventory().getItem(e.getSlot()).getItemMeta();

        // get the potion effects
        List<PotionEffect> basePotionEffects = new ArrayList<>();

        basePotionEffects.addAll(potionMeta.getBasePotionType().getPotionEffects());
        basePotionEffects.addAll(potionMeta.getCustomEffects());

        for (PotionEffect potionEffect : basePotionEffects)
        {
            if (potionEffect.getDuration() > 1)
            {
                potionMeta.addCustomEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration()+600, potionEffect.getAmplifier(),potionEffect.isAmbient(),potionEffect.hasParticles(),potionEffect.hasIcon(),potionEffect), true);
            }
        }

        // set the item meta
        e.getClickedInventory().getItem(e.getSlot()).setItemMeta(potionMeta);
    }
}
