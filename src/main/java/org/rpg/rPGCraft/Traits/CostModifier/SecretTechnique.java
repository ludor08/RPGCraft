package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.CostModifierTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class SecretTechnique extends CostModifierTrait
{
    NamespacedKey secretTechniqueKey = new NamespacedKey(main, "secret_technique");

    public SecretTechnique(Main main) {
        // add the name and lore
        super("Secret Technique", "secret technique", -15, "smoke bomb", ChatColor.AQUA, Material.GUNPOWDER, main, List.of(
                ChatColor.AQUA.toString() + "   - Makes Smoke Bomb 15 mana cheaper.",
                ChatColor.AQUA.toString() + "   - Using Smoke Bomb while sneaking will teleport you where you're looking."
        ));
    }

    @Override
    public void OnRemoveTraitBuff(Player player)
    {
        RPGutils.RemoveNamespacedKey(player, secretTechniqueKey);
    }

    @Override
    public void OnGainTraitBuff(Player player)
    {
        RPGutils.SetNamespacedKeyValue(player, secretTechniqueKey, true);
    }
}
