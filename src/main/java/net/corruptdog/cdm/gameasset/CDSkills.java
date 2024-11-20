package net.corruptdog.cdm.gameasset;

import java.util.Set;

import net.corruptdog.cdm.main.CDmoveset;
import net.corruptdog.cdm.skill.Dodge.SStep;
import net.corruptdog.cdm.skill.Passive.BloodWolf;
import net.corruptdog.cdm.skill.weaponinnate.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.skill.dodge.StepSkill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.*;
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
    public static Skill CDPARRY;
    public static Skill GUARDPARRY;
    public static Skill DENGLONG;
    public static Skill DUAL_GREATSWORD_SKILL;
    public static Skill WIND_SLASH;
    public static Skill FATAL_DRAW_DASH;
    public static Skill BLADE_RUSH;
    public static Skill KATANASKILL;
    public static Skill BLOODWOLF;
    public static Skill BLOOD;
    public static Skill YAMATO_STEP;
    public static Skill SSTEP;
    public static Skill PSSTEP;
    public static Skill DUAL_TACHISKILL;
//
//    public static Skill YAMATO_PASSIVE;
//    public static Skill YAMATO_ART;
    public static Skill YAMATO_ATTACK;
    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build) {

        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker(CDmoveset.MOD_ID);
        YAMATO_STEP = modRegistry.build("yamato_step", DodgeSkill::new, StepSkill.createDodgeBuilder().setAnimations(() -> CorruptAnimations.YAMATO_STEP_FORWARD, () -> CorruptAnimations.YAMATO_STEP_BACKWARD, () -> CorruptAnimations.YAMATO_STEP_LEFT, () -> CorruptAnimations.YAMATO_STEP_RIGHT));
        SSTEP = modRegistry.build("sstep", DodgeSkill::new, StepSkill.createDodgeBuilder().setAnimations(() -> CorruptAnimations.SSTEP_FORWARD, () -> CorruptAnimations.SSTEP_BACKWARD, () -> CorruptAnimations.SSTEP_LEFT, () -> CorruptAnimations.SSTEP_FORWARD));
        GUARDPARRY = modRegistry.build("guardparry", GuardParrySkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION_INFINITE));
        BLOODWOLF = modRegistry.build( "bloodwolf", BloodWolf::new, PassiveSkill.createIdentityBuilder());
        DUAL_TACHISKILL = modRegistry.build( "dual_tachiskill", DualTchiSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        YAMATOSKILL = modRegistry.build( "yamatoskill", YamatoSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());

//
//        YAMATO_ART = modRegistry.build( "yamato_art", YamatoArt::new, WeaponInnateSkill.createWeaponInnateBuilder());
//        YAMATO_ATTACK = modRegistry.build("yamato_attack",YamatoAttack::new,YamatoAttack.createBuilder());
////        YAMATO_PASSIVE = modRegistry.build("yamato_passive",YamatoPassive::new,Skill.createBuilder().setCategory(SkillCategories.WEAPON_PASSIVE));
//

        SkillBuildEvent.ModRegistryWorker registryWorker = build.createRegistryWorker(CDmoveset.MOD_ID);
        PSSTEP = registryWorker.build("psstep", SStep::new, SStep.createDodgeBuilder()
                .setAnimations1(
                        () -> CorruptAnimations.WOLFDODGE_FORWARD,
                        () -> CorruptAnimations.WOLFDODGE_BACKWARD,
                        () -> CorruptAnimations.SSTEP_LEFT,
                        () -> CorruptAnimations.SSTEP_RIGHT
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
                        () -> CorruptAnimations.SSTEP_LEFT,
                        () -> CorruptAnimations.SSTEP_RIGHT
                )
                .setPerfectAnimations(
                        () -> Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT,
                        () -> Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT,
                        () -> CorruptAnimations.SSTEP_LEFT,
                        () -> CorruptAnimations.SSTEP_RIGHT
                )
        );
        WeaponInnateSkill KatanaSkill = modRegistry.build("katanaskill", KatanaSpSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        KatanaSkill.newProperty()
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(15.0F))
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3F))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE))
                .registerPropertiesToAnimation();
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


        WeaponInnateSkill denglong = modRegistry.build("denglong", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) CorruptAnimations.DENG_LONG));
        denglong
                .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        DENGLONG = denglong;

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

        WeaponInnateSkill blood = modRegistry.build("blood", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) CorruptAnimations.BLOOD));
        blood.newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(15.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        BLOOD = blood;

    }

    public CDSkills(){}
}


