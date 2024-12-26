package net.corruptdog.cdm.api.animation.types;

import java.util.Locale;
import java.util.Optional;

import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.EntityState.StateFactor;
import yesman.epicfight.api.animation.types.StateSpectrum;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.datastruct.TypeFlexibleHashMap;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.config.EpicFightOptions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.gamerule.EpicFightGamerules;

import javax.annotation.Nullable;

public class SanjiAttackAnimation extends AttackAnimation {


    protected final StateSpectrum.Blueprint stateUtilsBlueprint = new StateSpectrum.Blueprint();
    private final StateSpectrum stateUtils = new StateSpectrum();

    public SanjiAttackAnimation(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, antic, contact, recovery, collider, colliderJoint, path, armature);
        this.stateUtilsBlueprint.clear();
        AttackAnimation.Phase[] var13 = phases;
        int var14 = phases.length;
        this.addProperty(ActionAnimationProperty.STOP_MOVEMENT, true);
    }



    public SanjiAttackAnimation(float convertTime, String path, Armature armature, Phase... phases) {
        this(convertTime, path, armature, false, phases);
    }

    public SanjiAttackAnimation(float convertTime, String path, Armature armature, boolean Coordsetter, AttackAnimation.Phase... phases) {
        super(convertTime, path, armature, phases);
    }


    @Override
    protected void bindPhaseState(Phase phase) {
        float preDelay = phase.preDelay;

        this.stateSpectrumBlueprint
                .newTimePair(phase.start, preDelay)
                .addState(EntityState.PHASE_LEVEL, 1)
                .newTimePair(phase.start, phase.end+1F)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.UPDATE_LIVING_MOTION, false)
                .newTimePair(phase.start, phase.end)
                .addState(EntityState.INACTION, true)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .newTimePair(preDelay, phase.contact + 0.01F)
                .addState(EntityState.ATTACKING, true)
                .addState(EntityState.PHASE_LEVEL, 2)
                .newTimePair(phase.contact + 0.01F, phase.end)
                .addState(EntityState.PHASE_LEVEL, 3)
                .addState(EntityState.TURNING_LOCKED, true);
    }

    @Override
    public void postInit() {
        super.postInit();

        if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.2f", (1.0F / this.getTotalTime())));
            this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
        }
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, DynamicAnimation nextAnimation, boolean isEnd) {
        super.end(entitypatch, nextAnimation, isEnd);

        boolean stiffAttack = entitypatch.getOriginal().level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS).get();

        if (!isEnd && !nextAnimation.isMainFrameAnimation() && entitypatch.isLogicalClient() && !stiffAttack) {
            float playbackSpeed = EpicFightOptions.A_TICK * this.getPlaySpeed(entitypatch, this);
            entitypatch.getClientAnimator().baseLayer.copyLayerTo(entitypatch.getClientAnimator().baseLayer.getLayer(Layer.Priority.HIGHEST), playbackSpeed);
        }
    }

    @Override
    public TypeFlexibleHashMap<StateFactor<?>> getStatesMap(LivingEntityPatch<?> entitypatch, float time) {
        TypeFlexibleHashMap<StateFactor<?>> stateMap = super.getStatesMap(entitypatch, time);

        if (!entitypatch.getOriginal().level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS).get()) {
            stateMap.put(EntityState.MOVEMENT_LOCKED, (Object)false);
            stateMap.put(EntityState.UPDATE_LIVING_MOTION, (Object)true);
        }

        return stateMap;
    }

    @Override
    protected Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        Vec3 vec3 = super.getCoordVector(entitypatch, dynamicAnimation);

        if (entitypatch.shouldBlockMoving() && this.getProperty(ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3 = vec3.scale(0.0F);
        }

        return vec3;
    }

    @Override
    public Optional<JointMaskEntry> getJointMaskEntry(LivingEntityPatch<?> entitypatch, boolean useCurrentMotion) {
        if (entitypatch.isLogicalClient()) {
            if (entitypatch.getClientAnimator().getPriorityFor(this) == Layer.Priority.HIGHEST) {
                return Optional.of(JointMaskEntry.BASIC_ATTACK_MASK);
            }
        }

        return super.getJointMaskEntry(entitypatch, useCurrentMotion);
    }

    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }

    @Override
    public boolean shouldPlayerMove(LocalPlayerPatch playerpatch) {
        if (playerpatch.isLogicalClient()) {
            if (!playerpatch.getOriginal().level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS).get()) {
                if (playerpatch.getOriginal().input.forwardImpulse != 0.0F || playerpatch.getOriginal().input.leftImpulse != 0.0F) {
                    return false;
                }
            }
        }

        return true;
    }
}