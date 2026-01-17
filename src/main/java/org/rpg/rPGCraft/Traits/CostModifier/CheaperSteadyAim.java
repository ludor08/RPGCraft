package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.Traits.CostModifierTrait;

import java.util.List;

public class CheaperSteadyAim extends CostModifierTrait
{
    public CheaperSteadyAim() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Cheaper Steady Aim", "cheaper steady aim", -15, "steady aim", Material.TIPPED_ARROW, List.of(
                ChatColor.AQUA.toString() + "   - Makes Steady Aim 15 mana cheaper."
        ));
    }
}
