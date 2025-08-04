package org.rpg.rPGCraft;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3d;

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

    public void SafeAttributeAdd(Attribute attribute, AttributeModifier attributeModifier, Player player)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            // remove the old attributeModifier
            player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

            // create a new AttributeModifier with the same key and the amount added together and add it
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), attributeModifierWithKey.getAmount() + attributeModifier.getAmount(), AttributeModifier.Operation.ADD_NUMBER));
        }
        else
        {
            // add the attribute modifier normally
            player.getAttribute(attribute).addModifier(attributeModifier);
        }
    }

    public void SafeAttributeAdd(Attribute attribute, AttributeModifier attributeModifier, Player player, float max)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            // remove the old attributeModifier
            player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

            // create a new AttributeModifier with the same key and the amount added together and add it
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.min(attributeModifierWithKey.getAmount() + attributeModifier.getAmount(), max), AttributeModifier.Operation.ADD_NUMBER));
        }
        else
        {
            // add the attribute modifier normally
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.min(attributeModifier.getAmount(), max), AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    public void SafeAttributeAdd(Attribute attribute, AttributeModifier attributeModifier, Player player, int max)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            // remove the old attributeModifier
            player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

            // create a new AttributeModifier with the same key and the amount added together and add it
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.min(attributeModifierWithKey.getAmount() + attributeModifier.getAmount(), max), AttributeModifier.Operation.ADD_NUMBER));
        }
        else
        {
            // add the attribute modifier normally
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.min(attributeModifier.getAmount(), max), AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    public void SafeAttributeRemove(Attribute attribute, AttributeModifier attributeModifier, Player player)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            if (!attributeModifierWithKey.equals(attributeModifier))
            {
                // remove the old attributeModifier
                player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

                // create a new AttributeModifier with the same key and the amount added together and add it
                player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), attributeModifierWithKey.getAmount() - attributeModifier.getAmount(), AttributeModifier.Operation.ADD_NUMBER));
            }
            else
            {
                // add the attribute modifier normally
                player.getAttribute(attribute).removeModifier(attributeModifier);
            }
        }
    }

    public void SafeAttributeRemove(Attribute attribute, AttributeModifier attributeModifier, Player player, float min)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            // remove the old attributeModifier
            player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

            // create a new AttributeModifier with the same key and the amount added together and add it
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.max(attributeModifierWithKey.getAmount() - attributeModifier.getAmount(), min), AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    public void SafeAttributeRemove(Attribute attribute, AttributeModifier attributeModifier, Player player, int min)
    {
        AttributeModifier attributeModifierWithKey = player.getAttribute(attribute).getModifier(attributeModifier.getKey());

        if (attributeModifierWithKey != null)
        {
            // remove the old attributeModifier
            player.getAttribute(attribute).removeModifier(attributeModifierWithKey);

            // create a new AttributeModifier with the same key and the amount added together and add it
            player.getAttribute(attribute).addModifier(new AttributeModifier(attributeModifier.getKey(), Math.max(attributeModifierWithKey.getAmount() - attributeModifier.getAmount(), min), AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    public Location Recast(int numberOfChecks, Vector3d direction, Location location, boolean isStoppedBySolidBlocks)
    {
        Vector3d position = new Vector3d(location.getX(), location.getY(), location.getZ());

        for (int i = 0; i < numberOfChecks; i++)
        {
            // update the position
            position.add(direction); // may not be actually updating the variable

            // check if the block at said position is solid and the recast is stopped by solid blocks
            if (new Location(location.getWorld(), position.x, position.y, position.z).getBlock().isSolid() && isStoppedBySolidBlocks)
            {
                // break out of the loop
                break;
            }
        }

        return new Location(location.getWorld(), position.x, position.y, position.z);
    }

    public Location Recast(int numberOfChecks, Vector3d direction, Location location, boolean isStoppedBySolidBlocks, Particle particle, int numberOfParticles)
    {
        Vector3d position = new Vector3d(location.getX(), location.getY(), location.getZ());

        for (int i = 0; i < numberOfChecks; i++)
        {
            // update the position
            position.add(direction); // may not be actually updating the variable

            // spawn the particle
            location.getWorld().spawnParticle(particle, location, numberOfParticles);

            for (Player player : Bukkit.getOnlinePlayers())
            {
                player.sendMessage(position + "");
            }

            // check if the block at said position is solid and the recast is stopped by solid blocks
            if (new Location(location.getWorld(), position.x, position.y, position.z).getBlock().isSolid() && isStoppedBySolidBlocks)
            {
                // break out of the loop
                break;
            }
        }

        return new Location(location.getWorld(), position.x, position.y, position.z);
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

    public void OnFoodLevelChange(FoodLevelChangeEvent e)
    {

    }

    public void OnSneak(PlayerToggleSneakEvent e)
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

    public void OnTargeted(EntityTargetEvent e)
    {

    }

}
