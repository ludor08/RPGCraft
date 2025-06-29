package org.rpg.rPGCraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public abstract class Trait
{
    // name and lore of this trait
    public String name;
    public ChatColor nameColor;
    public Material iconMaterial;
    public List<String> lore;

    // type of buffs
    boolean tickTrait;

    // main
    Main main;

    public Trait(String name, ChatColor nameColor, Material iconMaterial, boolean tickTrait, Main main, List<String> lore)
    {
        this.name = name;
        this.lore = lore;
        this.nameColor = nameColor;
        this.iconMaterial = iconMaterial;

        this.main = main;

        this.tickTrait = tickTrait;
    }

    public ItemStack GetTraitIcon()
    {
        // generates the icon for this trait
        ItemStack traitIcon = new ItemStack(iconMaterial);
        ItemMeta traitIconMeta = traitIcon.getItemMeta();

        traitIconMeta.setDisplayName(nameColor.toString() + ChatColor.BOLD + name);

        // add the trait
        traitIconMeta.getPersistentDataContainer().set(main.GetTraitKey(), PersistentDataType.STRING, name);

        // add the description
        List<String> lore = GetTraitLore();
        lore.removeFirst();

        traitIconMeta.setLore(lore);

        // set the item meta
        traitIcon.setItemMeta(traitIconMeta);

        // return the icon
        return traitIcon;
    }

    // get lore as a item lore friendly String List
    public List<String> GetTraitLore()
    {
        List<String> itemLore = new ArrayList<>();

        // add the name to the lore
        itemLore.add(ChatColor.AQUA.toString() + "- " + name + " :");

        // go through every lore
        for (int i = 0; i < lore.size(); i++)
        {
            // add the lore
            itemLore.add(lore.get(i));
        }

        // return item lore
        return itemLore;
    }

    // Add the buffs that need to be applied when you gain this trait
    public abstract void OnGainTraitBuff(Player player);

    // Cleanup the buffs that need to be removed when you lose this trait
    public abstract void OnRemoveTraitBuff(Player player);

    // Re add buffs that you lose when you die
    public abstract void OnRespawnBuffs(PlayerRespawnEvent e);

    // Re add buffs that you lose when you die
    public abstract void OnTakeDamage(EntityDamageEvent e);

    // Re add buffs that you lose when you die
    public abstract void OnDealDamage(EntityDamageByEntityEvent e);

    // Re add buffs that you lose when you die
    public abstract void OnFoodLevelChange(FoodLevelChangeEvent e);


}
