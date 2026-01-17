package org.rpg.rPGCraft.Traits.Active;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rpg.rPGCraft.Traits.ActiveTrait;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.RPGutils;

import java.util.List;

public class Kunai extends ActiveTrait
{
    NamespacedKey kunaiKey = new NamespacedKey(Main.GetInstance(), "kunai");

    NamespacedKey kunaiDamageKey = new NamespacedKey(Main.GetInstance(), "kunai_damage");

    public Kunai(Main main) {
        // add the name and lore
        super(ChatColor.RED + ChatColor.BOLD.toString() + "Kunai", "kunai", 5, Material.STONE_SWORD, false, List.of(
                ChatColor.AQUA.toString() + "   - Throw a kunai that does 1 damage and can carry other effects."
        ));


    }

    @Override
    public String GetInputSequence()
    {
        return "011";
    }

    @Override
    public void TriggerActiveEvent(Player player)
    {
        Arrow kunai = player.launchProjectile(Arrow.class);
        kunai.getPersistentDataContainer().set(kunaiKey, PersistentDataType.BOOLEAN, true);

        ((LivingEntity)kunai).getAttribute(Attribute.SCALE).addModifier(new AttributeModifier(new NamespacedKey(main, "kunai_size"), -0.9d, AttributeModifier.Operation.ADD_NUMBER));
        kunai.setDamage(1);
        kunai.setVelocity(kunai.getVelocity().multiply(2));
        kunai.setCustomName("Kunai");
        kunai.setGravity(false);

        if (player.getPersistentDataContainer().has(kunaiKey))
        {
            RPGutils.SetNamespacedKeyValue(kunai, poisonKunaiKey, player.getPersistentDataContainer().has(poisonKunaiKey));
            RPGutils.SetNamespacedKeyValue(kunai, poisonLevelKey, player.getPersistentDataContainer().get(poisonLevelKey, PersistentDataType.INTEGER));
            RPGutils.SetNamespacedKeyValue(kunai, poisonDurationKey, player.getPersistentDataContainer().get(poisonDurationKey, PersistentDataType.INTEGER));
        }
    }

    @Override
    public void OnShotProjectileHit(ProjectileHitEvent e)
    {
        if (e.getEntity().getPersistentDataContainer().has(kunaiKey))
        {
            if (e.getEntity().getPersistentDataContainer().get(poisonKunaiKey, PersistentDataType.BOOLEAN))
            {
                if (e.getHitEntity() != null)
                {
                    if (e.getHitEntity() instanceof LivingEntity livingHit) livingHit.addPotionEffect(new PotionEffect(PotionEffectType.POISON, e.getEntity().getPersistentDataContainer().get(poisonDurationKey, PersistentDataType.INTEGER),e.getEntity().getPersistentDataContainer().get(poisonLevelKey, PersistentDataType.INTEGER)-1));
                }
            }
        }
    }
}
