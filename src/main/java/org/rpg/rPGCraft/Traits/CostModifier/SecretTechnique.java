package org.rpg.rPGCraft.Traits.CostModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Traits.CostModifierTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class SecretTechnique extends CostModifierTrait
{
    NamespacedKey secretTechniqueKey = new NamespacedKey(Main.GetInstance(), "secret_technique");

    public SecretTechnique() {
        // add the name and lore
        super(ChatColor.AQUA + ChatColor.BOLD.toString() + "Secret Technique", "secret technique", 5, "smoke bomb", Material.GUNPOWDER, List.of(
                ChatColor.AQUA.toString() + "   - Makes Smoke Bomb cost 5 more mana.",
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
