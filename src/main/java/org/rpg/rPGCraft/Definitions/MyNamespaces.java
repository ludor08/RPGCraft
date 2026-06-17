package org.rpg.rPGCraft.Definitions;

import org.bukkit.NamespacedKey;
import org.rpg.rPGCraft.Main;

public enum MyNamespaces
{
    /// values
    // player keys
    LAST_PARTY_INVITE(new NamespacedKey(Main.GetInstance(), "last_party_invite")),
    RACE(new NamespacedKey(Main.GetInstance(), "race")),
    SUBRACE(new NamespacedKey(Main.GetInstance(), "subrace")),
    CLASS(new NamespacedKey(Main.GetInstance(), "class")),
    UI(new NamespacedKey(Main.GetInstance(), "ui")),
    TRAIT(new NamespacedKey(Main.GetInstance(), "trait")),
    LEVEL(new NamespacedKey(Main.GetInstance(), "level")),
    CLASS_XP(new NamespacedKey(Main.GetInstance(), "class_xp")),
    TREE_PROGRESSION(new NamespacedKey(Main.GetInstance(), "tree_progression")),
    DEACTIVATED_NODES(new NamespacedKey(Main.GetInstance(), "deactivated_nodes")),
    ACTIVE_TRAIT_INPUT(new NamespacedKey(Main.GetInstance(), "active_trait_input")),
    MANA(new NamespacedKey(Main.GetInstance(), "mana")),
    MANA_RECHARGE_SPEED(new NamespacedKey(Main.GetInstance(), "mana_recharge_speed")),
    MANA_MAX(new NamespacedKey(Main.GetInstance(), "mana_max")),

    // item keys
    CURRENT_TRAITS_FROM_CUSTOM_ITEMS(new NamespacedKey(Main.GetInstance(), "current_traits_from_custom_items")),
    WEAPON_TYPE(new NamespacedKey(Main.GetInstance(), "weapon_type")),
    CUSTOM_ITEM(new NamespacedKey(Main.GetInstance(), "custom_item_id")),
    CUSTOM_ITEM_ATTRIBUTE(new NamespacedKey(Main.GetInstance(), "custom_item_attribute")),

    // entity keys
    CUSTOM_MOB(new NamespacedKey(Main.GetInstance(), "custom_mob")),
    CUSTOM_ENTITY_ATTRIBUTE(new NamespacedKey(Main.GetInstance(), "custom_entity_attribute")),
    LEVEL_HP_MOD(new NamespacedKey(Main.GetInstance(), "level_hp_mod")),
    CUSTOM_XP_DROP_NUMBER(new NamespacedKey(Main.GetInstance(), "xp_drop_number")),
    LEGENDARY_MOB(new NamespacedKey(Main.GetInstance(), "legendary_mob")),
    CURRENT_STATE(new NamespacedKey(Main.GetInstance(), "current_state")),
    LEGENDARY_MOB_ATTRIBUTE(new NamespacedKey(Main.GetInstance(), "legendary_mob_attribute")),
    TARGETING(new NamespacedKey(Main.GetInstance(), "targeting")),
    TARGET_COOLDOWN(new NamespacedKey(Main.GetInstance(), "target_cooldown")),
    LAST_SEEN_TARGET(new NamespacedKey(Main.GetInstance(), "last_seen_target")),
    ANIMATION(new NamespacedKey(Main.GetInstance(), "animation")),
    DEFAULT_ANIMATION(new NamespacedKey(Main.GetInstance(), "default_animation")),
    ANIMATION_FRAME(new NamespacedKey(Main.GetInstance(), "animation_frame")),
    DISPLAY_ENTITY(new NamespacedKey(Main.GetInstance(), "display_entity")),

    // marker keys
    ARENA_MARKER(new NamespacedKey(Main.GetInstance(), "arena_marker")),
    ARENA_MARKER_FLOOR_START_OFFSET_X(new NamespacedKey(Main.GetInstance(), "arena_marker_floor_start_offset_x")),
    ARENA_MARKER_FLOOR_START_OFFSET_Y(new NamespacedKey(Main.GetInstance(), "arena_marker_floor_start_offset_y")),
    ARENA_MARKER_FLOOR_START_OFFSET_Z(new NamespacedKey(Main.GetInstance(), "arena_marker_floor_start_offset_z")),
    ARENA_MARKER_FLOOR_WIDTH(new NamespacedKey(Main.GetInstance(), "arena_marker_floor_width")),
    ARENA_MARKER_FLOOR_LENGTH(new NamespacedKey(Main.GetInstance(), "arena_marker_floor_length"));

    // store the namespacedKey
    private NamespacedKey namespacedKey;

    private MyNamespaces(NamespacedKey namespacedKey)
    {
        this.namespacedKey = namespacedKey;
    }

    // getter
    public NamespacedKey GetNamespacedKey()
    {
        return namespacedKey;
    }
}
