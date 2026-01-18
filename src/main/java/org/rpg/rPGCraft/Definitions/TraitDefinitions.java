package org.rpg.rPGCraft.Definitions;

import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.Traits.Trait;
import org.rpg.rPGCraft.Traits.Active.*;
import org.rpg.rPGCraft.Traits.CostModifier.*;
import org.rpg.rPGCraft.Traits.Passive.*;

import java.util.HashMap;

public class TraitDefinitions
{
    private static HashMap<String, Trait> traitIdMap;

    private static void AddTraitToMap(Trait trait)
    {
        traitIdMap.put(trait.name_id, trait);
    }

    public static void Initialize()
    {
        traitIdMap = new HashMap<String, Trait>();

        // active
        AddTraitToMap(new AuraOfSiphoning());
        AddTraitToMap(new BreathOfTheDragons());
        AddTraitToMap(new ConjureLightning());
        AddTraitToMap(new Dash());
        AddTraitToMap(new GiantsLeap());
        AddTraitToMap(new GrapplingHookArrow());
        AddTraitToMap(new Kunai());
        AddTraitToMap(new MendMinorWounds());
        AddTraitToMap(new OathOfRage());
        AddTraitToMap(new PinShot());
        AddTraitToMap(new PowerOfTheOakQueen());
        AddTraitToMap(new SmokeBomb());
        AddTraitToMap(new SpinAttack());
        AddTraitToMap(new SteadyAim());

        // cost modifier
        AddTraitToMap(new BloodlustRage());
        AddTraitToMap(new CheaperGrapplingHook());
        AddTraitToMap(new CheaperSteadyAim());
        AddTraitToMap(new EvenCheaperGrapplingHook());
        AddTraitToMap(new PoisonKunai());
        AddTraitToMap(new SecretTechnique());
        AddTraitToMap(new PowerfulKunai());

        // passive
        AddTraitToMap(new AbnormalDiet_Berries());
        AddTraitToMap(new AbnormalDiet_Death());
        AddTraitToMap(new AbnormalDiet_Raw_Fish());
        AddTraitToMap(new AbnormalDiet_Souls());
        AddTraitToMap(new Amphibious());
        AddTraitToMap(new AnimalAgility());
        AddTraitToMap(new AntiGravityArrow());
        AddTraitToMap(new Arthropod_trait());
        AddTraitToMap(new AssassinsMixture());
        AddTraitToMap(new BackStab());
        AddTraitToMap(new BetterBackStab());
        AddTraitToMap(new BetterCombo());
        AddTraitToMap(new BetterHeadshots());
        AddTraitToMap(new BetterSmokeBomb());
        AddTraitToMap(new BloodyRetreat());
        AddTraitToMap(new BoostedHealing_speed());
        AddTraitToMap(new BullCharge());
        AddTraitToMap(new Charge());
        AddTraitToMap(new Claws());
        AddTraitToMap(new CleavingImpact());
        AddTraitToMap(new Cleve());
        AddTraitToMap(new Combo());
        AddTraitToMap(new CorrectiveWindResistance());
        AddTraitToMap(new DiamondSkin());
        AddTraitToMap(new Dodge());
        AddTraitToMap(new EmpoweredMixture_1());
        AddTraitToMap(new EnhancedMixture_1());
        AddTraitToMap(new EnhancedMixture_2());
        AddTraitToMap(new Exoskeleton());
        AddTraitToMap(new ExtraAgilityJumping());
        AddTraitToMap(new ExtraAgilityRunning());
        AddTraitToMap(new ExtremeDiet_Meat());
        AddTraitToMap(new FelineAgility());
        AddTraitToMap(new FlameCharge());
        AddTraitToMap(new FlashOfOak());
        AddTraitToMap(new Fur());
        AddTraitToMap(new GiantsImpact());
        AddTraitToMap(new GreaterCapacity_1());
        AddTraitToMap(new GreaterCapacity_2());
        AddTraitToMap(new GreaterMinorHealing());
        AddTraitToMap(new GreenAndShiny());
        AddTraitToMap(new Headshot());
        AddTraitToMap(new HighPowerWindShot());
        AddTraitToMap(new IncineratingImpact());
        AddTraitToMap(new IronOak());
        AddTraitToMap(new Lacerate());
        AddTraitToMap(new LaserShot());
        AddTraitToMap(new LingeringRage());
        AddTraitToMap(new LungingDash());
        AddTraitToMap(new ManaRegainSpeed());
        AddTraitToMap(new ManyLegs());
        AddTraitToMap(new NightVision());
        AddTraitToMap(new NineLives());
        AddTraitToMap(new Pack());
        AddTraitToMap(new Piezoelectric());
        AddTraitToMap(new Pincers());
        AddTraitToMap(new PotentRebuke());
        AddTraitToMap(new Pounce());
        AddTraitToMap(new PowerfulSwings());
        AddTraitToMap(new PowerShot());
        AddTraitToMap(new RebukeOfTheFlame());
        AddTraitToMap(new ReflectingBreath());
        AddTraitToMap(new Regeneration());
        AddTraitToMap(new RejuvenatingRage());
        AddTraitToMap(new Ricochet());
        AddTraitToMap(new SandCrawler());
        AddTraitToMap(new ShieldCharge());
        AddTraitToMap(new Size_Change_Small());
        AddTraitToMap(new Size_Change_Tiny());
        AddTraitToMap(new SneakAttack());
        AddTraitToMap(new Squashable());
        AddTraitToMap(new Stealth());
        AddTraitToMap(new SuperHardenedSkin());
        AddTraitToMap(new SupernaturalGrowth());
        AddTraitToMap(new TeleportingGrapple());
        AddTraitToMap(new ThickSkin());
        AddTraitToMap(new ToxicBite());
        AddTraitToMap(new ToxicSpores());
        AddTraitToMap(new Vitality_1());
        AddTraitToMap(new Vitality_2());
        AddTraitToMap(new WeakeningSiphon());
        AddTraitToMap(new WeAreOne());
        AddTraitToMap(new WindShot());
        AddTraitToMap(new Wings());
        AddTraitToMap(new CancelMainHandBlockInteraction());
        AddTraitToMap(new CancelOffHandBlockInteraction());
        AddTraitToMap(new Mithridatism());
        AddTraitToMap(new CancelMainHandEntityInteraction());
        AddTraitToMap(new CancelOffHandEntityInteraction());
        AddTraitToMap(new GracefulStep());
    }

    public static Trait GetTraitByID(String name_id)
    {
        if (traitIdMap.containsKey(name_id))
        {
            return traitIdMap.get(name_id);
        }
        else
        {
            Main.GetInstance().getLogger().warning("key \"" + name_id + "\" is not contained in traitIdMap.");
            return null;
        }
    }
}
