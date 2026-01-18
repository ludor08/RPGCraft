package org.rpg.rPGCraft.CustomItemComponents;

import org.bukkit.inventory.EquipmentSlot;
import org.rpg.rPGCraft.Traits.Trait;

public class ItemEnhancement
{
    private ItemEnhancementUseCases useCase;
    private Trait trait;

    public ItemEnhancement(ItemEnhancementUseCases useCase, Trait trait)
    {
        this.useCase = useCase;
        this.trait = trait;
    }

    public static boolean IsEnchantmentApplicable(ItemEnhancement itemEnhancement, EquipmentSlot equipmentSlot)
    {
        switch (itemEnhancement.useCase)
        {
            case ItemEnhancementUseCases.Always :
                return true;

            case ItemEnhancementUseCases.InMainHand:
                if (equipmentSlot == EquipmentSlot.HAND)
                {
                    return true;
                }
                break;

            case ItemEnhancementUseCases.InOffHand:
                if (equipmentSlot == EquipmentSlot.OFF_HAND)
                {
                    return true;
                }
                break;

            case ItemEnhancementUseCases.InAnyHand:
                if (equipmentSlot != null && equipmentSlot.isHand())
                {
                    return true;
                }
                break;

            case ItemEnhancementUseCases.OnHead:
                if (equipmentSlot == EquipmentSlot.HEAD)
                {
                    return true;
                }
                break;

            case ItemEnhancementUseCases.OnChest:
                if (equipmentSlot == EquipmentSlot.CHEST)
                {
                    return true;
                }
                break;

            case ItemEnhancementUseCases.OnLegs:
                if (equipmentSlot == EquipmentSlot.LEGS)
                {
                    return true;
                }
                break;

            case ItemEnhancementUseCases.OnFeet:
                if (equipmentSlot == EquipmentSlot.FEET)
                {
                    return true;
                }
                break;

            case ItemEnhancementUseCases.Warren :
                if (equipmentSlot != null && equipmentSlot.isArmor())
                {
                    return true;
                }
                break;
        }

        return false;
    }

    public ItemEnhancementUseCases GetUseCase()
    {
        return useCase;
    }

    public Trait GetTrait()
    {
        return trait;
    }
}

