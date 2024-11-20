package net.corruptdog.cdm.skill.weaponinnate;

import java.util.UUID;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.network.server.NetworkManager;
import net.corruptdog.cdm.network.server.SPAfterImagine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class KatanaSpSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("3fa26bbc-d14e-11ed-afa1-0242ac120002");

    private AttackAnimationProvider first;
    private AttackAnimationProvider second;
    private AttackAnimationProvider third;

    public KatanaSpSkill(Builder<? extends Skill> builder) {
        super(builder);
        this.first = () -> (AttackAnimation) CorruptAnimations.CLASH;
        this.second = () -> (AttackAnimation)CorruptAnimations.BLADE_RUSH_FINISHER;
        this.third = () -> (AttackAnimation)CorruptAnimations.KATANA_SKILL3;
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        PlayerEventListener listener = container.getExecuter().getEventListener();
        listener.addEventListener(EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, (attackEvent) -> {
            int id = attackEvent.getAnimation().getId();
            if (id == CorruptAnimations.CLASH.getId()) {
                attackEvent.getPlayerPatch().reserveAnimation(CorruptAnimations.KATANA_SKILL3);
            }
            if (id == CorruptAnimations.BACKWARD_SLASH.getId()) {
                attackEvent.getPlayerPatch().reserveAnimation(CorruptAnimations.BLADE_RUSH_FINISHER);
            }
            if (id == CorruptAnimations.BLADE_RUSH_FINISHER.getId()) {
                attackEvent.getPlayerPatch().reserveAnimation(CorruptAnimations.FATAL_DRAW);
            }
            listener.addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID, (hurtEvent) -> {
                ServerPlayerPatch executer = hurtEvent.getPlayerPatch();
                AnimationPlayer animationPlayer = executer.getAnimator().getPlayerFor(null);
                float elapsedTime = animationPlayer.getElapsedTime();
                if (elapsedTime <= 0.45F) {
                    int animationId = executer.getAnimator().getPlayerFor(null).getAnimation().getId();
                    if (animationId == CorruptAnimations.CLASH.getId()) {
                        DamageSource damagesource = hurtEvent.getDamageSource();
                        if (!damagesource.is(DamageTypeTags.IS_EXPLOSION)
                                && !damagesource.is(DamageTypes.MAGIC)
                                && !damagesource.is(DamageTypeTags.BYPASSES_ARMOR)
                                && !damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                            ServerPlayer serverPlayer = (ServerPlayer)container.getExecuter().getOriginal();
                            SPAfterImagine msg = new SPAfterImagine(serverPlayer.position(), serverPlayer.getId());
                            NetworkManager.sendToAllPlayerTrackingThisEntityWithSelf(msg, serverPlayer);
                            REBACKWARD(executer);
                        }
                    }
                }
            });
        });
    }
    private void REBACKWARD(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.BACKWARD_SLASH, 0F);
        executer.playSound(EpicFightSounds.CLASH.get(), -0.05F, 0.1F);

    }
    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.playAnimationSynchronized(this.first.get(), 0.0F);
        super.executeOnServer(executer, args);
    }

}