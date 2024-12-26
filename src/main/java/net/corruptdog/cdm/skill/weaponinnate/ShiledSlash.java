package net.corruptdog.cdm.skill.weaponinnate;

import com.google.common.collect.Maps;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType.HURT_EVENT_PRE;

public class ShiledSlash extends WeaponInnateSkill {
    private final Map<ResourceLocation, Supplier<AttackAnimation>> comboAnimation = Maps.newHashMap();
    private static final UUID EVENT_UUID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    public ShiledSlash(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        PlayerEventListener listener = container.getExecuter().getEventListener();
        listener.addEventListener(HURT_EVENT_PRE, EVENT_UUID, (event) -> {
            ServerPlayerPatch executer = event.getPlayerPatch();
            AnimationPlayer animationPlayer = executer.getAnimator().getPlayerFor(null);
            float elapsedTime = animationPlayer.getElapsedTime();
            int animationId = executer.getAnimator().getPlayerFor(null).getAnimation().getId();
            if (elapsedTime <= 0.25F) {
                if (animationId == CorruptAnimations.SHILED_SLASH.getId()) {
                    DamageSource damagesource = event.getDamageSource();
                    Vec3 sourceLocation = damagesource.getSourcePosition();
                    if (sourceLocation != null) {
                        Vec3 viewVector = event.getPlayerPatch().getOriginal().getViewVector(1.0F);
                        Vec3 toSourceLocation = sourceLocation.subtract(event.getPlayerPatch().getOriginal().position()).normalize();
                        if (toSourceLocation.dot(viewVector) > 0.0D) {
                            if (!damagesource.is(DamageTypeTags.IS_EXPLOSION)
                                    && !damagesource.is(DamageTypes.MAGIC)
                                    && !damagesource.is(DamageTypeTags.BYPASSES_ARMOR)
                                    && !damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                                   event.getPlayerPatch().playSound(SoundEvents.SHIELD_BLOCK, 0.8F, 1.2F);
                                   event.setCanceled(true);
                                   event.setResult(AttackResult.ResultType.BLOCKED);
                                   HIT(event.getPlayerPatch().getOriginal(), event.getPlayerPatch().getOriginal().level());
                            }
                        }
                    }
                }
            }
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
                    ep.playSound(EpicFightSounds.NEUTRALIZE_BOSSES.get(), 0.1F, 0.1F);
                    ep.playAnimationSynchronized(Animations.BIPED_COMMON_NEUTRALIZED, 0.0F, SPPlayAnimation::new);
                }
            }
        }
    }
    @Override
    public void onRemoved(SkillContainer container) {
        PlayerPatch<?> executer = container.getExecuter();
        executer.getEventListener().removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID, 0);
    }
    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.playAnimationSynchronized(CorruptAnimations.SHILED_SLASH, 0.0F);
        super.executeOnServer(executer, args);
    }
}
