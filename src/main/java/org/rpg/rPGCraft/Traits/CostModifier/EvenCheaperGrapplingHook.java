package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Traits.CostModifierTrait;

import java.util.List;

public class EvenCheaperGrapplingHook extends CostModifierTrait
{
    public EvenCheaperGrapplingHook() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Even Cheaper Grappling Hook", "even cheaper grappling hook", -10, "grappling hook arrow", Material.LEAD, List.of(
                ChatColor.AQUA.toString() + "   - Makes Grappling Hook 10 mana cheaper."
        ));
    }
}
