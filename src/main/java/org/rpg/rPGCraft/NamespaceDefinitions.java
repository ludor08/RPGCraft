package org.rpg.rPGCraft;

import org.bukkit.NamespacedKey;

public class NamespaceDefinitions
{
    private static final NamespacedKey currentTraitsFromCustomItemsKey = new NamespacedKey(Main.GetInstance(), "current_traits_from_custom_items");

    private static final NamespacedKey lastPartyInviteKey = new NamespacedKey(Main.GetInstance(), "last_party_invite");
    private static final NamespacedKey raceKey = new NamespacedKey(Main.GetInstance(), "race");
    private static final NamespacedKey subraceKey = new NamespacedKey(Main.GetInstance(), "subrace");
    private static final NamespacedKey classKey = new NamespacedKey(Main.GetInstance(), "class");
    private static final NamespacedKey UIKey = new NamespacedKey(Main.GetInstance(), "ui");
    private static final NamespacedKey traitKey = new NamespacedKey(Main.GetInstance(), "trait");
    private static final NamespacedKey levelKey = new NamespacedKey(Main.GetInstance(), "level");
    private static final NamespacedKey classXPKey = new NamespacedKey(Main.GetInstance(), "class_xp");
    private static final NamespacedKey treeProgressionKey = new NamespacedKey(Main.GetInstance(), "tree_progression");
    private static final NamespacedKey deactivatedNodesKey = new NamespacedKey(Main.GetInstance(), "deactivated_nodes");
    private static final NamespacedKey activeTraitInputKey = new NamespacedKey(Main.GetInstance(), "active_trait_input");
    private static final NamespacedKey manaKey = new NamespacedKey(Main.GetInstance(), "mana");
    private static final NamespacedKey manaRechargeSpeedKey = new NamespacedKey(Main.GetInstance(), "mana_recharge_speed");
    private static final NamespacedKey manaMaxKey = new NamespacedKey(Main.GetInstance(), "mana_max");

    private static final NamespacedKey weaponTypeKey = new NamespacedKey(Main.GetInstance(), "weapon_type");
    private static final NamespacedKey customItemKey = new NamespacedKey(Main.GetInstance(), "custom_item_id");
    private static final NamespacedKey customItemAttributeKey = new NamespacedKey(Main.GetInstance(), "custom_item_attribute");

    private static final NamespacedKey customMobKey = new NamespacedKey(Main.GetInstance(), "custom_mob");
    private static final NamespacedKey customEntityAttributeKey = new NamespacedKey(Main.GetInstance(), "custom_entity_attribute");
    private static final NamespacedKey levelStatModKey = new NamespacedKey(Main.GetInstance(), "level_hp_mod");
    private static final NamespacedKey customXpDropNumberKey = new NamespacedKey(Main.GetInstance(), "xp_drop_number");
    private static final NamespacedKey legendaryMobKey = new NamespacedKey(Main.GetInstance(), "legendary_mob");
    private static final NamespacedKey currentStateKey = new NamespacedKey(Main.GetInstance(), "current_state");
    private static final NamespacedKey legendaryMobAttributeKey = new NamespacedKey(Main.GetInstance(), "legendary_mob_attribute");

    public static NamespacedKey GetCurrentTraitsFromCustomItemsKey()
    {
        return currentTraitsFromCustomItemsKey;
    }

    public static NamespacedKey GetLegendaryMobAttributeKey()
    {
        return legendaryMobAttributeKey;
    }

    public static NamespacedKey GetLastPartyInviteKey()
    {
        return lastPartyInviteKey;
    }

    public static NamespacedKey GetRaceKey()
    {
        return raceKey;
    }

    public static NamespacedKey GetManaKey()
    {
        return manaKey;
    }

    public static NamespacedKey GetManaRechargeSpeedKey()
    {
        return manaRechargeSpeedKey;
    }

    public static NamespacedKey GetCurrentStateKey()
    {
        return currentStateKey;
    }

    public static NamespacedKey GetCustomEntityAttributeKey()
    {
        return customEntityAttributeKey;
    }

    public static NamespacedKey GetCustomXpDropNumberKey()
    {
        return customXpDropNumberKey;
    }

    public static NamespacedKey GetManaMaxKey()
    {
        return manaMaxKey;
    }

    public static NamespacedKey GetActiveTraitInputKey()
    {
        return activeTraitInputKey;
    }

    public static NamespacedKey GetSubraceKey()
    {
        return subraceKey;
    }

    public static NamespacedKey GetUIKey()
    {
        return UIKey;
    }

    public static NamespacedKey GetClassKey()
    {
        return classKey;
    }

    public static NamespacedKey GetTraitKey()
    {
        return traitKey;
    }

    public static NamespacedKey GetLevelKey()
    {
        return levelKey;
    }

    public static NamespacedKey GetCustomItemKey()
    {
        return customItemKey;
    }

    public static NamespacedKey GetLevelStatModKey()
    {
        return levelStatModKey;
    }

    public static NamespacedKey GetClassXPKey()
    {
        return classXPKey;
    }

    public static NamespacedKey GetWeaponTypeKey()
    {
        return weaponTypeKey;
    }

    public static NamespacedKey GetItemAttributeKey()
    {
        return customItemAttributeKey;
    }

    public static NamespacedKey GetCustomMobKey()
    {
        return customMobKey;
    }

    public static NamespacedKey GetTreeProgressionKey()
    {
        return treeProgressionKey;
    }

    public static NamespacedKey GetDeactivatedNodesKey()
    {
        return deactivatedNodesKey;
    }

    public static NamespacedKey GetLegendaryMobKey()
    {
        return legendaryMobKey;
    }
}
