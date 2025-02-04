package net.corruptdog.cdm.api.animation.types;

import java.util.Locale;

import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class YamtoAttackAnimation extends AttackAnimation {


    protected final StateSpectrum.Blueprint stateUtilsBlueprint = new StateSpectrum.Blueprint();
    private final StateSpectrum stateUtils = new StateSpectrum();
    protected final float attackstart;
    protected final float attackwinopen;
    protected final float attackwinclose;
    protected final float skillwinopen;
    protected final float skillwinclose;
    protected final float attackend;
    protected final float lockon;
    protected final float lockoff;

    public YamtoAttackAnimation(float convertTime, float attackstart, float attackend, float attackwinopen, float attackwinclose, float skillwinopen, float skillwinclose, float lockon, float lockoff, String path, Armature armature, AttackAnimation.Phase... phases) {
        super(convertTime, path, armature, phases);


        this.attackstart = attackstart;
        this.attackwinopen = attackwinopen;
        this.attackwinclose = attackwinclose;
        this.skillwinopen = skillwinopen;
        this.skillwinclose = skillwinclose;
        this.lockon = lockon;
        this.lockoff = lockoff;
        this.attackend = attackend;
        this.stateUtilsBlueprint.clear();
        AttackAnimation.Phase[] var13 = phases;
        int var14 = phases.length;
        this.addProperty(ActionAnimationProperty.STOP_MOVEMENT, true);
    }


    @Override
    protected void bindPhaseState(Phase phase) {
        float preDelay = phase.preDelay;

        this.stateSpectrumBlueprint
                .newTimePair(attackstart, attackwinopen)
                .addState(EntityState.CAN_BASIC_ATTACK, false)

                .newTimePair( phase.contact, phase.recovery)
                .addState(EntityState.CAN_BASIC_ATTACK, false)

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
                .addState(EntityState.TURNING_LOCKED, true)

                .newTimePair(attackwinclose ,skillwinclose)
                .addState(EntityState.PHASE_LEVEL, 3)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.CAN_BASIC_ATTACK, false);
    }
    @Override
    public void setLinkAnimation(DynamicAnimation dynamicAnimation, Pose pose1, boolean reverse, float timeModifier, LivingEntityPatch<?> entitypatch, LinkAnimation dest) {
        float extTime = Math.max(this.convertTime + timeModifier, 0);

        if (entitypatch instanceof PlayerPatch<?> playerpatch) {
            Phase phase = this.getPhaseByTime(playerpatch.getAnimator().getPlayerFor(this).getElapsedTime());
            extTime *= this.getTotalTime() * playerpatch.getAttackSpeed(phase.getHand());
        }

        extTime = Math.max(extTime - this.convertTime, 0);
        super.setLinkAnimation(dynamicAnimation, pose1, reverse, this.convertTime, entitypatch, dest);
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
    protected Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        Vec3 vec3 = super.getCoordVector(entitypatch, dynamicAnimation);

        if (entitypatch.shouldBlockMoving() && this.getProperty(ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3 = vec3.scale(0.0F);
        }

        return vec3;
    }
    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }
}