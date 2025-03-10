package net.corruptdog.cdm.gameasset;

import java.util.Set;

import net.corruptdog.cdm.main.CDmoveset;
import net.corruptdog.cdm.skill.Dodge.Dodge;
import net.corruptdog.cdm.skill.Dodge.EXYamatoDodge;
import net.corruptdog.cdm.skill.Dodge.SStep;
import net.corruptdog.cdm.skill.Passive.BloodWolf;
import net.corruptdog.cdm.skill.identity.FatalFlash;
import net.corruptdog.cdm.skill.weaponinnate.*;
import net.corruptdog.cdm.world.CDWeaponCapabilityPresets;
import net.corruptdog.cdm.world.CorruptWeaponCategories;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.skill.identity.RevelationSkill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.*;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = CDmoveset.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class CDSkills {
    public static Skill SWORD_SLASH;
    public static Skill DUAL_SLASH;
    public static Skill SPEAR_SLASH;
    public static Skill LETHAL_SLICING;
    public static Skill YAMATOSKILL;
    public static Skill GUARDPARRY;
    public static Skill DUAL_GREATSWORD_SKILL;
    public static Skill WIND_SLASH;
    public static Skill FATAL_DRAW_DASH;
    public static Skill BLADE_RUSH_FINISHER;
    public static Skill KATANASPSKILL;
    public static Skill BLOODWOLF;
    public static Skill BLADE_RUSH;
    public static Skill YAMATO_STEP;
    public static Skill EX_YAMATO_STEP;

    public static Skill SSTEP;
    public static Skill BSTEP;
    public static Skill WOLF_DODGE;
    public static Skill GREAT_TACHISKILL;
    public static Skill KATANASKILL;
    public static Skill SHILEDSLASH;
    public static Skill FATALFLASH;

    public static Skill YAMATO_PASSIVE;
//    public static Skill YAMATO_ART;
    public static Skill YAMATO_ATTACK;
    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build) {

        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker(CDmoveset.MOD_ID);
        YAMATO_STEP = modRegistry.build("yamato_step", Dodge::new, DodgeSkill.createDodgeBuilder().setAnimations(() -> CorruptAnimations.YAMATO_STEP_FORWARD, () -> CorruptAnimations.YAMATO_STEP_BACKWARD, () -> CorruptAnimations.YAMATO_STEP_LEFT, () -> CorruptAnimations.YAMATO_STEP_RIGHT));
        EX_YAMATO_STEP = modRegistry.build("ex_yamato_step", EXYamatoDodge::new, DodgeSkill.createDodgeBuilder().setAnimations(() -> CorruptAnimations.YAMATO_STEP_FORWARD, () -> CorruptAnimations.YAMATO_STEP_BACKWARD, () -> CorruptAnimations.YAMATO_STEP_LEFT, () -> CorruptAnimations.YAMATO_STEP_RIGHT,() -> CorruptAnimations.YAMATO_CHASE));

        SSTEP = modRegistry.build("sstep", Dodge::new, DodgeSkill.createDodgeBuilder().setAnimations(() -> CorruptAnimations.SSTEP_FORWARD, () -> CorruptAnimations.SSTEP_BACKWARD, () -> CorruptAnimations.SSTEP_LEFT, () -> CorruptAnimations.SSTEP_RIGHT));
        GUARDPARRY = modRegistry.build("guardparry", GuardParrySkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION_INFINITE));
        BLOODWOLF = modRegistry.build( "bloodwolf", BloodWolf::new, PassiveSkill.createIdentityBuilder());
        GREAT_TACHISKILL = modRegistry.build( "greeat_tachiskill", DualTchiSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        YAMATOSKILL = modRegistry.build( "yamatoskill", YamatoSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        BLADE_RUSH = modRegistry.build( "blade_rush", DualDaggerSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        SHILEDSLASH = modRegistry.build( "shiled_slahs", ShiledSlash::new, WeaponInnateSkill.createWeaponInnateBuilder());
        BSTEP = modRegistry.build("bstep", Dodge::new, DodgeSkill.createDodgeBuilder().setAnimations(() -> CorruptAnimations.STEP_FORWARD, () -> CorruptAnimations.STEP_BACKWARD, () -> CorruptAnimations.STEP_LEFT, () -> CorruptAnimations.STEP_RIGHT));
        FATALFLASH = modRegistry.build("fatalfalsh",  FatalFlash::new, FatalFlash.createRevelationSkillBuilder().addAvailableWeaponCategory(CorruptWeaponCategories.KATANA, CapabilityItem.WeaponCategories.UCHIGATANA, CorruptWeaponCategories.S_LONGSWORD,CapabilityItem.WeaponCategories.LONGSWORD,CorruptWeaponCategories.S_TACHI,CapabilityItem.WeaponCategories.TACHI));

//        YAMATO_ATTACK = modRegistry.build("yamato_attack",YamatoAttack::new,YamatoAttack.createBuilder());
        YAMATO_PASSIVE = modRegistry.build("yamato_passive",YamatoPassive::new,Skill.createBuilder().setCategory(SkillCategories.WEAPON_PASSIVE));


        SkillBuildEvent.ModRegistryWorker registryWorker = build.createRegistryWorker(CDmoveset.MOD_ID);
        WOLF_DODGE = registryWorker.build("wolf_dodge", SStep::new, SStep.createDodgeBuilder()
                .setAnimations1(
                        () -> CorruptAnimations.WOLFDODGE_FORWARD,
                        () -> CorruptAnimations.WOLFDODGE_BACKWARD,
                        () -> CorruptAnimations.WOLFDODGE_LEFT,
                        () -> CorruptAnimations.WOLFDODGE_RIGHT
                )
                .setAnimations2(
                        () -> CorruptAnimations.ROLL_FORWARD,
                        () -> CorruptAnimations.ROLL_BACKWARD,
                        () -> CorruptAnimations.ROLL_LEFT,
                        () -> CorruptAnimations.ROLL_RIGHT
                )
                .setAnimations3(
                        () -> CorruptAnimations.WOLFDODGE_FORWARD,
                        () -> CorruptAnimations.WOLFDODGE_BACKWARD,
                        () -> CorruptAnimations.WOLFDODGE_LEFT,
                        () -> CorruptAnimations.WOLFDODGE_RIGHT
                )
                .setPerfectAnimations(
                        () -> CorruptAnimations.STEP_FORWARD,
                        () -> CorruptAnimations.STEP_BACKWARD,
                        () -> Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT,
                        () -> Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT
                )
        );
        WeaponInnateSkill KatanaspSkill = modRegistry.build("katanaspskill", TachiDual::new, WeaponInnateSkill.createWeaponInnateBuilder());
        KatanaspSkill.newProperty()
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(15.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1F))
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2F))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        KATANASPSKILL = KatanaspSkill;

        WeaponInnateSkill KatanaSkill = modRegistry.build("katanaskill", KatanaSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        KatanaSkill.newProperty()
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(15.0F))
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3F))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        KATANASKILL = KatanaSkill;

        WeaponInnateSkill wind_slash = modRegistry.build("wind_slash", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) CorruptAnimations.WIND_SLASH));
        wind_slash.newProperty()
                .newProperty()
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.4F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        WIND_SLASH = wind_slash;

        WeaponInnateSkill fatal_draw = modRegistry.build("fatal_draw", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) CorruptAnimations.FATAL_DRAW));
        fatal_draw.newProperty()
                .newProperty()
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.4F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        FATAL_DRAW_DASH = fatal_draw;

        WeaponInnateSkill greatsword_dual_skill = modRegistry.build("greatsword_dual_skill", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) CorruptAnimations.DUAL_GREATSWORD_SKILL));
        greatsword_dual_skill.newProperty()
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        DUAL_GREATSWORD_SKILL = greatsword_dual_skill;

        WeaponInnateSkill LethalSlicingSkill = modRegistry.build("lethalslicing", LethalSlicingSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        LethalSlicingSkill.newProperty()
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(5.0F))
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(6))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(0.0F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE))
                .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(6))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(50.0F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE))
                .registerPropertiesToAnimation();
        LETHAL_SLICING = LethalSlicingSkill;

        WeaponInnateSkill dual_slash = modRegistry.build("dual_slash", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) CorruptAnimations.DUAL_SLASH));
        dual_slash
                .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        DUAL_SLASH = dual_slash;


        WeaponInnateSkill spearslash = modRegistry.build("spearslash", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) CorruptAnimations.SPEAR_SLASH));
        spearslash
                .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        SPEAR_SLASH = spearslash;

        WeaponInnateSkill swordslash = modRegistry.build("sword_slash", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) CorruptAnimations.SWORD_SLASH));
        swordslash.newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        SWORD_SLASH = swordslash;

        WeaponInnateSkill blade_rush_finisher = modRegistry.build("blade_rush_finisher", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) CorruptAnimations.BLADE_RUSH_FINISHER));
        blade_rush_finisher.newProperty()
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        BLADE_RUSH_FINISHER = blade_rush_finisher;

    }

    public CDSkills(){}
}


