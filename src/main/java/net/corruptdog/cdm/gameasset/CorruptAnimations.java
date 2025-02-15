package net.corruptdog.cdm.gameasset;

import net.corruptdog.cdm.Client.Particles.SkillEvent.Cut;
import net.corruptdog.cdm.api.animation.types.*;
import net.corruptdog.cdm.main.CDmoveset;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;
import  net.corruptdog.cdm.particle.CorruptParticles;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;

import java.util.List;
import java.util.Set;


public class CorruptAnimations {

    public static StaticAnimation BIPED_HIT_LONG2;
    public static StaticAnimation GUARD_BREAK1;
    public static StaticAnimation GUARD_BREAK2;
    public static StaticAnimation KNOCKDOWN;
    public static StaticAnimation EXECUTED_WEAPON;
    public static StaticAnimation TACHI_GUARD;
    public static StaticAnimation TACHI_GUARD_HIT;
    //旧版本闪避动作
    public static StaticAnimation ROLL_FORWARD;
    public static StaticAnimation ROLL_BACKWARD;
    public static StaticAnimation ROLL_LEFT;
    public static StaticAnimation ROLL_RIGHT;
    public static StaticAnimation STEP_FORWARD;
    public static StaticAnimation STEP_BACKWARD;
    public static StaticAnimation STEP_LEFT;
    public static StaticAnimation STEP_RIGHT;

    //完美闪避
    public static StaticAnimation SSTEP_FORWARD;
    public static StaticAnimation SSTEP_BACKWARD;
    public static StaticAnimation SSTEP_LEFT;
    public static StaticAnimation SSTEP_RIGHT;

    public static StaticAnimation WOLFDODGE_FORWARD;
    public static StaticAnimation WOLFDODGE_BACKWARD;
    public static StaticAnimation WOLFDODGE_LEFT;
    public static StaticAnimation WOLFDODGE_RIGHT;

    public static StaticAnimation BIPED_HOLD_KATANA_SHEATHING;
    public static StaticAnimation BIPED_KATANA_SCRAP;
    public static StaticAnimation BIPED_HOLD_KATANA;
    public static StaticAnimation KATANA_AUTO1;
    public static StaticAnimation KATANA_AUTO2;
    public static StaticAnimation KATANA_AUTO3;
    public static StaticAnimation KATANA_SHEATHING_AUTO;
    public static StaticAnimation KATANA_SHEATHING_DASH;
    public static StaticAnimation KATANA_AIR_SLASH;
    public static StaticAnimation KATANA_SHEATH_AIR_SLASH;
    public static StaticAnimation FATAL_DRAW;
    public static StaticAnimation FATAL_DRAW_DASH;
    public static StaticAnimation RUN_KATANA;
    public static StaticAnimation WALK_KATANA;
    public static StaticAnimation CLASH;
    public static StaticAnimation BACKWARD_SLASH;
    public static StaticAnimation BLADE_RUSH_FINISHER;
    public static StaticAnimation RECOVER;
    public static StaticAnimation FATALFLASH;
    public static StaticAnimation FATALFLASH_READY;


    public static StaticAnimation DUAL_GREATSWORD_IDLE;
    public static StaticAnimation DUAL_GREATSWORD_WALK;
    public static StaticAnimation DUAL_GREATSWORD_RUN;
    public static StaticAnimation DUAL_GREATSWORD_AUTO1;
    public static StaticAnimation DUAL_GREATSWORD_AUTO2;
    public static StaticAnimation DUAL_GREATSWORD_AUTO3;
    public static StaticAnimation DUAL_GREATSWORD_AUTO4;
    public static StaticAnimation DUAL_GREATSWORD_AIRSLASH;
    public static StaticAnimation DUAL_GREATSWORD_DASH;
    public static StaticAnimation DUAL_GREATSWORD_SKILL;

    public static StaticAnimation GREATSWORD_OLD_AUTO1;
    public static StaticAnimation GREATSWORD_OLD_AUTO2;
    public static StaticAnimation GREATSWORD_OLD_AUTO3;
    public static StaticAnimation GREATSWORD_OLD_AIRSLASH;
    public static StaticAnimation GREATSWORD_OLD_DASH;
    public static StaticAnimation GREATSWORD_OLD_IDLE;
    public static StaticAnimation GREATSWORD_OLD_WALK;
    public static StaticAnimation GREATSWORD_OLD_RUN;
    public static StaticAnimation WIND_SLASH;


    public static StaticAnimation TACHI_SLASH;
    public static StaticAnimation DUAL_SLASH;
    public static StaticAnimation D_BLADE_SLASH;


    public static StaticAnimation SSPEAR_ONEHAND_AUTO;
    public static StaticAnimation SSPEAR_DASH;
    public static StaticAnimation SSPEAR_TWOHAND_AUTO1;
    public static StaticAnimation SSPEAR_TWOHAND_AUTO2;
    public static StaticAnimation SPEAR_SLASH;

    //Ymato moveset
    public static StaticAnimation YAMATO_STEP_FORWARD;
    public static StaticAnimation YAMATO_STEP_BACKWARD;
    public static StaticAnimation YAMATO_STEP_LEFT;
    public static StaticAnimation YAMATO_STEP_RIGHT;
    public static StaticAnimation YAMATO_AUTO1;
    public static StaticAnimation YAMATO_AUTO2;
    public static StaticAnimation YAMATO_AUTO3;
    public static StaticAnimation YAMATO_AUTO4;
    public static StaticAnimation YAMATO_IDLE;
    public static StaticAnimation YAMATO_WALK;
    public static StaticAnimation YAMATO_RUN;
    public static StaticAnimation YAMATO_GUARD;
    public static StaticAnimation YAMATO_GUARD_HIT;
    public static StaticAnimation YAMATO_ACTIVE_GUARD_HIT;
    public static StaticAnimation YAMATO_ACTIVE_GUARD_HIT2;
    public static StaticAnimation YAMATO_DASH;
    public static StaticAnimation YAMATO_AIRSLASH;
    public static StaticAnimation YAMATO_POWER1;
    public static StaticAnimation YAMATO_POWER2;
    public static StaticAnimation YAMATO_POWER3;
    public static StaticAnimation YAMATO_POWER3_REPEAT;
    public static StaticAnimation YAMATO_POWER3_FINISH;
    public static StaticAnimation YAMATO_COUNTER1;
    public static StaticAnimation YAMATO_COUNTER2;
    public static StaticAnimation YAMATO_POWER_DASH;
    public static StaticAnimation YAMATO_STRIKE1;
    public static StaticAnimation YAMATO_STRIKE2;
    public static StaticAnimation YAMATO_POWER0_1;
    public static StaticAnimation N_YAMATO_POWER0_1;
    public static StaticAnimation YAMATO_POWER0_2;
    public static StaticAnimation YAMATO_RISING_SLASH;
    public static StaticAnimation YAMATO_TWIN_SLASH;
    public static StaticAnimation YAMATO_TURN_SLASH;

    public static StaticAnimation EX_YAMATO_AUTO1;
    public static StaticAnimation EX_YAMATO_AUTO2;
    public static StaticAnimation EX_YAMATO_AUTO3;
    public static StaticAnimation EX_YAMATO_AUTO4;
    public static StaticAnimation YAMATO_JUDEGMENT_CUT;




    public static StaticAnimation LONGSWORD_OLD_AUTO1;
    public static StaticAnimation LONGSWORD_OLD_AUTO2;
    public static StaticAnimation LONGSWORD_OLD_AUTO3;
    public static StaticAnimation LONGSWORD_OLD_AUTO4;
    public static StaticAnimation LONGSWORD_OLD_DASH;
    public static StaticAnimation LONGSWORD_OLD_AIRSLASH;
    public static StaticAnimation LETHAL_SLICING_START;
    public static StaticAnimation LETHAL_SLICING_ONCE;
    public static StaticAnimation LETHAL_SLICING_TWICE;
    public static StaticAnimation LETHAL_SLICING_ONCE1;


    public static StaticAnimation UCHIGATANA_IDLE;
    public static StaticAnimation UCHIGATANA_WALK;
    public static StaticAnimation UCHIGATANA_RUN;
    public static StaticAnimation UCHIGATANA_AUTO1;
    public static StaticAnimation UCHIGATANA_AUTO2;
    public static StaticAnimation UCHIGATANA_AUTO3;
    public static StaticAnimation UCHIGATANA_AUTO4;
    public static StaticAnimation UCHIGATANA_AUTO5;
    public static StaticAnimation UCHIGATANA_DASH;
    public static StaticAnimation UCHIGATANA_SKILL1;
    public static StaticAnimation UCHIGATANA_SKILL2;

    public static StaticAnimation DUAL_TACHI_AIRSLASH;
    public static StaticAnimation DUAL_TACHI_AUTO1;
    public static StaticAnimation DUAL_TACHI_AUTO2;
    public static StaticAnimation DUAL_TACHI_AUTO3;
    public static StaticAnimation DUAL_TACHI_AUTO4;
    public static StaticAnimation DUAL_TACHI_AUTO5;

    public static StaticAnimation TACHI_TWOHAND_AUTO_1;
    public static StaticAnimation TACHI_TWOHAND_AUTO_2;
    public static StaticAnimation TACHI_TWOHAND_AUTO_3;
    public static StaticAnimation TACHI_TWOHAND_AUTO_4;

    public static StaticAnimation SWORD_ONEHAND_AUTO1;
    public static StaticAnimation SWORD_ONEHAND_AUTO2;
    public static StaticAnimation SWORD_ONEHAND_AUTO3;
    public static StaticAnimation SWORD_ONEHAND_AUTO4;
    public static StaticAnimation SWORD_ONEHAND_DASH;
    public static StaticAnimation SWORD_SLASH;
    public static StaticAnimation DUAL_SWORD1;
    public static StaticAnimation DUAL_SWORD2;
    public static StaticAnimation DUAL_SWORD3;
    public static StaticAnimation DUAL_IDLE;


    public static StaticAnimation KATANA_SKILL2;
    public static StaticAnimation KATANA_SKILL3;
    public static StaticAnimation GUARD;
    public static StaticAnimation RECOUNTER;
    public static StaticAnimation SHILED_SLASH;
    public static StaticAnimation EXECUTE;
    public static StaticAnimation EXECUTED;
    public static StaticAnimation SK_EXECUTE;

