package net.corruptdog.cdm.skill.weaponinnate;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.SkillDataManager.SkillDataKey;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;


public class GuardParrySkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("244c57c0-a837-11eb-bcbc-0242ac130002");
    private static final SkillDataKey<Integer> PARRY_MOTION_COUNTER = SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    private int returnDuration;
    private int maxDuration;


    public GuardParrySkill(Builder<? extends Skill> builder) {
        super(builder);
    }
    @Override
    public void setParams(CompoundTag parameters) {
        super.setParams(parameters);
        this.returnDuration = parameters.getInt("return_duration");
        this.maxDuration = parameters.getInt("max_duration");
    }
    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        container.getDataManager().registerData(PARRY_MOTION_COUNTER);
        int enchant = this.returnDuration * EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, container.getExecuter().getOriginal());
        if (!container.getExecuter().isLogicalClient()) {
            System.out.println("Executing on server: " + container.getExecuter());
            this.setMaxDurationSynchronize((ServerPlayerPatch) container.getExecuter(), this.maxDuration +  enchant);
        }

        container.getExecuter().getEventListener().addEventListener(EventType.DEALT_DAMAGE_EVENT_PRE, EVENT_UUID, (event) -> {
            if (container.isActivated() && !container.isDisabled()) {
                if (event.getAttackDamage() > event.getTarget().getHealth()) {
                    this.setDurationSynchronize(event.getPlayerPatch(), Math.min(this.maxDuration, container.getRemainDuration() + this.returnDuration));
                }
            }
        });

        container.getExecuter().getEventListener().addEventListener(EventType.HURT_EVENT_PRE, EVENT_UUID, (event) -> {
            if (event.getAmount() > 0.0F && container.getRemainDuration() > 0 && this.isExecutableState(event.getPlayerPatch()) && this.canExecute(event.getPlayerPatch()) && isBlockableSource(event.getDamageSource())) {
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
                    this.setDurationSynchronize(event.getPlayerPatch(), container.getRemainDuration() - this.returnDuration);
                    SkillDataManager dataManager = event.getPlayerPatch().getSkill(this).getDataManager();
                    int motionCounter = dataManager.getDataValue(PARRY_MOTION_COUNTER);
                    dataManager.setDataF(PARRY_MOTION_COUNTER, (v) -> v + 1);
                    motionCounter %= 2;
                    if (motionCounter == 1) {
                        event.getPlayerPatch().playAnimationSynchronized(Animations.LONGSWORD_GUARD_ACTIVE_HIT1, 0);
                    } else {
                        event.getPlayerPatch().playAnimationSynchronized(Animations.LONGSWORD_GUARD_ACTIVE_HIT2, 0);
                    }
                    event.getPlayerPatch().playSound(EpicFightSounds.CLASH, -0.05F, 0.1F);
                    ServerPlayer playerentity = event.getPlayerPatch().getOriginal();
                    ServerPlayer serveerPlayer = event.getPlayerPatch().getOriginal();
                    EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(serveerPlayer.getLevel(), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerentity, damageSource.getDirectEntity());
                    float knockback = 0.25F;


                    if (damageSource instanceof EpicFightDamageSource) {
                        knockback += Math.min(((EpicFightDamageSource)damageSource).getImpact() * 0.1F, 1.0F);
                    }

                    if (damageSource.getDirectEntity() instanceof LivingEntity) {
                        knockback += EnchantmentHelper.getKnockbackBonus((LivingEntity)damageSource.getDirectEntity()) * 0.1F;
                    }
                    if  (damageSource.getDirectEntity() instanceof LivingEntity livingEntity) {
                        Vec3 lookVec = livingEntity.getLookAngle();
                        Vec3 pushVec = new Vec3(-lookVec.x, 0, -lookVec.z).normalize().scale(knockback);
                        livingEntity.push(pushVec.x, pushVec.y, pushVec.z);
                    }
                    ServerPlayer player = event.getPlayerPatch().getOriginal();
                    Vec3 playerPushVec = new Vec3(0, 0, knockback);
                    player.push(playerPushVec.x, playerPushVec.y, playerPushVec.z);

                    event.getPlayerPatch().knockBackEntity(damageSource.getDirectEntity().position(), knockback);
                    event.setCanceled(true);
                    event.setResult(AttackResult.ResultType.BLOCKED);
                }
            }
        }, 0);

        container.getExecuter().getEventListener().addEventListener(EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID, (event) -> {
            SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);

            if (skillContainer.isActivated()) {
                LocalPlayer clientPlayer = event.getPlayerPatch().getOriginal();
                clientPlayer.setSprinting(false);
                Minecraft mc = Minecraft.getInstance();
                ClientEngine.getInstance().controllEngine.setKeyBind(mc.options.keySprint, false);
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_PRE, EVENT_UUID, 0);
        container.getExecuter().getEventListener().removeListener(EventType.DEALT_DAMAGE_EVENT_POST, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.playSound(SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F);
        executer.playAnimationSynchronized(Animations.LONGSWORD_GUARD, 0.0F);
        SkillContainer weaponInnateSkill = executer.getSkill(this);
        if (weaponInnateSkill.isActivated()) {
            super.cancelOnServer(executer, args);
            this.setConsumptionSynchronize(executer, this.consumption * ((float)weaponInnateSkill.getRemainDuration() / (this.maxDuration + EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, executer.getOriginal()) + 1)));
            this.setDurationSynchronize(executer, 0);
            int stack = weaponInnateSkill.getStack() - 1;
            this.setStackSynchronize(executer, stack);
            executer.modifyLivingMotionByCurrentItem();
//            float consumption = this.consumption * ((float)weaponInnateSkill.getRemainDuration() / (this.maxDuration + this.returnDuration * EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, executer.getOriginal()) ));
//            this.setConsumptionSynchronize(executer, consumption);
//            this.setDurationSynchronize(executer, 0);
//            int stack = weaponInnateSkill.getStack() - 1;
//            this.setStackSynchronize(executer, stack);
//            executer.modifyLivingMotionByCurrentItem();
        } else {
            this.setDurationSynchronize(executer, this.maxDuration + EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, executer.getOriginal()));
            weaponInnateSkill.activate();
            executer.modifyLivingMotionByCurrentItem();
//            int enchant = this.returnDuration * EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, executer.getOriginal());
//            int maxDuration = this.maxDuration + enchant;
//            this.setDurationSynchronize(executer, maxDuration);
//            weaponInnateSkill.activate();
//            executer.modifyLivingMotionByCurrentItem();
        }
    }
    @Override
    public void cancelOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.cancelOnServer(executer, args);
        this.setConsumptionSynchronize(executer, 0);
        this.setStackSynchronize(executer, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 1);
        executer.modifyLivingMotionByCurrentItem();
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
        return !damageSource.isBypassInvul() && !damageSource.isExplosion();
    }
}