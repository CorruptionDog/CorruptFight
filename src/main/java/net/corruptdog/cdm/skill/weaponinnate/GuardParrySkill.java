package net.corruptdog.cdm.skill.weaponinnate;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import static yesman.epicfight.skill.SkillDataKeys.PARRY_MOTION_COUNTER;

public class GuardParrySkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("244c57c0-a837-11eb-bcbc-0242ac130002");
    private int returnDuration;
    public GuardParrySkill(Builder<? extends Skill> builder) {
        super(builder);
    }
    @Override
    public void setParams(CompoundTag parameters) {
        super.setParams(parameters);
        this.returnDuration = parameters.getInt("return_duration");
    }
    @Override
    public void onInitiate(SkillContainer container) {
        container.getExecuter().getEventListener().addEventListener(EventType.DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID, (event) -> {
            if (container.isActivated() && !container.isDisabled()) {
                if (event.getAttackDamage() > event.getTarget().getHealth()) {
                    this.setDurationSynchronize(event.getPlayerPatch(), Math.min(this.maxDuration, container.getRemainDuration() + this.returnDuration));
                }
            }
        });

        container.getDataManager().registerData(PARRY_MOTION_COUNTER.get());

        if (!container.getExecuter().isLogicalClient()) {
            ServerPlayerPatch executer = (ServerPlayerPatch) container.getExecuter();
            int maxDuration = this.maxDuration + EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, executer.getOriginal());
            this.setMaxDurationSynchronize(executer, maxDuration);
        }


        container.getExecuter().getEventListener().addEventListener(EventType.HURT_EVENT_PRE, EVENT_UUID, (event) -> {
            if (event.getAmount() > 0.0F && container.isActivated() && !container.isDisabled() && container.getRemainDuration() > 0  && isBlockableSource(event.getDamageSource())) {
                DamageSource damageSource = event.getDamageSource();
                boolean isFront = false;
                Vec3 sourceLocation = damageSource.getSourcePosition();

                if (sourceLocation != null) {
                    Vec3 viewVector = event.getPlayerPatch().getOriginal().getViewVector(1.0F);
                    Vec3 toSourceLocation = sourceLocation.subtract(event.getPlayerPatch().getOriginal().position()).normalize();
                    if (toSourceLocation.dot(viewVector) > 0.0D) {
                        isFront = true;
                    }
                }

                if (isFront) {
                    this.setDurationSynchronize(event.getPlayerPatch(), container.getRemainDuration() - 20);
                    SkillDataManager dataManager = event.getPlayerPatch().getSkill(this).getDataManager();
                    int motionCounter = dataManager.getDataValue(SkillDataKeys.PARRY_MOTION_COUNTER.get());
                    dataManager.setDataF(SkillDataKeys.PARRY_MOTION_COUNTER.get(), (v) -> v + 1);
                    motionCounter %= 2;
                    if (motionCounter == 1) {
                        event.getPlayerPatch().playAnimationSynchronized(Animations.LONGSWORD_GUARD_ACTIVE_HIT1, 0);
                    } else {
                        event.getPlayerPatch().playAnimationSynchronized(Animations.LONGSWORD_GUARD_ACTIVE_HIT2, 0);
                    }
                    event.getPlayerPatch().playSound(EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
                    ServerPlayer playerentity = event.getPlayerPatch().getOriginal();
                    EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(playerentity.serverLevel(), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerentity, damageSource.getDirectEntity());
                    float knockback = 0.25F;

                    if (damageSource instanceof EpicFightDamageSource epicfightSource) {
                        knockback += Math.min(epicfightSource.getImpact() * 0.1F, 1.0F);
                    }

                    if (damageSource.getDirectEntity() instanceof LivingEntity) {
                        knockback += EnchantmentHelper.getKnockbackBonus((LivingEntity)damageSource.getDirectEntity()) * 0.1F;
                    }

                    event.getPlayerPatch().knockBackEntity(damageSource.getDirectEntity().position(), knockback);
                    event.setCanceled(true);
                    event.setResult(AttackResult.ResultType.BLOCKED);
                }
            }
        }, 0);

        container.getExecuter().getEventListener().addEventListener(EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID, (event) -> {
            if (event.getPlayerPatch().getSkill(this).isActivated()) {
                LivingEntity livingEntity = event.getPlayerPatch().getOriginal();
                livingEntity.setSprinting(false);
                Minecraft mc = Minecraft.getInstance();
                ControllEngine.setKeyBind(mc.options.keySprint, false);
            }
        });
    }
    @Override
    public void onRemoved(SkillContainer container) {
        PlayerPatch<?> executer = container.getExecuter();
        executer.getEventListener().removeListener(EventType.HURT_EVENT_PRE, EVENT_UUID, 0);
        executer.getEventListener().removeListener(EventType.DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID);
        executer.getEventListener().removeListener(EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.playSound(SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F);
        executer.playAnimationSynchronized(Animations.LONGSWORD_GUARD, 0.0F);
        SkillContainer weaponInnateSkill = executer.getSkill(this);
        if (weaponInnateSkill.isActivated()) {
            float consumption = this.consumption * ((float)weaponInnateSkill.getRemainDuration() / (this.maxDuration + EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, executer.getOriginal()) + 1));
            this.setConsumptionSynchronize(executer, consumption);
            this.setDurationSynchronize(executer, 0);
            int stack = weaponInnateSkill.getStack() - 1;
            this.setStackSynchronize(executer, stack);
            executer.modifyLivingMotionByCurrentItem(false);
        } else {
            int maxDuration = this.maxDuration + EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, executer.getOriginal());
            this.setDurationSynchronize(executer, maxDuration);
            weaponInnateSkill.activate();
            executer.modifyLivingMotionByCurrentItem(false);
        }
    }

    @Override
    public void cancelOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.getSkill(this).deactivate();
        executer.modifyLivingMotionByCurrentItem(false);
    }

    @Override
    public void executeOnClient(LocalPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnClient(executer, args);
        executer.getSkill(this).activate();
    }
    @Override
    public void cancelOnClient(LocalPlayerPatch executer, FriendlyByteBuf args) {
        super.cancelOnClient(executer, args);
        executer.getSkill(this).deactivate();
    }


    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        if (executer.isLogicalClient()) {
            return super.canExecute(executer);
        } else {
            ItemStack itemstack = executer.getOriginal().getMainHandItem();

            return EpicFightCapabilities.getItemStackCapability(itemstack).getInnateSkill(executer, itemstack) == this && executer.getOriginal().getVehicle() == null;
        }
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    private static boolean isBlockableSource(DamageSource damageSource) {
        return !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !damageSource.is(DamageTypeTags.IS_EXPLOSION);
    }
}