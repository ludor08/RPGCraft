package org.rpg.rPGCraft.Traits.Passive.EmpoweredMixture;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Trait;

import java.util.ArrayList;
import java.util.List;

public class EmpoweredMixture_1 extends Trait
{

    public EmpoweredMixture_1(Main main)
    {
        // add the name and lore
        super("Empowered Mixture", "empowered mixture 1", ChatColor.AQUA, Material.POTION, false, main, List.of(
                ChatColor.AQUA.toString() + "   - The level of potions brewed by you is increased by one."
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
            potionMeta.addCustomEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration(), potionEffect.getAmplifier()+1,potionEffect.isAmbient(),potionEffect.hasParticles(),potionEffect.hasIcon(),potionEffect), true);
        }

        // set the item meta
        e.getClickedInventory().getItem(e.getSlot()).setItemMeta(potionMeta);
    }
}
