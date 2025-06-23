package org.rpg.rPGCraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Trait
{
    // name and lore of this trait
    public String name;
    public List<String> lore;

    // type of buffs
    boolean tickTrait;

    public Trait(String name, boolean tickTrait, List<String> lore)
    {
        this.name = name;
        this.lore = lore;
        this.tickTrait = tickTrait;
    }

    // get lore as a item lore friendly String List
    public List<String> getTraitLore()
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
