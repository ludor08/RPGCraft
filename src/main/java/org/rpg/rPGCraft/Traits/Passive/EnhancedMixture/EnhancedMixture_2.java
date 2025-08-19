package org.rpg.rPGCraft.Traits.Passive.EnhancedMixture;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.ArrayList;
import java.util.List;

public class EnhancedMixture_2 extends Trait
{

    public EnhancedMixture_2(Main main)
    {
        // add the name and lore
        super("Enhanced Mixture", "enhanced mixture 2", ChatColor.AQUA, Material.POTION, false, main, List.of(
                ChatColor.AQUA.toString() + "   - The duration of potion brewed by you is increased",
                ChatColor.AQUA.toString() + "     by 60 seconds if the duration was already more than one second."
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
                potionMeta.addCustomEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration()+1200, potionEffect.getAmplifier(),potionEffect.isAmbient(),potionEffect.hasParticles(),potionEffect.hasIcon(),potionEffect), true);
            }
        }

        // set the item meta
        e.getClickedInventory().getItem(e.getSlot()).setItemMeta(potionMeta);
    }
}
