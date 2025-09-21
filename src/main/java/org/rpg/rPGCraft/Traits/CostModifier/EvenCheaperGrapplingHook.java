package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.rpg.rPGCraft.CostModifierTrait;
import org.rpg.rPGCraft.Main;

import java.util.List;

public class EvenCheaperGrapplingHook extends CostModifierTrait
{
    public EvenCheaperGrapplingHook(Main main) {
        // add the name and lore
        super("Even Cheaper Grappling Hook", "even cheaper grappling hook", -10, "grappling hook arrow", ChatColor.AQUA, Material.LEAD, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Grappling Hook 10 mana cheaper."
        ));
    }
}
