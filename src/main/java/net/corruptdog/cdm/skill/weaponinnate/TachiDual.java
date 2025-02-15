package net.corruptdog.cdm.skill.weaponinnate;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.gameasset.CorruptSound;
import net.corruptdog.cdm.network.server.NetworkManager;
import net.corruptdog.cdm.network.server.SPAfterImagine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class TachiDual extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("3fa26bbc-d14e-11ed-afa1-0242ac120002");

    public TachiDual(Builder<? extends Skill> builder) {
        super(builder);
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
                attackEvent.getPlayerPatch().reserveAnimation(CorruptAnimations.SK_EXECUTE);
                HIT(attackEvent.getPlayerPatch().getOriginal(), attackEvent.getPlayerPatch().getOriginal().level());
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
    private static void HIT(Player player, Level level) {
        if (player == null) return;
        if (!level.isClientSide())
        {
            final Vec3 _center = new Vec3(player.getX(), player.getEyeY(), player.getZ());
            List<LivingEntity> _entfound = level.getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                    .limit(2)
                    .toList();
            for (LivingEntity entityiterator : _entfound) {
                LivingEntityPatch<?> ep = EpicFightCapabilities.getEntityPatch(entityiterator, LivingEntityPatch.class);
                if (ep != null && (entityiterator != player)) {
                    ep.playSound(CorruptSound.EXECUTE.get(), 0.1F, 0.1F);
                    ep.playAnimationSynchronized(CorruptAnimations.EXECUTED, 0.0F, SPPlayAnimation::new);
                }
            }
        }
    }
    private void REBACKWARD(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.BACKWARD_SLASH, 0F);
        executer.playSound(EpicFightSounds.CLASH.get(), -0.05F, 0.1F);

    }
    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.playAnimationSynchronized(CorruptAnimations.CLASH, 0.0F);
        super.executeOnServer(executer, args);
    }
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
    }
}