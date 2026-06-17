package org.rpg.rPGCraft.Definitions;

import org.rpg.rPGCraft.Entities.EntityState;
import org.rpg.rPGCraft.Entities.EntityStates.Attacks.RepellingWaveState;
import org.rpg.rPGCraft.Entities.EntityStates.Attacks.ToxicCloudState;
import org.rpg.rPGCraft.Entities.EntityStates.Attacks.VoidBombDetonateState;
import org.rpg.rPGCraft.Entities.EntityStates.Attacks.VoidBombFallingState;
import org.rpg.rPGCraft.Entities.EntityStates.DiscardedSentientArmament.DiscardedSentientArmamentChasingState;
import org.rpg.rPGCraft.Entities.EntityStates.DiscardedSentientArmament.DiscardedSentientArmamentIdleState;
import org.rpg.rPGCraft.Entities.EntityStates.ZombieKing.*;

public enum EntityStates
{
    // values
    ZOMBIE_KING_SUMMONER(new ZombieKingSummonState()),
    WEAKEN_ZOMBIE_KING_IDLE(new WeakenZombieKingIdleState()),
    WEAKEN_ZOMBIE_KING_SUMMON_ZOMBIES(new WeakenZombieKingSummonZombiesState()),
    VOID_BOMB_FALLING(new VoidBombFallingState()),
    VOID_BOMB_DETONATE(new VoidBombDetonateState()),
    WEAKEN_ZOMBIE_KING_SHOOT_AT_PLAYER_START(new WeakenZombieKingShootAtPlayerStartState()),
    WEAKEN_ZOMBIE_KING_SHOOT_AT_PLAYER(new WeakenZombieKingShootAtPlayerState()),
    WEAKEN_ZOMBIE_KING_ENHANCE_ZOMBIES(new WeakenZombieKingEnhanceZombiesState()),
    UNBOUND_ZOMBIE_KING_SUMMON_ZOMBIES(new UnboundZombieKingSummonZombiesState()),
    UNBOUND_ZOMBIE_KING_TOXIN_RAIN(new UnboundZombieKingToxinRainState()),
    UNBOUND_ZOMBIE_KING_IDLE(new UnboundZombieKingIdleState()),
    DISCARDED_SENTIENT_ARMAMENT_CHASING(new DiscardedSentientArmamentChasingState()),
    DISCARDED_SENTIENT_ARMAMENT_IDLE(new DiscardedSentientArmamentIdleState()),
    REPELLING_WAVE_STATE(new RepellingWaveState()),
    TOXIN_CLOUD_STATE(new ToxicCloudState());

    // store the state
    private final EntityState state;

    private EntityStates(EntityState entityState)
    {
        this.state = entityState;
    }

    // getter
    public EntityState GetEntityState()
    {
        return state;
    }

    public static EntityState GetEntityStateByString(String id)
    {
        // loop through all EntityStates
        for (EntityStates stateEnum : EntityStates.values())
        {
            // if the id is the same, return that state
            if (stateEnum.state.GetStateID().equals(id))
            {
                return stateEnum.state;
            }
        }

        return null;
    }
}
