package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.CostModifierTrait;
import org.rpg.rPGCraft.Main;

import java.util.List;

public class CheaperSteadyAim extends CostModifierTrait
{
    public CheaperSteadyAim(Main main) {
        // add the name and lore
        super("Cheaper Steady Aim", "cheaper steady aim", -15, "steady aim", ChatColor.AQUA, Material.TIPPED_ARROW, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Steady Aim 15 mana cheaper."
        ));
    }
}
