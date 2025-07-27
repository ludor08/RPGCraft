package org.rpg.rPGCraft;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        AttributeModifier attributeModifierWithKey = null;

        for (AttributeModifier modifier : Objects.requireNonNull(player.getAttribute(attribute)).getModifiers())
        {
            // if the player has an attribute modifier with the same key
            if (modifier.getKey().equals(attributeModifier.getKey()))
            {
                attributeModifierWithKey = modifier;
            }
        }

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

    public void SafeAttributeRemove(Attribute attribute, AttributeModifier attributeModifier, Player player)
    {
        AttributeModifier attributeModifierWithKey = null;

        for (AttributeModifier modifier : Objects.requireNonNull(player.getAttribute(attribute)).getModifiers())
        {
            // if the player has an attribute modifier with the same key
            if (modifier.getKey().equals(attributeModifier.getKey()))
            {
                attributeModifierWithKey = modifier;
            }
        }

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

    // Add the buffs that need to be applied when you gain this trait
    public abstract void OnGainTraitBuff(Player player);

    // Add the buffs that need to be applied when you gain this trait
    public abstract void OnTick(Player player);

    // Cleanup the buffs that need to be removed when you lose this trait
    public abstract void OnRemoveTraitBuff(Player player);

    // Re add buffs that you lose when you die
    public abstract void OnRespawnBuffs(PlayerRespawnEvent e);

    public abstract void OnTakeDamage(EntityDamageEvent e);

    public abstract void OnDealDamage(EntityDamageByEntityEvent e);

    public abstract void OnFoodLevelChange(FoodLevelChangeEvent e);


}
