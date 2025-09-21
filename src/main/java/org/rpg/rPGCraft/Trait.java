package org.rpg.rPGCraft;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public abstract class Trait
{
    // name and lore of this trait
    public String name;
    public String name_id;
    public ChatColor nameColor;
    public Material iconMaterial;
    public List<String> lore;

    // type of buffs
    public boolean tickTrait;

    // main
    public Main main;

    public Trait(String name, String name_id, ChatColor nameColor, Material iconMaterial, boolean tickTrait, Main main, List<String> lore)
    {
        this.name = name;
        this.name_id = name_id;
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

    // get lore as an item lore friendly String List
    public List<String> GetTraitLore()
    {
        List<String> itemLore = new ArrayList<>();

        // add the name to the lore
        itemLore.add(ChatColor.AQUA.toString() + "- " + name + " :");

        // if this is an Active trait
        if (this instanceof ActiveTrait activeTrait)
        {
            itemLore.add(ChatColor.BLUE + "Input Sequence : " +  main.statSheetManager.GenerateInputSequenceActionBar(activeTrait.GetInputSequence(), ChatColor.BLUE));
            itemLore.add(ChatColor.BLUE + "Cost : " + activeTrait.GetCost());
            itemLore.add(" ");
            itemLore.add(ChatColor.AQUA + "On Activation :");
        }

        // add the lore
        itemLore.addAll(lore);

        // return item lore
        return itemLore;
    }

    // the buffs that need to be applied when you gain this trait
    public void OnGainTraitBuff(Player player)
    {

    }

    // the buffs that need to be applied when you gain this trait
    public void OnTick(Player player)
    {

    }

    // Cleanup the buffs that need to be removed when you lose this trait
    public void OnRemoveTraitBuff(Player player)
    {

    }

    public void OnRespawnBuffs(PlayerRespawnEvent e)
    {

    }

    public void OnTakeDamage(EntityDamageEvent e)
    {

    }

    public void OnDealDamage(EntityDamageByEntityEvent e)
    {

    }

    public void OnShotProjectileHit(ProjectileHitEvent e)
    {

    }

    public void OnLaunchProjectile(ProjectileLaunchEvent e)
    {

    }

    public void OnFoodLevelChange(FoodLevelChangeEvent e)
    {

    }

    public void OnToggleSneak(PlayerToggleSneakEvent e)
    {

    }

    public void OnJump(PlayerJumpEvent e)
    {

    }

    public void OnPlayerItemConsume(PlayerItemConsumeEvent e)
    {

    }

    public void OnPickUpXP(PlayerPickupExperienceEvent e)
    {

    }

    public void OnClick(PlayerInteractEvent e)
    {

    }

    public void OnInventoryClick(InventoryClickEvent e)
    {

    }

    public void OnTakePotionFromBrewingStand(InventoryClickEvent e)
    {

    }

    public void OnTargeted(EntityTargetEvent e)
    {

    }

    public void OnGainEffect(EntityPotionEffectEvent e)
    {

    }
}
