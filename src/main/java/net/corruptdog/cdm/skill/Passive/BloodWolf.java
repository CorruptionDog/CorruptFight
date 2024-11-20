package net.corruptdog.cdm.skill.Passive;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.gameasset.CorruptSound;
import net.corruptdog.cdm.network.server.NetworkManager;
import net.corruptdog.cdm.network.server.SPAfterImagine;
import net.corruptdog.cdm.skill.CDSkillDataKeys;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class BloodWolf extends PassiveSkill {
    private static final UUID EVENT_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    private static final ResourceLocation BLOODWOLF_TEXTURE = new ResourceLocation("cdmoveset", "textures/gui/skills/bloodwolf.png");
    private static final int MAX_WOLFPASSIVE = 10;
    private static final int TIMER_DURATION = 800;
    private static final int TIMER_INTERVAL = 40;
    private static final int MAX_DAMAGE = 5;

    public BloodWolf(Builder<? extends Skill> builder) {
        super(builder);
    }
    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        container.getExecuter().getEventListener().addEventListener(EventType.DODGE_SUCCESS_EVENT, EVENT_UUID, (event) -> {
            Integer wolfPassive = container.getDataManager().getDataValue(CDSkillDataKeys.WOLFPASSIVE.get());
            if (wolfPassive == null) {
                wolfPassive = 0;
            }
            if (wolfPassive < MAX_WOLFPASSIVE) {
                container.getDataManager().setDataSync(CDSkillDataKeys.WOLFPASSIVE.get(), wolfPassive + 1, event.getPlayerPatch().getOriginal());
                System.out.println(container.getDataManager().getDataValue(CDSkillDataKeys.WOLFPASSIVE.get()));
                if (wolfPassive + 1 == MAX_WOLFPASSIVE) {
                    event.getPlayerPatch().playSound(CorruptSound.SKILL.get(), 0.8F, 1.2F);
                    container.getDataManager().setDataSync(CDSkillDataKeys.TIMER.get(), TIMER_DURATION, event.getPlayerPatch().getOriginal());
                }
            }
        });
        container.getExecuter().getEventListener().addEventListener(EventType.MODIFY_DAMAGE_EVENT, EVENT_UUID, (event) -> {
            Integer damage = container.getDataManager().getDataValue(CDSkillDataKeys.DAMAGE.get());
            if (damage == null) {
                damage = 0;
            }
            if (damage > 5) {
                float attackDamage = event.getDamage();
                event.setDamage(attackDamage * 1.50F);
            }
        });

        container.getExecuter().getEventListener().addEventListener(EventType.DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID, (event) -> {
            Integer wolfPassive = container.getDataManager().getDataValue(CDSkillDataKeys.WOLFPASSIVE.get());
            if (wolfPassive == null) {
                wolfPassive = 0;
            }
            if (event.getAttackDamage() > event.getTarget().getHealth()) {
                if (wolfPassive < MAX_WOLFPASSIVE) {
                    if (TIMER_INTERVAL != 0 && wolfPassive + 1 != MAX_WOLFPASSIVE) {
                        container.getDataManager().setDataSync(CDSkillDataKeys.WOLFPASSIVE.get(), wolfPassive + 1, event.getPlayerPatch().getOriginal());
                    }
                }
            }
        });
        container.getExecuter().getEventListener().addEventListener(EventType.HURT_EVENT_PRE, EVENT_UUID, (event) -> {
            Integer wolfPassive = container.getDataManager().getDataValue(CDSkillDataKeys.WOLFPASSIVE.get());
            if (wolfPassive == null) {
                wolfPassive = 0;
            }
            if (wolfPassive == MAX_WOLFPASSIVE && event.getDamageSource().getEntity() != null) {
                double probability = 0.35;
                if (Math.random() < probability) {
                    event.getPlayerPatch().playSound(CorruptSound.HURT.get(), 0.8F, 1.2F);
                    event.getPlayerPatch().playSound(EpicFightSounds.BLADE_HIT.get(), 0.8F, 1.2F);
                    ServerPlayer playerentity = event.getPlayerPatch().getOriginal();
                    DamageSource damageSource = event.getDamageSource();
                    EpicFightParticles.HIT_BLADE.get().spawnParticleWithArgument(playerentity.serverLevel(), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerentity, damageSource.getDirectEntity());
                    event.getPlayerPatch().playAnimationSynchronized(Animations.SWORD_GUARD_ACTIVE_HIT3, 0);
                    event.setCanceled(true);
                    event.setResult(AttackResult.ResultType.BLOCKED);
                    HIT(event.getPlayerPatch().getOriginal(), event.getPlayerPatch().getOriginal().level());
                }
            }
        });
    }
    private static void HIT(Player player, Level level) {
        if (player == null) return;
        if (!level.isClientSide())
        {
            final Vec3 _center = new Vec3(player.getX(), player.getEyeY(), player.getZ());
            List<LivingEntity> _entfound = level.getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (LivingEntity entityiterator : _entfound) {
                LivingEntityPatch<?> ep = EpicFightCapabilities.getEntityPatch(entityiterator, LivingEntityPatch.class);
                if (ep != null && (entityiterator != player)) {
                    ep.playAnimationSynchronized(CorruptAnimations.BIPED_HIT_LONG2, 0.0F, SPPlayAnimation::new);
                }
            }
        }
    }


    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(EventType.DODGE_SUCCESS_EVENT, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_PRE, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(EventType.MODIFY_DAMAGE_EVENT, EVENT_UUID);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y) {
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.translate(0.0F, (float)gui.getSlidingProgression(), 0.0F);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        String string;
        int wolfPassive = container.getDataManager().getDataValue(CDSkillDataKeys.WOLFPASSIVE.get());
        if (wolfPassive == MAX_WOLFPASSIVE + 1) {
            RenderSystem.setShaderColor(0.5F, 0.5F, 0.5F, 0.5F);
            guiGraphics.blit(this.getSkillTexture(), (int)x, (int)y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);
            string = String.valueOf(container.getDataManager().getDataValue(CDSkillDataKeys.TIMER.get()) / TIMER_INTERVAL);
        } else if (wolfPassive < MAX_WOLFPASSIVE) {
            string = String.valueOf(wolfPassive);
            guiGraphics.blit(BLOODWOLF_TEXTURE, (int)x, (int)y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);
        } else {
            string = String.valueOf(container.getDataManager().getDataValue(CDSkillDataKeys.TIMER.get()) / TIMER_INTERVAL);
            guiGraphics.blit(BLOODWOLF_TEXTURE, (int)x, (int)y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.drawString(gui.font, string, x + 13.0F - (float)(gui.font.width(string)), y + 10.0F, 16777215, true);
        poseStack.pushPose();
    }

    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (!container.getExecuter().isLogicalClient()) {
            int timer = container.getDataManager().getDataValue(CDSkillDataKeys.TIMER.get());
            int wolfPassive = container.getDataManager().getDataValue(CDSkillDataKeys.WOLFPASSIVE.get());
            if (timer == 0 && wolfPassive == MAX_WOLFPASSIVE) {
                container.getExecuter().getOriginal().playSound(CorruptSound.HURT.get());}
            if (timer == 0 && wolfPassive == MAX_WOLFPASSIVE + 1) {
                container.getExecuter().getOriginal().playSound(CorruptSound.HURT.get());}
            if (timer == TIMER_DURATION && wolfPassive == MAX_WOLFPASSIVE) {
                container.getDataManager().setDataSync(CDSkillDataKeys.DAMAGE.get(), MAX_DAMAGE + 1, ((ServerPlayerPatch)container.getExecuter()).getOriginal());
                ServerPlayer serverPlayer = (ServerPlayer)container.getExecuter().getOriginal();
                SPAfterImagine msg = new SPAfterImagine(serverPlayer.position(), serverPlayer.getId());
                NetworkManager.sendToAllPlayerTrackingThisEntityWithSelf(msg, serverPlayer);}
            if (timer > 0) {
                container.getDataManager().setDataSync(CDSkillDataKeys.TIMER.get(), timer - 1, ((ServerPlayerPatch)container.getExecuter()).getOriginal());
                if (timer == 1) {
                    container.getDataManager().setDataSync(CDSkillDataKeys.TIMER.get(), TIMER_DURATION, ((ServerPlayerPatch)container.getExecuter()).getOriginal());
                    ServerPlayer serverPlayer = (ServerPlayer)container.getExecuter().getOriginal();
                    SPAfterImagine msg = new SPAfterImagine(serverPlayer.position(), serverPlayer.getId());
                    NetworkManager.sendToAllPlayerTrackingThisEntityWithSelf(msg, serverPlayer);
                    container.getDataManager().setDataSync(CDSkillDataKeys.DAMAGE.get(), 0, ((ServerPlayerPatch)container.getExecuter()).getOriginal());
                    if (wolfPassive == MAX_WOLFPASSIVE) {
                        container.getDataManager().setDataSync(CDSkillDataKeys.WOLFPASSIVE.get(), MAX_WOLFPASSIVE + 1, ((ServerPlayerPatch)container.getExecuter()).getOriginal());
                    } else {
                        container.getDataManager().setDataSync(CDSkillDataKeys.WOLFPASSIVE.get(), 0, ((ServerPlayerPatch)container.getExecuter()).getOriginal());
                        container.getDataManager().setDataSync(CDSkillDataKeys.DAMAGE.get(), 0, ((ServerPlayerPatch)container.getExecuter()).getOriginal());
                    }
                }
            }
        }
    }
}