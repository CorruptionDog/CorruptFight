package net.corruptdog.cdm.skill.identity;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.gameasset.CorruptSound;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Comparator;
import java.util.List;
import java.util.Set;


@Mod.EventBusSubscriber
public class Execute {
    private static final List<CapabilityItem.WeaponCategories> executeCategories = List.of(CapabilityItem.WeaponCategories.SWORD);

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickEntity(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != event.getEntity().getUsedItemHand()) return;
        execute(event.getEntity(), event.getEntity().level());
    }

    public static void execute(Player player, Level level) {
        if (player == null) return;
        if (!level.isClientSide()) {
            Vec3 playerPosition = new Vec3(player.getX(), player.getEyeY(), player.getZ());
            List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, new AABB(playerPosition, playerPosition).inflate(3), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(playerPosition)))
                    .toList();

            for (LivingEntity targetEntity : nearbyEntities) {
                LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(targetEntity, LivingEntityPatch.class);
                if (targetPatch != null && targetEntity != player) {
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (isAnimationValid(targetPatch, playerPatch)) {
                        handleExecution(player, targetPatch, playerPatch);
                        break;
                    }
                }
            }
        }
    }

    private static boolean isAnimationValid(LivingEntityPatch<?> targetPatch, PlayerPatch<?> playerPatch) {
        return (targetPatch.getAnimator().getPlayerFor(null).getAnimation() instanceof StaticAnimation staticAnimation && staticAnimation == Animations.BIPED_KNEEL) ||
                (targetPatch.getAnimator().getPlayerFor(null).getAnimation() instanceof LongHitAnimation longHitAnimation &&
                        Set.of(Animations.WITHER_NEUTRALIZED, Animations.VEX_NEUTRALIZED, Animations.SPIDER_NEUTRALIZED,
                                Animations.DRAGON_NEUTRALIZED, Animations.ENDERMAN_NEUTRALIZED,
                                Animations.BIPED_COMMON_NEUTRALIZED, Animations.GREATSWORD_GUARD_BREAK).contains(longHitAnimation));
    }

    private static void handleExecution(Player player, LivingEntityPatch<?> targetPatch, PlayerPatch<?> playerPatch) {
        Vec3 viewVec = targetPatch.getOriginal().getViewVector(1.0F);
        playerPatch.setGrapplingTarget(targetPatch.getOriginal());
        if (playerPatch instanceof LocalPlayerPatch localPlayerPatch) {
            localPlayerPatch.setLockOn(true);
            localPlayerPatch.toggleLockOn();
        }
        player.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 50, 1));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50));
        targetPatch.getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));
        targetPatch.playSound(CorruptSound.EXECUTE.get(), 1.0F, 1.0F);
        player.teleportTo(targetPatch.getOriginal().getX() + viewVec.x() * 1.85, targetPatch.getOriginal().getY(), targetPatch.getOriginal().getZ() + viewVec.z() * 1.85);
        playerPatch.playAnimationSynchronized(CorruptAnimations.EXECUTE, 0.0F);
        targetPatch.playAnimationSynchronized(CorruptAnimations.GUARD_BREAK1, 0.35F, SPPlayAnimation::new);
    }
}