    public static StaticAnimation DAGGER_DUAL_AUTO1;
    public static StaticAnimation DAGGER_DUAL_AUTO2;
    public static StaticAnimation DAGGER_DUAL_AUTO3;
    public static StaticAnimation DAGGER_DUAL_AUTO4;
    public static StaticAnimation BLADE_RUSH1;
    public static StaticAnimation BLADE_RUSH2;
    public static StaticAnimation BLADE_RUSH3;
    public static StaticAnimation BLADE_RUSH4;

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event){
        event.getRegistryMap().put(CDmoveset.MOD_ID, CorruptAnimations::build);
    }

    private static void build() {
        HumanoidArmature biped = Armatures.BIPED;
        EXECUTED = new LongHitAnimation(0.01F, "biped/hit/executed", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 4.0F))
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false);
        EXECUTED_WEAPON = new LongHitAnimation(0.01F, "biped/hit/executed_weapon", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 4.0F))
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EXECUT);

        TACHI_GUARD = new StaticAnimation(0.25F, true, "biped/hit/guard_longsword", biped);
        TACHI_GUARD_HIT = new GuardAnimation(0.05F, "biped/hit/guard_longsword_hit", biped);

        BIPED_HIT_LONG2 = new LongHitAnimation(0.08F, "biped/hit/hit_long", biped);
        KNOCKDOWN = new KnockdownAnimation(0.08F, "biped/hit/knockdown", biped);
        GUARD_BREAK1 = new LongHitAnimation(0.05F, "biped/hit/guard_break1", biped);
        GUARD_BREAK2 = new LongHitAnimation(0.05F, "biped/hit/guard_break2", biped);
        ROLL_FORWARD = new DodgeAnimation(0.1F, "biped/new/dodge/psstep/roll_forward", 0.6F, 0.8F, biped);
        ROLL_BACKWARD = new DodgeAnimation(0.1F, "biped/new/dodge/psstep/roll_backward", 0.6F, 0.8F, biped);
        ROLL_LEFT = new DodgeAnimation(0.1F, "biped/new/dodge/psstep/roll_left", 0.6F, 0.8F, biped);
        ROLL_RIGHT = new DodgeAnimation(0.1F, "biped/new/dodge/psstep/roll_right", 0.6F, 0.8F, biped);

        STEP_FORWARD = new DodgeAnimation(0.1F, 0.5F, "biped/new/dodge/psstep/step_forward", 0.6F, 1.65F, biped)
                .addState(EntityState.LOCKON_ROTATE, true)
                .newTimePair(0.0F, 0.2F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
        STEP_BACKWARD = new DodgeAnimation(0.1F, 0.5F, "biped/new/dodge/psstep/step_backward", 0.6F, 1.65F, biped)
                .newTimePair(0.0F, 0.2F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
        STEP_LEFT = new DodgeAnimation(0.1F, 0.5F, "biped/new/dodge/psstep/step_left", 0.6F, 1.65F, biped)
                .addState(EntityState.LOCKON_ROTATE, true)
                .newTimePair(0.0F, 0.2F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
        STEP_RIGHT = new DodgeAnimation(0.1F, 0.5F, "biped/new/dodge/psstep/step_right", 0.6F, 1.65F, biped)
                .addState(EntityState.LOCKON_ROTATE, true)
                .newTimePair(0.0F, 0.2F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);


        WOLFDODGE_FORWARD = new DodgeAnimation(0.1F, 1.35F, "biped/new/dodge/wolfdodge/forward", 0.6F, 1.65F, biped)
                .addState(EntityState.LOCKON_ROTATE, true)
                .newTimePair(0.0F, 0.5F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
        WOLFDODGE_BACKWARD = new DodgeAnimation(0.1F, 1.35F, "biped/new/dodge/wolfdodge/backward", 0.6F, 1.65F, biped)
                .newTimePair(0.0F, 0.5F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
        WOLFDODGE_RIGHT = new DodgeAnimation(0.1F, 1.05F, "biped/new/dodge/wolfdodge/right", 0.6F, 1.65F, biped)
                .addState(EntityState.LOCKON_ROTATE, true)
                .newTimePair(0.0F, 0.5F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
        WOLFDODGE_LEFT = new DodgeAnimation(0.1F, 1.05F, "biped/new/dodge/wolfdodge/left", 0.6F, 1.65F, biped)
                .addState(EntityState.LOCKON_ROTATE, true)
                .newTimePair(0.0F, 0.5F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);


        SSTEP_FORWARD = new YamatoDodge(0.15F, 0.55F, "biped/new/dodge/step_forward", 0.6F, 1.65F, biped)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(CorruptSound.FORESIGHT.get(), 0.8F, 1.2F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SSTEP_BACKWARD = new YamatoDodge(0.05F, 0.55F, "biped/new/dodge/step_backward", 0.6F, 1.65F, biped)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(CorruptSound.FORESIGHT.get(), 0.6F, 1.2F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SSTEP_LEFT = new YamatoDodge(0.02F, 0.50F, "biped/new/dodge/step_left", 0.6F, 1.65F, biped)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(CorruptSound.FORESIGHT.get(), 0.8F, 1.5F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SSTEP_RIGHT = new YamatoDodge(0.02F, 0.50F, "biped/new/dodge/step_right", 0.6F, 1.65F, biped)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(CorruptSound.FORESIGHT.get(), 0.8F, 1.2F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);


        BIPED_HOLD_KATANA_SHEATHING = new StaticAnimation(true, "biped/new/katana/hold_katana_sheath", biped);
        BIPED_HOLD_KATANA = new StaticAnimation(true, "biped/new/katana/hold_katana", biped);
        RUN_KATANA = new StaticAnimation(true, "biped/new/katana/run_katana", biped);
        WALK_KATANA = new StaticAnimation(true, "biped/new/katana/walk_unsheath", biped);
        BIPED_KATANA_SCRAP = (new StaticAnimation(false, "biped/new/katana/katana_scrap", biped))
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.15F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(EpicFightSounds.SWORD_IN.get()));
        KATANA_AUTO1 = new BasicAttackAnimation(0.15F, 0.05F, 0.16F, 0.2F, null, biped.toolR, "biped/new/katana/katana_auto1", biped);
        KATANA_AUTO2 = new BasicAttackAnimation(0.20F, 0.05F, 0.11F, 0.2F, null, biped.toolR, "biped/new/katana/katana_auto2", biped);
        KATANA_AUTO3 = new BasicAttackAnimation(0.16F, 0.1F, 0.21F, 0.59F, null, biped.toolR, "biped/new/katana/katana_auto3", biped);
        KATANA_SHEATHING_AUTO = (new BasicAttackAnimation(0.05F, 0.05F, 0.1F, 0.65F, ColliderPreset.BATTOJUTSU, biped.rootJoint, "biped/new/katana/katana_sheath_auto", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.ActionAnimationProperty.RESET_PLAYER_COMBO_COUNTER, false);
        KATANA_SHEATHING_DASH = (new DashAttackAnimation(0.06F, 0.05F, 0.05F, 0.11F, 0.65F, null, biped.toolR, "biped/new/katana/katana_sheath_dash", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get());
        KATANA_AIR_SLASH = new AirSlashAnimation(0.1F, 0.05F, 0.16F, 0.3F, null, biped.toolR, "biped/new/katana/katana_airslash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.FINISHER));
        KATANA_SHEATH_AIR_SLASH = (new AirSlashAnimation(0.1F, 0.1F, 0.16F, 0.3F, null, biped.toolR, "biped/new/katana/katana_sheath_airslash", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                .addProperty(AnimationProperty.ActionAnimationProperty.RESET_PLAYER_COMBO_COUNTER, false);
        FATAL_DRAW = (new AttackAnimation(0.15F, 0.0F, 0.7F, 0.81F, 1.0F, ColliderPreset.BATTOJUTSU, biped.rootJoint, "biped/new/katana/skill/fatal_draw", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.15F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(EpicFightSounds.SWORD_IN.get()));
        FATAL_DRAW_DASH = (new AttackAnimation(0.15F, 0.43F, 0.85F, 0.851F, 1.4F, CorruptCollider.FATAL_DRAW_DASH, biped.rootJoint, "biped/new/katana/skill/fatal_draw_dash", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.15F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(EpicFightSounds.SWORD_IN.get()))
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.85F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                );

        BLADE_RUSH_FINISHER = new AttackAnimation(0.15F, 0.0F, 0.1F, 0.26F, 0.75F, CorruptCollider.BLADE_RUSH_FINISHER, biped.rootJoint, "biped/new/blade_rush_finisher", biped)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        BACKWARD_SLASH = new DodgeAttackAnimation(0.1F, 0.3F, 0.20F, 0.48F, 2.38F, CorruptCollider.YAMATO_P, biped.toolL, "biped/new/katana/skill/backward_slash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.75F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(3.0F))
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.005F);

        CLASH = new AttackAnimation(0.0F, 1F, 1F, 1F, 1.0F, null, biped.toolL, "biped/new/katana/skill/clash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.05F))
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.005F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.2F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            SoundEvent soundEvent = EpicFightSounds.WHOOSH_ROD.get();
                            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundEvent, SoundSource.PLAYERS, 1.0F, 1.2F);
                        }, AnimationEvent.Side.SERVER)
                );

        FATALFLASH = new DodgeAttackAnimation(0.05F, 0.0F, 0.05F, 0.15F, 0.65F,CorruptCollider.BLADE_RUSH_FINISHER, biped.rootJoint, "biped/skill/fatal_flash", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0F)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(10F))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.35F))
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_UPDATE_TIME, TimePairList.create(0.0F, 0.25F))
//                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_ORIGIN_AS_TARGET_POSITION_BEGIN)
//                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_ORIGIN_AS_TARGET_POSITION)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_DEST_LOCATION_BEGIN)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_DEST_LOCATION)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_GET, MoveCoordFunctions.WORLD_COORD)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, SLOW_SPEED)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .setResourceLocation("biped/new/katana/katana_sheath_dash")
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.05F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                );
        FATALFLASH_READY = new DodgeAttackAnimation(0.05F, 1.2F, 1.2F, 1.2F, 0.5F, CorruptCollider.YAMATO_P, biped.toolR, "biped/new/katana/skill/fatalflash_ready", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.RESET_PLAYER_COMBO_COUNTER, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 4.0F))
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, SLOW_SPEED);


        DUAL_GREATSWORD_AUTO1 = (new BasicAttackAnimation(0.25F, "biped/new/dual_greatsword/combat/greatsword_dual_auto_1", biped,
                new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, InteractionHand.OFF_HAND, biped.toolL, null),
                new AttackAnimation.Phase(0.45F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_GREATSWORD_AUTO2 = (new BasicAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, CorruptCollider.GREATSWORD_DOUBLESWING, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_dual_auto_2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.85F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, -1.0F, 2.0F), Armatures.BIPED.rootJoint, 1.5D, 0.55F));
        DUAL_GREATSWORD_AUTO3 = (new BasicAttackAnimation(0.15F, "biped/new/dual_greatsword/combat/greatsword_dual_auto_3", biped,
                new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, biped.toolR, null)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F)),
                new AttackAnimation.Phase(0.45F, 0.55F, 0.7F, 0.9F, Float.MAX_VALUE, InteractionHand.OFF_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter( 3.5F)))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.80F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, -0.0F, -2.0F), Armatures.BIPED.rootJoint, 1.15D, 0.55F));
        DUAL_GREATSWORD_AUTO4 = (new BasicAttackAnimation(0.1F, 0.8F, 1.0F, 1.25F, InteractionHand.OFF_HAND, CorruptCollider.GREATSWORD_DUAL, biped.rootJoint, "biped/new/dual_greatsword/combat/greatsword_dual_auto_4", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_GREATSWORD_DASH = (new BasicAttackAnimation(0.05F, 0.1F, 0.4F, 0.4F, CorruptCollider.SHOULDER_BUMP, biped.rootJoint, "biped/new/dual_greatsword/combat/greatsword_dual_dash", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_GREATSWORD_AIRSLASH = (new BasicAttackAnimation(0.05F, 0.25F, 0.4F, 0.45F, InteractionHand.OFF_HAND, CorruptCollider.AIRSLAM, biped.rootJoint, "biped/new/dual_greatsword/combat/greatsword_dual_airslash", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.FINISHER))
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.2F))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_GREATSWORD_SKILL = new AttackAnimation(0.15F, "biped/new/dual_greatsword/skill/greatsword_dual_earthquake", biped,
                new AttackAnimation.Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, biped.toolR, CorruptCollider.GREATSWORD_DOUBLESWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                new AttackAnimation.Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, biped.rootJoint, CorruptCollider.GREATSWORD_DUAL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                .addEvents(AnimationEvent.TimeStampedEvent.create(1.30F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.rootJoint, 2.55D, 0.55F));
        DUAL_GREATSWORD_IDLE = new StaticAnimation(0.1F, true, "biped/new/dual_greatsword/living/greatsword_dual_idle", biped);
        DUAL_GREATSWORD_WALK = new MovementAnimation(0.1F, true, "biped/new/dual_greatsword/living/greatsword_dual_walk", biped);
        DUAL_GREATSWORD_RUN = new MovementAnimation(0.1F, true, "biped/new/dual_greatsword/living/greatsword_dual_run", biped);
        GREATSWORD_OLD_IDLE = new MovementAnimation(0.1F, true, "biped/new/dual_greatsword/living/hold_greatsword", biped);
        GREATSWORD_OLD_WALK = new MovementAnimation(0.1F, true, "biped/new/dual_greatsword/living/walk_greatsword", biped);
        GREATSWORD_OLD_RUN = new MovementAnimation(0.1F, true, "biped/new/dual_greatsword/living/run_greatsword", biped);
        GREATSWORD_OLD_AUTO1 = (new BasicAttackAnimation(0.1F, 0.3F, 0.4F, 0.5F, InteractionHand.MAIN_HAND, ColliderPreset.GREATSWORD, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_twohand_auto_1", biped))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
        GREATSWORD_OLD_AUTO2 = (new BasicAttackAnimation(0.1F, 0.3F, 0.4F, 0.5F, InteractionHand.MAIN_HAND, ColliderPreset.GREATSWORD, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_twohand_auto_2", biped))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
        GREATSWORD_OLD_AUTO3 = (new BasicAttackAnimation(0.1F, 0.4F, 0.5F, 0.6F, InteractionHand.MAIN_HAND, ColliderPreset.GREATSWORD, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_twohand_auto_3", biped))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
        GREATSWORD_OLD_DASH = (new DashAttackAnimation(0.11F, 0.4F, 0.65F, 0.8F, 1.2F, ColliderPreset.GREATSWORD, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_dash", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, Boolean.TRUE);
        GREATSWORD_OLD_AIRSLASH = (new AirSlashAnimation(0.1F, 0.5F, 0.55F, 0.71F, 0.75F, false, null, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_airslash", biped));
        WIND_SLASH = new AttackAnimation(0.2F, "biped/new/wind_slash", biped,
                new AttackAnimation.Phase(.0F, 0.3F, 0.35F, 0.55F, 0.9F, 0.9F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.9F, 0.95F, 1.05F, 1.2F, 1.5F, 1.5F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(1.5F, 1.65F, 1.75F, 1.95F, 2.5F, Float.MAX_VALUE, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, Boolean.FALSE)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);


        LETHAL_SLICING_START = new AttackAnimation(0.15F, 0.05F, 0.10F, 0.15F, 0.38F, ColliderPreset.FIST_FIXED, biped.rootJoint, "biped/new/lethal_slicing_start", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        LETHAL_SLICING_ONCE = new AttackAnimation(0.15F, 0.06F, 0.10F, 0.25F, 0.6F, CorruptCollider.LETHAL_SLICING, biped.rootJoint, "biped/new/lethal_slicing_once", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        LETHAL_SLICING_TWICE = new AttackAnimation(0.015F, 0.06F, 0.10F, 0.35F, 0.6F, CorruptCollider.LETHAL_SLICING, biped.rootJoint, "biped/new/lethal_slicing_twice", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(50.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.75F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        LETHAL_SLICING_ONCE1 = new AttackAnimation(0.015F, 0.06F, 0.10F, 0.15F, 0.85F, CorruptCollider.LETHAL_SLICING1, biped.rootJoint, "biped/new/lethal_slicing_once1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get()).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(50.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        SWORD_SLASH = new AttackAnimation(0.20F, 0.1F, 0.35F, 0.46F, 0.79F, ColliderPreset.BIPED_BODY_COLLIDER, biped.toolR, "biped/new/sword_slash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.45F)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE);


        SSPEAR_ONEHAND_AUTO = new BasicAttackAnimation(0.16F, 0.1F, 0.2F, 0.45F, null, biped.toolR, "biped/new/spear/spear_onehand_auto", biped);
        SSPEAR_TWOHAND_AUTO1 = new BasicAttackAnimation(0.25F, 0.05F, 0.15F, 0.45F, null, biped.toolR, "biped/new/spear/spear_twohand_auto1", biped);
        SSPEAR_TWOHAND_AUTO2 = new BasicAttackAnimation(0.25F, 0.05F, 0.15F, 0.45F, null, biped.toolR, "biped/new/spear/spear_twohand_auto2", biped);
        SSPEAR_DASH = (new DashAttackAnimation(0.16F, 0.05F, 0.2F, 0.3F, 0.7F, null, biped.toolR, "biped/new/spear/spear_dash", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        SPEAR_SLASH = (new AttackAnimation(0.11F, "biped/new/spear_slash", biped,
                new AttackAnimation.Phase(0.0F, 0.3F, 0.36F, 0.5F, 0.5F, biped.toolR, null),
                new AttackAnimation.Phase(0.5F, 0.5F, 0.56F, 0.75F, 0.75F, biped.toolR, null),
                new AttackAnimation.Phase(0.75F, 0.75F, 0.81F, 1.05F, Float.MAX_VALUE, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

        DUAL_SLASH = new AttackAnimation(0.1F, "biped/new/dual_slash", biped,
                new AttackAnimation.Phase(.0F, 0.2F, 0.31F, 0.4F, 0.4F, biped.toolR, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, biped.toolL, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.65F, 0.75F, 0.85F, 1.15F, Float.MAX_VALUE, biped.toolR, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        YAMATO_STEP_FORWARD = new DodgeAnimation(0.005F, 0.55F, "biped/new/yamato_step_forward", 0.6F, 1.65F, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.2F))
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(CorruptSound.YAMATO_STEP.get(), 1.5F, 1.5F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addState(EntityState.LOCKON_ROTATE, true)
                .newTimePair(0.0F, 0.5F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
        YAMATO_STEP_BACKWARD = new DodgeAnimation(0.005F, 0.55F, "biped/new/yamato_step_backward", 0.6F, 1.65F, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.2F))
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(CorruptSound.YAMATO_STEP.get(), 1.5F, 1.5F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .newTimePair(0.0F, 0.5F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
        YAMATO_STEP_LEFT = new DodgeAnimation(0.02F, 0.55F, "biped/new/yamato_step_left", 0.6F, 1.65F, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.2F))
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(CorruptSound.YAMATO_STEP.get(), 1.5F, 1.5F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .newTimePair(0.0F, 0.5F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
        YAMATO_STEP_RIGHT = new DodgeAnimation(0.02F, 0.55F, "biped/new/yamato_step_right", 0.6F, 1.65F, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.5F))
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(CorruptSound.YAMATO_STEP.get(), 1.5F, 1.5F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .newTimePair(0.0F, 0.5F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
        YAMATO_IDLE = new StaticAnimation(true, "biped/new/yamato/yamato_idle", biped);
        YAMATO_WALK = new StaticAnimation(true, "biped/new/yamato/yamato_walk", biped);
        YAMATO_RUN = new StaticAnimation(true, "biped/new/yamato/yamato_run", biped);
        YAMATO_GUARD = new StaticAnimation(true, "biped/new/yamato/yamato_guard", biped);
        YAMATO_GUARD_HIT = new GuardAnimation(0.05F, "biped/new/yamato/yamato_guard_hit", biped);
        YAMATO_ACTIVE_GUARD_HIT = new GuardAnimation(0.02F, "biped/new/yamato/yamato_guard_parry", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        YAMATO_ACTIVE_GUARD_HIT2 = new GuardAnimation(0.02F, "biped/new/yamato/yamato_guard_parry2", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        YAMATO_AUTO1 = (new YamtoAttackAnimation(0.05F, 0.0F, 1.41F, 0.75F, 1.3F, 0.27F, 1.41F, 0.0F, 0.0F, "biped/new/yamato/yamato_auto1", biped,
                new AttackAnimation.Phase(0.0F, 0.25F, 0.3F, 0.35F, 0.55F, 0.55F, InteractionHand.MAIN_HAND, biped.toolL, CorruptCollider.YAMATO_SHEATH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT),
                new AttackAnimation.Phase(0.55F, 0.56F, 0.65F, 0.75F, 0.75F, 0.75F, InteractionHand.MAIN_HAND, biped.toolL, CorruptCollider.YAMATO_SHEATH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(1.28F, COMBO_BREAK, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.0F, YAMATO_IN, AnimationEvent.Side.SERVER))
                .addState(EntityState.MOVEMENT_LOCKED, true);

        YAMATO_AUTO2 = (new YamtoAttackAnimation(0.05F, 0.0F, 2.1F, 0.7F, 1.2F, 0.3F, 2.1F, 0.0F, 0.0F, "biped/new/yamato/yamato_auto2", biped,
                new AttackAnimation.Phase(0.0F, 0.3F, 0.37F, 0.53F, 0.53F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get()),
                new AttackAnimation.Phase(0.53F, 0.6F, 0.67F, 0.73F, 0.75F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter( 2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.25F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.78F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.8F, STAMINAR, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.75F,COMBO_BREAK, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.8F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()))
                .addState(EntityState.MOVEMENT_LOCKED, true));
        YAMATO_AUTO3 = new YamtoAttackAnimation(0.05F, 0.0F, 2.65F, 1.3F, 1.75F, 0.7F, 2.65F, 0.0F, 0.0F, "biped/new/yamato/yamato_auto3", biped,
                new AttackAnimation.Phase(0.0F, 0.7F, 0.78F, 0.88F, 0.88F, biped.toolR, null),
                new AttackAnimation.Phase(0.88F, 1.12F, 1.23F, 1.25F, 1.25F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.75F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.0F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.1F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.1F, STAMINAR, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.05F,COMBO_BREAK, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.1F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()))
                .addState(EntityState.MOVEMENT_LOCKED, true);
        YAMATO_AUTO4 = new YamtoAttackAnimation(0.10F, 0.0F, 2.87F, 2.87F, 2.87F, 0.81F, 2.87F, 0.81F, 2.87F, "biped/new/yamato/yamato_auto4", biped,
                (new AttackAnimation.Phase(0.0F, 0.81F, 0.9F, 2.87F, 2.87F, biped.toolR, CorruptCollider.YAMATO_P))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter( 5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(15.0F)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.95F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.0F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.45F,COMBO_BREAK, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.5F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.5F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()),
                        AnimationEvent.TimeStampedEvent.create(2.55F, STAMINA, AnimationEvent.Side.SERVER))
                .addState(EntityState.MOVEMENT_LOCKED, true);
        YAMATO_DASH = new DashAttackAnimation(0.12F, 0.1F, 0.25F, 0.4F, 0.65F, null, biped.toolR, "biped/new/yamato/yamato_dash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(2))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, Boolean.TRUE);
        YAMATO_AIRSLASH = new AirSlashAnimation(0.25F, 0.15F, 0.26F, 0.5F, null, biped.toolR, "biped/new/yamato/yamato_airslash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.adder(1.5F));
        YAMATO_POWER1 = (new KnockbackAnimation(0.05F, 0.42F, 0.43F, 0.53F, 3.83F, CorruptCollider.YAMATO_P, biped.toolR, "biped/new/yamato/skill/yamato_power1", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter( 15F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.FINISHER))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F).addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.48F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.89F, YAMATO_IN, AnimationEvent.Side.SERVER),
        AnimationEvent.TimeStampedEvent.create(0.43F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(EpicFightSounds.WHOOSH_SHARP.get()),
                        AnimationEvent.TimeStampedEvent.create(2.89F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()),
                        AnimationEvent.TimeStampedEvent.create(2.90F, STAMINASKILL, AnimationEvent.Side.SERVER));
        YAMATO_POWER2 = new AttackAnimation(0.05F, "biped/new/yamato/skill/yamato_power2", biped,
                (new AttackAnimation.Phase(0.0F, 0.62F, 0.68F, 1.05F, 1.05F, biped.toolR, CorruptCollider.YAMATO_P))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter( 3.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F)),
                (new AttackAnimation.Phase(1.05F, 1.28F, 1.55F, 4.91F, 4.91F, biped.toolR, CorruptCollider.YAMATO_P))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter( 5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(5))
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter( 100F)))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.01F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(4.4F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(4.1F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()),
                        AnimationEvent.TimeStampedEvent.create(4.1F, STAMINASKILL, AnimationEvent.Side.SERVER));
        YAMATO_POWER3 = new BasicAttackAnimation(0.05F, "biped/new/yamato/skill/yamato_power3", biped,
                (new AttackAnimation.Phase(0.0F, 0.67F, 0.73F, 0.88F, 0.88F, biped.rootJoint, CorruptCollider.YAMATO_DASH))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F)),
                (new AttackAnimation.Phase(0.88F, 0.94F, 1.0F, 1.0F, 1.8F, biped.rootJoint, CorruptCollider.YAMATO_DASH))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F)))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, Boolean.FALSE)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.4667F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.65F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.15F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.60F, (entitypatch, animation, params) -> {
                            if (entitypatch instanceof ServerPlayerPatch patch){
                                float cost = 2;
                                if(patch.getStamina() > cost) {
                                    BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ANOTHER_ACTION_ANIMATION, patch, patch.getSkill(SkillSlots.BASIC_ATTACK), YAMATO_POWER3, 5);
                                }
                            }
                        }, AnimationEvent.Side.SERVER));


        YAMATO_POWER3_REPEAT = new YamtoAttackAnimation(0F, 0F,0.65F,0.48F,0.63F,0.04F,0.65F, 0F, 0F, "biped/new/yamato/skill/yamato_power3_repeat", biped,
                new AttackAnimation.Phase(0.0F, 0.05F, 0.15F, 0.25F, 0.25F, biped.rootJoint, CorruptCollider.YAMATO_DASH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(5F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F)),
                new AttackAnimation.Phase(0.29F, 0.31F, 0.36F, 0.45F, 0.5F, biped.rootJoint, CorruptCollider.YAMATO_DASH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(5F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F)))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, Boolean.FALSE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.0F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.5F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.05F, (entitypatch, animation, params) -> {
                            float cost = 3;
                            if (entitypatch instanceof ServerPlayerPatch patch) {
                                BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ANOTHER_ACTION_ANIMATION, patch, patch.getSkill(SkillSlots.BASIC_ATTACK), YAMATO_POWER3_REPEAT, 5);
                                patch.setStamina(patch.getStamina()-cost);
                            }
                        }, AnimationEvent.Side.SERVER))
                .addEvents(AnimationEvent.TimePeriodEvent.create(0.35F, 1.0F, (entitypatch, animation, params) -> {
                    Vec3 pos = entitypatch.getOriginal().position();

                    for (int x = -1; x <= 1; x += 2) {
                        for (int z = -1; z <= 1; z += 2) {
                            Vec3 rand = new Vec3(Math.random() * x, Math.random(), Math.random() * z).normalize().scale(2.0D);
                            entitypatch.getOriginal().level().addParticle(EpicFightParticles.TSUNAMI_SPLASH.get(), pos.x + rand.x, pos.y + rand.y - 1.0D, pos.z + rand.z, rand.x * 0.1D, rand.y * 0.1D, rand.z * 0.1D);
                        }
                    }
                }, AnimationEvent.Side.CLIENT));
        YAMATO_POWER3_FINISH = new YamtoAttackAnimation(0F, 0F,0.65F,0.48F,0.63F,0.04F,0.65F, 0F, 0F, "biped/new/yamato/skill/yamato_power3_finish", biped,
                (new AttackAnimation.Phase(0.0F, 0.05F, 0.13F, 0.43F, 0.43F, biped.rootJoint, CorruptCollider.YAMATO_DASH))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.05F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get()),
                (new AttackAnimation.Phase(0.43F, 0.82F, 0.9F, 1.35F, 1.5F, biped.rootJoint, CorruptCollider.YAMATO_DASH_FINISH))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(50F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.133F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.9F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.89F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()))
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.05F,COMBO_BREAK, AnimationEvent.Side.SERVER));

        YAMATO_POWER_DASH = new AttackAnimation(0.005F, "biped/new/yamato/skill/yamato_power_dash", biped,
                (new AttackAnimation.Phase(0.0F, 0.38F, 0.9F, 1.17F, 1.17F, biped.rootJoint, CorruptCollider.YAMATO_DASH))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.75F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get()),
                (new AttackAnimation.Phase(1.17F, 1.6F, 1.67F, 2.26F, 2.26F, biped.rootJoint, CorruptCollider.YAMATO_DASH_FINISH))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.75F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(75F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, CorruptSound.YAMATO_IN.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, Boolean.TRUE)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.7F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.6F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.35F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                );
        YAMATO_COUNTER1 = new DodgeAttackAnimation(0.05F, 0.60F, 0.60F, 0.8F, 1.2F, CorruptCollider.YAMATO_DASH, biped.rootJoint, "biped/new/yamato/skill/yamato_counter_1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.75F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter( 2F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(3.0F))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, Boolean.TRUE)
                .addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
                .newTimePair(0.0F, 2.38F)
                .addStateRemoveOld(EntityState.MOVEMENT_LOCKED, true)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.05F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.35F, (entitypatch, animation, params) -> {
                            if (entitypatch instanceof ServerPlayerPatch patch) {
                                BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ANOTHER_ACTION_ANIMATION, patch, patch.getSkill(SkillSlots.BASIC_ATTACK), YAMATO_COUNTER1, 4);
                            }
                        }, AnimationEvent.Side.SERVER));

        YAMATO_COUNTER2 = new AttackAnimation(0.02F, 0.01F, 0.55F, 0.56F, 1.15F, CorruptCollider.YAMATO_P0, biped.rootJoint, "biped/new/yamato/skill/yamato_counter_2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, CorruptSound.YAMATO_IN.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(3.0F))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.RESET_PLAYER_COMBO_COUNTER, true)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.0F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.55F, YAMATO_IN, AnimationEvent.Side.SERVER));


        YAMATO_STRIKE1 = new YamtoAttackAnimation(0.15F, 0.0F, 2.1F, 0.7F, 1.2F, 0.3F, 2.1F, 0.0F, 0.0F, "biped/new/yamato/skill/yamato_strike1", biped,
                (new AttackAnimation.Phase(0.0F, 0.51F, 0.62F, 0.72F, 0.72F, biped.toolR, CorruptCollider.YAMATO_P))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F)),
                (new AttackAnimation.Phase(0.75F, 0.78F, 0.87F, 0.9F, 1.0F, biped.toolR, CorruptCollider.YAMATO_P))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
//                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.48F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.81F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.55F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()),
                        AnimationEvent.TimeStampedEvent.create(0.28F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.TimeStampedEvent.create(0.84F, (entitypatch, animation, params) -> {
                            if (entitypatch instanceof ServerPlayerPatch patch) {
                                BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ANOTHER_ACTION_ANIMATION, patch, patch.getSkill(SkillSlots.BASIC_ATTACK), YAMATO_STRIKE1, 2);
                            }
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.5F,COMBO_BREAK, AnimationEvent.Side.SERVER)
                );

        YAMATO_STRIKE2 = new YamtoAttackAnimation(0.10F, 0.0F, 2.65F, 1.3F, 1.65F, 0.7F, 2.35F, 0.0F, 0.0F, "biped/new/yamato/skill/yamato_strike2", biped,
                new AttackAnimation.Phase(0.0F, 0.82F, 0.95F, 1.0F, 1.0F, biped.toolR, CorruptCollider.YAMATO_P)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F)),
                new AttackAnimation.Phase(1.0F, 1.1F, 1.32F, 1.42F, 1.45F, biped.toolR, CorruptCollider.YAMATO_P)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(1.5F)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
//                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.46F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.11F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.86F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()),
                        AnimationEvent.TimeStampedEvent.create(0.28F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.TimeStampedEvent.create(1.36F, (entitypatch, animation, params) -> {
                            if (entitypatch instanceof ServerPlayerPatch patch) {
                                BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ANOTHER_ACTION_ANIMATION, patch, patch.getSkill(SkillSlots.BASIC_ATTACK), YAMATO_STRIKE2, 3);
                            }
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.75F,COMBO_BREAK, AnimationEvent.Side.SERVER)
                );
        YAMATO_POWER0_1 = (new YamtoAttackAnimation(0.05F, 0.0F, 2.87F, 2.87F, 2.87F, 0.81F, 2.87F, 0.81F, 0.83F, "biped/new/yamato/skill/yamato_power0_1", biped,
                new AttackAnimation.Phase(0.0F, 0.75F, 0.767F, 0.83F, 1.23F, biped.rootJoint, CorruptCollider.LETHAL_SLICING)))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.25F))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.SWORD_IN.get())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, SPEED)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.67F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.74F, YAMATO_IN, AnimationEvent.Side.SERVER));
        YAMATO_POWER0_2 = new DodgeAttackAnimation(0.005F, 0.55F, 0.567F, 0.7F, 0.7F, CorruptCollider.YAMATO_P0, biped.rootJoint, "biped/new/yamato/skill/yamato_power0_2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter(2.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.25F))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, CorruptParticles.FORESIGHT)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .newTimePair(0.0F, 1.5F)
                .addStateRemoveOld(EntityState.MOVEMENT_LOCKED, true)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((entitypatch, animation, params) -> {
                    Entity entity = entitypatch.getOriginal();
                    entitypatch.getOriginal().level().addParticle(CorruptParticles.CORRUPT_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                    entitypatch.playSound(CorruptSound.FORESIGHT.get(), 1.0F, 0.0F, 0.0F);
                    }, AnimationEvent.Side.CLIENT))
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.488F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.54F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.5F, STAMINASKILL, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.55F, (entitypatch, animation, params) -> {
                            if (entitypatch instanceof ServerPlayerPatch patch) {
                                BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ANOTHER_ACTION_ANIMATION, patch, patch.getSkill(SkillSlots.BASIC_ATTACK), YAMATO_STRIKE2, 4);
                            }
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.05F,COMBO_BREAK, AnimationEvent.Side.SERVER));

        YAMATO_RISING_SLASH = new KnockbackAnimation(0.25F,"biped/new/yamato/skill/yamato_risingslash", biped,
                new AttackAnimation.Phase(0.0F, 0.35F, 0.50F, 0.60F, 0.7F, biped.rootJoint, CorruptCollider.RISING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER,ValueModifier.multiplier(1.25F)),
                new AttackAnimation.Phase(0.7F, 1.75F, 1.9F, 2.0F, 2.3F, biped.rootJoint, CorruptCollider.RISING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER,ValueModifier.multiplier(0.5F)))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .newTimePair(0.0F,3.0F)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                .addStateRemoveOld(EntityState.MOVEMENT_LOCKED, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.40F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.85F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.85F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()),
                        AnimationEvent.TimeStampedEvent.create(2.05F, STAMINA, AnimationEvent.Side.SERVER))
                .addState(EntityState.MOVEMENT_LOCKED, true);
        YAMATO_TWIN_SLASH= new AttackAnimation(0.25F,  "biped/new/yamato/skill/yamato_twinslash", biped,
                new AttackAnimation.Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.35F, biped.toolR, CorruptCollider.YAMATO_P)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER,ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get()),
                new AttackAnimation.Phase(0.35F, 0.35F, 0.45F, 2.5F, 3.0F, biped.toolR, CorruptCollider.YAMATO_P)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER,ValueModifier.multiplier(1.25F)))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .newTimePair(0.0F,3.0F)
                .addStateRemoveOld(EntityState.MOVEMENT_LOCKED, true)
                .newTimePair(0.0F,0.65F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.05F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.35F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.35F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()),
                        AnimationEvent.TimeStampedEvent.create(2.35F, STAMINA, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0.55F, (entitypatch, animation, params) -> {
                            if (entitypatch instanceof ServerPlayerPatch patch) {
                                BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ANOTHER_ACTION_ANIMATION, patch, patch.getSkill(SkillSlots.BASIC_ATTACK), YAMATO_TWIN_SLASH, 2);
                            }
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.3F,COMBO_BREAK, AnimationEvent.Side.SERVER)
                )
                .addState(EntityState.MOVEMENT_LOCKED, true);
        YAMATO_TURN_SLASH = new AttackAnimation(0.15F, "biped/new/yamato/skill/yamato_turnslash", biped,
                new AttackAnimation.Phase(0.0F, 0.45F, 0.50F, 0.60F, 2.3F, biped.toolR, CorruptCollider.YAMATO_P))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.0F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.15F, YAMATO_IN, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.15F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()),
                        AnimationEvent.TimeStampedEvent.create(2.05F, STAMINA, AnimationEvent.Side.SERVER));
        YAMATO_JUDEGMENT_CUT = new ExecuteAnimation(0.0F, 0.0F, 2.65F, 1.3F, 1.75F, 0.7F, 2.65F, 0.0F, 0.0F, "biped/new/yamato/skill/yamato_judgement_cut", biped,
                new AttackAnimation.Phase(0.0F, 2.35F, 2.55F, 2.55F,  2.55F, biped.rootJoint, CorruptCollider.POWER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                new AttackAnimation.Phase(2.55F, 2.6F, 2.65F, 2.7F, 2.7F, biped.rootJoint, CorruptCollider.POWER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                new AttackAnimation.Phase(2.7F, 2.75F, 2.8F, 2.85F, 2.85F, biped.rootJoint, CorruptCollider.POWER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                new AttackAnimation.Phase(2.85F, 2.9F, 2.95F, 3.0F, 3.0F, biped.rootJoint, CorruptCollider.POWER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                new AttackAnimation.Phase(3.0F, 3.05F, 3.1F, 3.15F, 4.55F, biped.rootJoint, CorruptCollider.POWER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(5F)),
                new AttackAnimation.Phase(4.55F, 4.65F, 4.85F, 5F, 10F, biped.rootJoint, CorruptCollider.POWER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(100.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.NO_SOUND.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10F)))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((entitypatch, animation, params) -> {
                    Entity entity = entitypatch.getOriginal();
                    entitypatch.getOriginal().level().addParticle(CorruptParticles.CORRUPT_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                }, AnimationEvent.Side.CLIENT))
                .addProperty(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, new AnimationEvent[]{
                        AnimationEvent.create((ep, anim, objs) -> {
                            Cut.prev(ep);
                        }, AnimationEvent.Side.CLIENT)
                })
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(1.95F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_CUT1.get()),
                        AnimationEvent.TimeStampedEvent.create(4.55F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_CUT2.get()),
                        AnimationEvent.TimeStampedEvent.create(4.55F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()),
                        AnimationEvent.TimeStampedEvent.create(1.7F, YAMATO_OUT, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(4.65F, YAMATO_IN, AnimationEvent.Side.SERVER))
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false);

//             /indestructible 9aeaa1be-2bda-4409-bc3a-63e08352f07f play "cdmoveset:biped/new/yamato/skill/yamato_judgement_cut" 0 0


        EX_YAMATO_AUTO1 = (new YamtoAttackAnimation(0.05F, 0.0F, 1.41F, 0.75F, 1.3F, 0.27F, 1.41F, 0.0F, 0.0F, "biped/new/yamato/ex_yamato/yamato_auto1", biped,
                new AttackAnimation.Phase(0.0F, 0.05F, 0.15F, 0.25F, 0.5F, 0.5F, InteractionHand.MAIN_HAND, biped.toolL, CorruptCollider.YAMATO_SHEATH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.0F, YAMATO_IN, AnimationEvent.Side.SERVER))
                .addState(EntityState.MOVEMENT_LOCKED, true);

        EX_YAMATO_AUTO2 = (new YamtoAttackAnimation(0.05F, 0.0F, 2.1F, 0.7F, 1.2F, 0.3F, 2.1F, 0.0F, 0.0F, "biped/new/yamato/ex_yamato/yamato_auto2", biped,
                new AttackAnimation.Phase(0.0F, 0.05F, 0.17F, 0.73F, 0.75F, biped.toolL, CorruptCollider.YAMATO_SHEATH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter( 2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(1.8F, STAMINAR, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(1.8F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()))
                .addState(EntityState.MOVEMENT_LOCKED, true);
        EX_YAMATO_AUTO3 = new YamtoAttackAnimation(0.05F, 0.0F, 2.65F, 1.3F, 1.75F, 0.7F, 2.65F, 0.0F, 0.0F, "biped/new/yamato/ex_yamato/yamato_auto3", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.17F, 0.23F, 0.23F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get()),
                new AttackAnimation.Phase(0.23F, 0.25F, 0.35F, 0.43F, 0.75F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.75F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(2.1F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()))
                .addState(EntityState.MOVEMENT_LOCKED, true);
        EX_YAMATO_AUTO4 = new YamtoAttackAnimation(0.10F, 0.0F, 2.87F, 2.87F, 2.87F, 0.81F, 2.87F, 0.81F, 2.87F, "biped/new/yamato/ex_yamato/yamato_auto4", biped,
                (new AttackAnimation.Phase(0.0F, 0.45F, 0.50F, 0.60F, 2.3F, biped.toolR, CorruptCollider.YAMATO_P))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter( 5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(15.0F)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.95F)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(2.5F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(CorruptSound.YAMATO_IN.get()),
                        AnimationEvent.TimeStampedEvent.create(2.55F, STAMINA, AnimationEvent.Side.SERVER))
                .addState(EntityState.MOVEMENT_LOCKED, true);


        LONGSWORD_OLD_AUTO1 = new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.4F, null, biped.toolR, "biped/new/longsword/longsword_twohand_auto_1", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);
        LONGSWORD_OLD_AUTO2 = new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.4F, null, biped.toolR, "biped/new/longsword/longsword_twohand_auto_2", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);
        LONGSWORD_OLD_AUTO3 = new BasicAttackAnimation(0.1F, 0.2F, 0.4F, 0.45F, null, biped.toolR, "biped/new/longsword/longsword_twohand_auto_3", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);
        LONGSWORD_OLD_AUTO4 = new BasicAttackAnimation(0.2F, 0.3F, 0.4F, 0.7F, null, biped.toolR, "biped/new/longsword/longsword_twohand_auto_4", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.adder(1.25F));
        LONGSWORD_OLD_DASH = (new DashAttackAnimation(0.15F, 0.1F, 0.3F, 0.5F, 0.7F, null, biped.toolR, "biped/new/longsword/longsword_dash", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        LONGSWORD_OLD_AIRSLASH = new AirSlashAnimation(0.1F, 0.3F, 0.41F, 0.5F, null, biped.toolR, "biped/new/longsword/longsword_airslash", biped);

        UCHIGATANA_IDLE = new StaticAnimation(true, "biped/new/dual_tachi/uchigatana_idle", biped);
        UCHIGATANA_WALK = new StaticAnimation(true, "biped/new/dual_tachi/uchigatana_walk", biped);
        UCHIGATANA_RUN = new StaticAnimation(true, "biped/new/dual_tachi/uchigatana_run", biped);
        UCHIGATANA_AUTO1 = new BasicAttackAnimation(0.05F, 0.467F, 0.6F, 0.833F, null, biped.toolR, "biped/new/dual_tachi/uchigatana_auto1", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter(2.0F));
        UCHIGATANA_AUTO2 = new BasicAttackAnimation(0.1F, 0.433F, 0.6F, 0.833F, null, biped.toolR, "biped/new/dual_tachi/uchigatana_auto2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter(2.0F))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);
        UCHIGATANA_AUTO3 = new BasicAttackAnimation(0.1F, 0.4F, 0.533F, 0.833F, null, biped.toolR, "biped/new/dual_tachi/uchigatana_auto3", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter(1.5F));
        UCHIGATANA_AUTO4 = new BasicAttackAnimation(0.1F, 0.467F, 0.567F, 0.833F, null, biped.toolR, "biped/new/dual_tachi/uchigatana_auto4", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter(1.5F));
        UCHIGATANA_AUTO5 = new BasicAttackAnimation(0.2F, 0.567F, 0.667F, 2.0F, null, biped.toolR, "biped/new/dual_tachi/uchigatana_auto5", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter(3F));
        UCHIGATANA_DASH = new YamtoAttackAnimation(0.05F, 0.0F, 2.87F, 2.87F, 2.87F, 0.81F, 2.87F, 0.81F, 0.83F,  "biped/new/dual_tachi/uchigatana_dash", biped,
                 new AttackAnimation.Phase(0.0F, 0.5F, 0.6F, 0.7F, 1.2F,1.8F, biped.toolR,null))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter(5F))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        DUAL_TACHI_AIRSLASH = new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.5F, ColliderPreset.DUAL_SWORD_AIR_SLASH, biped.torso, "biped/new/dual_tachi/dual_tachi_airslash", biped);
        DUAL_TACHI_AUTO1 = new BasicAttackAnimation(0.1F, "biped/new/dual_tachi/dual_tachi_auto1", biped,
                new AttackAnimation.Phase(0.0F, 0.367F, 0.41F, 0.567F, 1.3F, InteractionHand.OFF_HAND, biped.toolL, null),
                new AttackAnimation.Phase(0.2F, 0.633F, 0.68F, 0.767F, 1.3F,  biped.toolR, null))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter(1.5F))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);
        DUAL_TACHI_AUTO2 = new BasicAttackAnimation(0.15F, "biped/new/dual_tachi/dual_tachi_auto2", biped,
                new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, biped.toolR, null)
                , new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F,  biped.toolL, null))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter(2.5F));
        DUAL_TACHI_AUTO3= new BasicAttackAnimation(0.16F, "biped/new/dual_tachi/dual_tachi_auto3", biped,
                new AttackAnimation.Phase(0.0F, 0.66F, 0.69F, 0.733F, 1F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(biped.toolR, null), AttackAnimation.JointColliderPair.of(biped.toolL, null)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter( 2.5F ));
        DUAL_TACHI_AUTO4 = new BasicAttackAnimation(0.1F, "biped/new/dual_tachi/dual_tachi_auto4",biped,
                new AttackAnimation.Phase( 0.0F, 0.633F,0.69F, 0.8F, 1.167F, 1.65F,  InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(biped.toolR, null), AttackAnimation.JointColliderPair.of(biped.toolL, null)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG);
        DUAL_TACHI_AUTO5 = new BasicAttackAnimation(0.1F, "biped/new/dual_tachi/dual_tachi_auto5",biped,
                new AttackAnimation.Phase( 0.0F, 0.633F,0.69F, 0.8F, 1.167F, 1.65F,  InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(biped.toolR, null), AttackAnimation.JointColliderPair.of(biped.toolL, null)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG);

        UCHIGATANA_SKILL1 = new AttackAnimation(0.3F,"biped/new/dual_tachi/uchigatana_heavy1", biped,
                new AttackAnimation.Phase(  0.0F, 0.9F, 1.05F, 1.5F,1.5F, biped.toolR,null))
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.1F)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter( 6.5F ))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER,ValueModifier.setter( 25F ))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_LOCROT_TARGET)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_LOCROT_TARGET)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(1.25F, (entitypatch, animation, params) -> {
                            if (entitypatch.isLastAttackSuccess()) {
                                entitypatch.playAnimationSynchronized(UCHIGATANA_SKILL2, 0.0F);
                            }
                        }, AnimationEvent.Side.SERVER)
                );

        UCHIGATANA_SKILL2 = new AttackAnimation(0.05F,"biped/new/dual_tachi/uchigatana_heavy2", biped,
                new AttackAnimation.Phase(  0.0F, 1.2F, 1.35F, 1.35F,3F, biped.toolR,null))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER,ValueModifier.multiplier( 1.25F ))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,ValueModifier.setter( 10F ))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER,ValueModifier.setter( 3F ))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER,ValueModifier.setter( 25F ))
                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.TARGET_LOST_HEALTH.create(0.15F)))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addState(EntityState.MOVEMENT_LOCKED, true);




        TACHI_TWOHAND_AUTO_1 = new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.3F, null, biped.toolR, "biped/new/longsword/tachi_twohand_auto_1", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);
        TACHI_TWOHAND_AUTO_2 = new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.3F, null, biped.toolR, "biped/new/longsword/tachi_twohand_auto_2", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);
        TACHI_TWOHAND_AUTO_3 = new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.45F, null, biped.toolR, "biped/new/longsword/tachi_twohand_auto_3", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        TACHI_TWOHAND_AUTO_4 = new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.65F, null, biped.toolR, "biped/new/longsword/tachi_twohand_auto_4", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.adder(1.25F));
        TACHI_SLASH = new YamtoAttackAnimation(0.25F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, "biped/new/tachi_slash", biped,
                new AttackAnimation.Phase(0.0F, 0.15F, 0.20F, 0.21F, 0.30F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F)),
                new AttackAnimation.Phase(0.30F, 0.35F, 0.4F, 0.4F, 0.41F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F)),
                new AttackAnimation.Phase(0.65F, 0.75F, 0.85F, 1.15F, 1.25F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(5.0F)),
                new AttackAnimation.Phase(1.95F, 2.05F, 2.10F, 2.2F, Float.MAX_VALUE, biped.rootJoint, CorruptCollider.LETHAL_SLICING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.75F)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F).addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        KATANA_SKILL2 = new AttackAnimation(0.0F, 0.65F, 0.70F, 0.73F, 1.05F, CorruptCollider.YAMATO_P, biped.toolR, "biped/new/longsword/skill/katana_skill2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD);
        KATANA_SKILL3 = new AttackAnimation(0.05F, 0.06F, 0.10F, 0.35F, 0.6F, CorruptCollider.LETHAL_SLICING, biped.rootJoint, "biped/new/longsword/skill/katana_skill3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(15.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        RECOUNTER = (new DodgeAttackAnimation(0.02F, 0.01F, 0.15F, 0.26F, 1.15F, CorruptCollider.YAMATO_P0, biped.rootJoint, "biped/new/katana/skill/backward", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(5))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        GUARD = (new AttackAnimation(0.15F, "biped/new/longsword/skill/guard", biped,
                (new AttackAnimation.Phase(0.0F, 0.04F, 0.09F, 0.25F, 0.25F, biped.rootJoint, CorruptCollider.YAMATO_DASH)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(-10.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F)),
                (new AttackAnimation.Phase(0.25F, 0.31F, 0.36F, 0.65F, 0.65F, biped.rootJoint, CorruptCollider.YAMATO_DASH)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F))))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);


        RECOVER = (new DodgeAttackAnimation(0.02F, 0.01F, 0.15F, 0.26F, 1.15F, CorruptCollider.YAMATO_P0, biped.rootJoint, "biped/new/longsword/skill/recover", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(5))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        SHILED_SLASH = new AttackAnimation(0.02F, 0.01F, 0.15F, 0.26F, 1.15F,  null, biped.toolL,"biped/new/longsword/skill/shiled_slash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);

        SWORD_ONEHAND_AUTO1 = new BasicAttackAnimation(0.15F, 0.15F, 0.40F, 0.4F, null, biped.toolR, "biped/new/sword/sword_onehand_auto_1", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_AUTO2 = new BasicAttackAnimation(0.15F, 0.15F, 0.25F, 0.40F, null, biped.toolR, "biped/new/sword/sword_onehand_auto_2", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_AUTO3 = new BasicAttackAnimation(0.12F, 0.10F, 0.42F, 0.45F, null, biped.toolR, "biped/new/sword/sword_onehand_auto_3", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_AUTO4 = new BasicAttackAnimation(0.10F, 0.15F, 0.35F, 0.6F, null, biped.toolR, "biped/new/sword/sword_onehand_auto_4", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_DASH = (new DashAttackAnimation(0.12F, 0.1F, 0.25F, 0.4F, 0.65F, null, biped.toolR, "biped/new/sword/sword_onehand_dash", biped))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);
        DUAL_SWORD1 = new BasicAttackAnimation(0.15F, 0.15F, 0.40F, 0.4F, null, biped.toolR, "biped/new/sword/dual_auto1", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_SWORD2 = new BasicAttackAnimation(0.15F, 0.15F, 0.25F, 0.40F, null, biped.toolR, "biped/new/sword/dual_auto2", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_SWORD3 = new BasicAttackAnimation(0.12F, 0.10F, 0.42F, 0.45F, null, biped.toolR, "biped/new/sword/dual_auto3", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_IDLE = new StaticAnimation(true, "biped/new/sword/dual_idle", biped);
        D_BLADE_SLASH = new AttackAnimation(0.1F, "biped/new/sword/d_blade_slash", biped,
                new AttackAnimation.Phase(.0F, 0.2F, 0.31F, 0.4F, 0.4F, biped.toolR, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, biped.toolL, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.65F, 0.75F, 0.85F, 1.15F, Float.MAX_VALUE, biped.toolR, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);


        EXECUTE = new ExecuteAnimation(0.0F, 0.0F, 2.65F, 1.3F, 1.75F, 0.7F, 2.65F, 0.0F, 0.0F,  "biped/new/skill/execute", biped,
                new AttackAnimation.Phase(0.0F, 0.75F, 0.51F, 0.95F,  3.10F, biped.toolR, CorruptCollider.EXECUTE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, CorruptSound.DAMAGE.get()),
                new AttackAnimation.Phase(3F, 3.05F, 3.15F, 6.0F, Float.MAX_VALUE, biped.rootJoint, CorruptCollider.EXECUTE_SECOND)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(100.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.TARGET_LOST_HEALTH.create(0.25F))))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_LOCROT_TARGET)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_LOCROT_TARGET)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false)

                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0F, (entitypatch, animation, params) -> {
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.TimeStampedEvent.create(3.1F, STAMINASKILL, AnimationEvent.Side.SERVER))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        SK_EXECUTE = new YamtoAttackAnimation(0.0F, 0.0F, 2.65F, 1.3F, 1.75F, 0.7F, 2.65F, 0.0F, 0.0F, "biped/new/skill/sekiro", biped,
                new AttackAnimation.Phase(0.0F, 1.05F, 1.15F, 1.2F, 1.2F, biped.rootJoint, ColliderPreset.BIPED_BODY_COLLIDER)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.NEUTRALIZE_BOSSES.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(0.5F)),
                 new AttackAnimation.Phase(1.21F, 2.25F, 2.35F, 3.15F, Float.MAX_VALUE, biped.toolR, CorruptCollider.EXECUTE)
                         .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, CorruptSound.DAMAGE.get())
                         .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                         .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(100.0F))
                         .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_LOCROT_TARGET)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_LOCROT_TARGET)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 4.0F))
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);



        DAGGER_DUAL_AUTO1 = new BasicAttackAnimation(0.08F, 0.05F, 0.16F, 0.25F, null, biped.toolR, "biped/new/dagger/dagger_dual_auto1", biped);
        DAGGER_DUAL_AUTO2 = new BasicAttackAnimation(0.08F, 0.0F, 0.11F, 0.16F, InteractionHand.OFF_HAND, null, biped.toolL, "biped/new/dagger/dagger_dual_auto2", biped);
        DAGGER_DUAL_AUTO3 = new BasicAttackAnimation(0.08F, 0.0F, 0.11F, 0.2F, null, biped.toolR, "biped/new/dagger/dagger_dual_auto3", biped);
        DAGGER_DUAL_AUTO4 = new BasicAttackAnimation(0.13F, 0.1F, 0.21F, 0.4F, ColliderPreset.DUAL_DAGGER_DASH, biped.rootJoint, "biped/new/dagger/dagger_dual_auto4", biped)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true);
        BLADE_RUSH1 = new AttackAnimation(0.1F, 0.05F, 0.05F, 0.25F, Float.MAX_VALUE, CorruptCollider.BLADE_RUSH, biped.rootJoint, "biped/new/dagger/skill/blade_rush_first", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.35F))
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_UPDATE_TIME, TimePairList.create(0.0F, 0.25F))
//                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_ORIGIN_AS_TARGET_POSITION_BEGIN)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_DEST_LOCATION_BEGIN)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        BLADE_RUSH2 = new AttackAnimation(0.1F, 0.05F, 0.05F, 0.25F, Float.MAX_VALUE, CorruptCollider.BLADE_RUSH,  biped.rootJoint, "biped/new/dagger/skill/blade_rush_second", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.35F))
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_UPDATE_TIME, TimePairList.create(0.0F, 0.25F))
//                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_ORIGIN_AS_TARGET_POSITION_BEGIN)
//                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_ORIGIN_AS_TARGET_POSITION)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_DEST_LOCATION_BEGIN)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_DEST_LOCATION)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_GET, MoveCoordFunctions.WORLD_COORD)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        BLADE_RUSH3 = new AttackAnimation(0.1F, 0.05F, 0.05F, 0.25F, Float.MAX_VALUE, CorruptCollider.BLADE_RUSH,  biped.rootJoint,"biped/new/dagger/skill/blade_rush_third", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.35F))
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_UPDATE_TIME, TimePairList.create(0.0F, 0.25F))
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_DEST_LOCATION_BEGIN)
//                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_ORIGIN_AS_TARGET_POSITION_BEGIN)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        BLADE_RUSH4 = new AttackAnimation(0.15F, 0.05F, 0.15F, 0.35F, Float.MAX_VALUE, CorruptCollider.BLADE_RUSH, biped.rootJoint,"biped/new/dagger/skill/blade_rush_finisher", biped)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.35F))
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_UPDATE_TIME, TimePairList.create(0.0F, 0.25F))
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_DEST_LOCATION_BEGIN)
//                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_ORIGIN_AS_TARGET_POSITION_BEGIN)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
//        SANJI_IDLE = new StaticAnimation(0.1F, true, "biped/new/sanji/living/sanji_idle", biped);
//        SANJI_WALK = new MovementAnimation(0.1F, true, "biped/new/sanji/living/sanji_walk", biped);
//        SANJI_RUN = new MovementAnimation(0.1F, true, "biped/new/sanji/living/sanji_run", biped);
//        SANJI_AUTO_1 = new SanjiAttackAnimation(0.1F, 0.25F, 0.35F, 0.35F, CorruptCollider.KICK_HUGE, biped.legL, "biped/new/sanji/combat/sanji_auto_1", biped)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
//        SANJI_AUTO_2 = (new SanjiAttackAnimation(0.05F, "biped/new/sanji/combat/sanji_auto_2", biped,
//                new AttackAnimation.Phase(0.0F, 0.3F, 0.5F, 0.55F, 0.55F, biped.legR, CorruptCollider.KICK_HUGE),
//                new AttackAnimation.Phase(0.55F, 0.6F, 0.7F, 1.1F, Float.MAX_VALUE, biped.toolL, CorruptCollider.KICK_HUGE)))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG, 1)
//                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F), 1)
//                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
//                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
//        SANJI_AUTO_3 = (new SanjiAttackAnimation(0.1F, "biped/new/sanji/combat/sanji_auto_3", biped,
//                new AttackAnimation.Phase(0.0F, 0.25F, 0.35F, 0.4F, 0.4F, biped.toolL, CorruptCollider.KICK_HUGE),
//                new AttackAnimation.Phase(0.4F, 0.55F, 0.6F, 0.65F, 0.65F, biped.rootJoint, CorruptCollider.PARTYTABLE),
//                new AttackAnimation.Phase(0.65F, 0.7F, 0.75F, 0.8F, 0.8F, biped.rootJoint, CorruptCollider.PARTYTABLE),
//                new AttackAnimation.Phase(0.8F, 0.85F, 0.9F, 0.95F, 0.95F, biped.rootJoint, CorruptCollider.PARTYTABLE),
//                new AttackAnimation.Phase(0.95F, 1.0F, 1.05F, 1.1F, 1.1F, biped.rootJoint, CorruptCollider.PARTYTABLE),
//                new AttackAnimation.Phase(1.1F, 1.15F, 1.2F, 1.25F, 1.25F, biped.rootJoint, CorruptCollider.PARTYTABLE),
//                new AttackAnimation.Phase(1.25F, 1.3F, 1.35F, 1.4F, 1.4F, biped.rootJoint, CorruptCollider.PARTYTABLE),
//                new AttackAnimation.Phase(1.4F, 1.45F, 1.5F, 1.55F, 1.55F, biped.rootJoint, CorruptCollider.PARTYTABLE),
//                new AttackAnimation.Phase(1.55F, 1.6F, 1.65F, 1.7F, 1.7F, biped.rootJoint, CorruptCollider.PARTYTABLE),
//                new AttackAnimation.Phase(1.7F, 1.75F, 1.8F, 1.85F, 1.85F, biped.rootJoint, CorruptCollider.PARTYTABLE),
//                new AttackAnimation.Phase(1.85F, 2.1F, 2.2F, 2.2F, Float.MAX_VALUE, biped.rootJoint, CorruptCollider.PARTYTABLE)))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 1)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 2)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 3)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 4)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 5)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 6)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 7)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 8)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 9)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 10)
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 1)
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 2)
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 3)
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 4)
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 5)
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 6)
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 7)
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 8)
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 9)
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 10)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 2)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 3)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 4)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 5)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 5)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 6)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 7)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 8)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 9)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 10)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 10)
//                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3.0F), 10)
//                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
//                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
//        SANJI_AUTO_4 = (new SanjiAttackAnimation(0.05F, 0.55F, 0.65F, 1.0F, CorruptCollider.KICK_HUGE, biped.toolL, "biped/new/sanji/combat/sanji_auto_4", biped))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
//                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);
//        SANJI_CONCASSER = (new SanjiAttackAnimation(0.1F, 1.15F, 1.9F, 2.35F, CorruptCollider.PARTYTABLE, biped.rootJoint, "biped/combat/sanji_concasser", biped))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.2F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(8.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
//                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
//                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
//                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false);
//        SANJI_ANTIMANER = (new SanjiAttackAnimation(0.1F, 0.4F, 0.5F, 0.65F, CorruptCollider.KICK_HUGE, biped.toolL, "biped/combat/sanji_antimaner", biped))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
//                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
//                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false);
//        SANJI_IDLE = new StaticAnimation(0.1F, true, "biped/living/sanji_idle", biped);
//        SANJI_WALK = new MovementAnimation(0.1F, true, "biped/living/sanji_walk", biped);
//        SANJI_RUN = new MovementAnimation(0.1F, true, "biped/living/sanji_run", biped);
//        SANJI_DIABLE = (new SanjiAttackAnimation(0.1F, "biped/skill/sanji_diable", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 1.95F, 2.15F, 3.0F, Float.MAX_VALUE, biped.rootJoint, OPXEFColliders.PARTYTABLE)}))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
//                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.FIRE_AMBIENT)
//                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
//                .addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.55F, (entitypatch, self, params) -> {
//            ((LivingEntity)entitypatch.getOriginal()).m_9236_().m_6269_((Player)null, entitypatch.getOriginal(), SoundEvents.f_11705_, SoundSource.PLAYERS, 1.0F, 0.5F);
//            ((LivingEntity)entitypatch.getOriginal()).m_9236_().m_6269_((Player)null, entitypatch.getOriginal(), SoundEvents.f_11702_, SoundSource.PLAYERS, 1.0F, 0.5F);
//            ((PlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getDataManager().setDataSync(DiableJambuSkill.TRANSFORMING, true, (ServerPlayer)entitypatch.getOriginal());
//        }, Side.SERVER), TimeStampedEvent.create(2.15F, (entitypatch, self, params) -> {
//            ((LivingEntity)entitypatch.getOriginal()).m_9236_().m_6269_((Player)null, entitypatch.getOriginal(), SoundEvents.f_11937_, SoundSource.PLAYERS, 1.0F, 0.5F);
//            ((LivingEntity)entitypatch.getOriginal()).m_9236_().m_6269_((Player)null, entitypatch.getOriginal(), SoundEvents.f_11702_, SoundSource.PLAYERS, 1.0F, 0.5F);
//            ((ServerPlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getDataManager().setDataSync(DiableJambuSkill.ACTIVE, true, (ServerPlayer)entitypatch.getOriginal());
//            ((ServerPlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getDataManager().setDataSync(DiableJambuSkill.TRANSFORMING, false, (ServerPlayer)entitypatch.getOriginal());
//        }, Side.SERVER)});
//        SANJI_DIABLE_AUTO_1 = (new BasicAttackAnimation(0.1F, 0.45F, 0.55F, 0.7F, OPXEFColliders.KICK_HUGE, biped.legR, "biped/skill/sanji_diable_auto_1", biped))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.25F);
//        SANJI_DIABLE_AUTO_2 = (new SanjiAttackAnimation(0.1F, "biped/skill/sanji_diable_auto_2", biped,
//                new AttackAnimation.Phase(0.0F, 0.25F, 0.3F, 0.35F, 0.35F, biped.legR, CorruptCollider.KICK_HUGE),
//                new AttackAnimation.Phase(0.35F, 0.4F, 0.45F, 0.5F, 0.5F, biped.legR, CorruptCollider.KICK_HUGE),
//                new AttackAnimation.Phase(0.5F, 0.55F, 0.6F, 0.65F, 0.65F, biped.legR, CorruptCollider.KICK_HUGE),
//                new AttackAnimation.Phase(0.65F, 0.7F, 0.75F, 0.8F, 0.8F, biped.legR, CorruptCollider.KICK_HUGE),
//                new AttackAnimation.Phase(0.8F, 0.85F, 0.9F, 0.95F, 0.95F, biped.legR, CorruptCollider.KICK_HUGE),
//                new AttackAnimation.Phase(0.95F, 1.1F, 1.2F, 1.25F, Float.MAX_VALUE, biped.legR, CorruptCollider.KICK_HUGE)))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 1)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 2)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 3)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 4)
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 5)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 2)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 3)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 4)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 4)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 5)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG, 5)
//                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F), 5)
//                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.25F);
//        SANJI_DIABLE_AUTO_3 = (new SanjiAttackAnimation(0.05F, "biped/skill/sanji_diable_auto_3", biped,
//                new AttackAnimation.Phase(0.0F, 0.45F, 0.65F, 0.7F, 0.7F, biped.legR, CorruptCollider.ANTIMANER),
//                new AttackAnimation.Phase(0.7F, 1.2F, 1.4F, 1.6F, Float.MAX_VALUE, biped.legR, CorruptCollider.KICK_HUGE)))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
//                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG, 1)
//                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
//                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.25F);
//        SANJI_DIABLE_AUTO_4 = (new BasicAttackAnimation(0.1F, 0.45F, 0.6F, 0.85F, CorruptCollider.KICK_HUGE, biped.legR, "biped/skill/sanji_diable_auto_4", biped))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
//                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
//                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.25F)
//                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
//                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false);
//        SANJI_DIABLE_ANTIMANER = (new BasicAttackAnimation(0.1F, 0.4F, 0.5F, 0.65F, CorruptCollider.KICK_HUGE, biped.legR, "biped/skill/sanji_diable_antimaner", biped))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
//                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.45F)
//                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false);
//        SANJI_DIABLE_IDLE = new StaticAnimation(0.1F, true, "biped/living/sanji_diable_idle", biped);
//        SANJI_DIABLE_WALK = new MovementAnimation(0.1F, true, "biped/living/sanji_diable_walk", biped);
//        CONQUEROR_HAKI = (new BasicAttackAnimation(0.05F, 0.2F, 1.2F, 1.9F, CorruptCollider.CONQUEROR_HAKI, biped.rootJoint, "biped/combat/conqueror_haki", biped))
//                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(10.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(5.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(100.0F))
//                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
//                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.f_144245_).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
//                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
//                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
//                .addEvents(new AnimationEvent.TimeStampedEvent[]{TimeStampedEvent.create(0.0F, (entitypatch, self, params) -> {
//            ((LivingEntity)entitypatch.getOriginal()).m_9236_().m_6269_((Player)null, entitypatch.getOriginal(), (SoundEvent)OPXEFSounds.CONQUEROR_HAKI.get(), SoundSource.PLAYERS, 0.4F, 1.0F);
//        }, Side.SERVER), TimeStampedEvent.create(0.2F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.25F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.3F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.4F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.45F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.5F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.55F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.6F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.65F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.7F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.75F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.8F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.85F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.9F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(0.95F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(1.0F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(1.05F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(1.1F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(1.15F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER), TimeStampedEvent.create(1.2F, OPXEFAnimations.ReuseableEvents.HAKI_AURA, Side.SERVER)});
//
//



    }


    private static final AnimationEvent.AnimationEventConsumer YAMATO_OUT = (entitypatch, animation, params) -> {
        if (entitypatch.getOriginal() instanceof Player) {
            entitypatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("unsheathed", true);
        }
    };
    private static final AnimationEvent.AnimationEventConsumer YAMATO_IN = (entitypatch, animation, params) -> {
        if (entitypatch.getOriginal() instanceof Player) {
            entitypatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("unsheathed", false);
        }
    };


    public static final AnimationProperty.PlaybackSpeedModifier SPEED = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 1.1F;

    public static final AnimationProperty.PlaybackSpeedModifier SLOW_SPEED = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 0.7F;

    public static final AnimationProperty.PlaybackSpeedModifier EXECUT = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 0.85F;


    public static final AnimationEvent.AnimationEventConsumer STAMINA = (entitypatch, animation, params) -> {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            float currentStamina = playerPatch.getStamina();
            float maxStamina = playerPatch.getMaxStamina();
            float recoveredStamina = maxStamina * 0.15F;
            playerPatch.setStamina(currentStamina + recoveredStamina);
        }
    };

    public static final AnimationEvent.AnimationEventConsumer STAMINAR = (entitypatch, animation, params) -> {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            float currentStamina = playerPatch.getStamina();
            float maxStamina = playerPatch.getMaxStamina();
            float recoveredStamina = maxStamina * 0.05F;
            playerPatch.setStamina(currentStamina + recoveredStamina);
        }
    };

    public static final AnimationEvent.AnimationEventConsumer STAMINASKILL = (entitypatch, animation, params) -> {
        if (entitypatch instanceof PlayerPatch) {
            PlayerPatch<?> playerPatch = (PlayerPatch<?>) entitypatch;
            float maxStamina = playerPatch.getMaxStamina();
            float currentStamina = playerPatch.getStamina();
            float recoveredStamina = maxStamina * 0.25F;
            playerPatch.setStamina(currentStamina + recoveredStamina);
        }
    };
    private static final AnimationEvent.AnimationEventConsumer COMBO_BREAK = (entitypatch,animation, params) -> {
        if (entitypatch instanceof PlayerPatch) {
            ((PlayerPatch<?>) entitypatch).getSkill(SkillCategories.BASIC_ATTACK.universalOrdinal()).getDataManager().setData(SkillDataKeys.COMBO_COUNTER.get(), 0);
        }
    };

}


