package net.corruptdog.cdm.skill.weaponinnate;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.google.common.collect.Maps;
import net.corruptdog.cdm.gameasset.CDSkills;
import net.corruptdog.cdm.network.server.NetworkManager;
import net.corruptdog.cdm.network.server.SPAfterImagine;
import net.corruptdog.cdm.skill.CDSkillDataKeys;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import static net.corruptdog.cdm.skill.CDSkillDataKeys.*;
import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType.*;

public class YamatoSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f082557a-b2f9-11eb-8529-0242ac130003");
    private final Map<ResourceLocation, Supplier<AttackAnimation>> comboAnimation = Maps.newHashMap();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public YamatoSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        PlayerEventListener listener = container.getExecuter().getEventListener();
        listener.addEventListener(ACTION_EVENT_SERVER, EVENT_UUID, (event) -> {
            StaticAnimation animation = event.getAnimation();
            if (animation == CorruptAnimations.YAMATO_COUNTER2 ) {
                container.getDataManager().setDataSync(CDSkillDataKeys.COUNTER_SUCCESS.get(), false, ((ServerPlayerPatch) container.getExecuter()).getOriginal());
            }
            if (animation == CorruptAnimations.YAMATO_STRIKE1 || animation == CorruptAnimations.YAMATO_STRIKE2) {
                container.getDataManager().setDataSync(COUNTER_SUCCESS.get(), false, ((ServerPlayerPatch) container.getExecuter()).getOriginal());
                container.getDataManager().setDataSync(COUNTER.get(), false, ((ServerPlayerPatch) container.getExecuter()).getOriginal());
            }
            if (animation == CorruptAnimations.YAMATO_POWER3_FINISH || animation == CorruptAnimations.YAMATO_POWER_DASH  ) {
                container.getDataManager().setDataSync(POWER3.get(), false, ((ServerPlayerPatch) container.getExecuter()).getOriginal());
            }
            if (!(animation == CorruptAnimations.YAMATO_POWER3 || animation == CorruptAnimations.YAMATO_POWER3_REPEAT || animation == CorruptAnimations.YAMATO_POWER3_FINISH || animation == CorruptAnimations.YAMATO_POWER_DASH)) {
                if (container.getDataManager().getDataValue(DAMAGES.get()) > 1) {
                    container.getDataManager().setData(DAMAGES.get(), 0);
                }
            }
        });
        listener.addEventListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, (event) -> {
            int id = event.getAnimation().getId();
            if (id == CorruptAnimations.YAMATO_POWER3.getId() || id == CorruptAnimations.YAMATO_POWER3_REPEAT.getId()) {
                event.getPlayerPatch().reserveAnimation(CorruptAnimations.YAMATO_POWER3_FINISH);
            } else if (id == CorruptAnimations.YAMATO_COUNTER1.getId()){
                container.getDataManager().setDataSync(CDSkillDataKeys.COUNTER_SUCCESS.get(), true, ((ServerPlayerPatch) container.getExecuter()).getOriginal());
                event.getPlayerPatch().reserveAnimation(CorruptAnimations.YAMATO_COUNTER2);
            } else if (id == CorruptAnimations.YAMATO_POWER3_FINISH.getId() || id == CorruptAnimations.YAMATO_POWER_DASH.getId()){
                container.getDataManager().setData(DAMAGE.get(), 0);
            }
        });
        listener.addEventListener(DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID, (event) -> {
            Integer k = container.getDataManager().getDataValue(DAMAGES.get());
            int id = event.getDamageSource().getAnimation().getId();
            float maxstamina = event.getPlayerPatch().getMaxStamina();
            float stamina = event.getPlayerPatch().getStamina();
            float recover = maxstamina * 0.025F;
            if (id == CorruptAnimations.YAMATO_POWER3.getId() || id == CorruptAnimations.YAMATO_POWER3_REPEAT.getId()) {
                event.getPlayerPatch().setStamina(stamina + recover);
                container.getDataManager().setData(DAMAGES.get(), k + 1);
            }
            if (id == CorruptAnimations.YAMATO_POWER1.getId()) {
                float r = 0.25F;
                if (stamina < maxstamina) {
                    container.getExecuter().setStamina(stamina + r * maxstamina);
                }
            } else if(id == CorruptAnimations.YAMATO_COUNTER2.getId()) {
                float c = 0.1F;
                if (stamina < maxstamina) {
                    container.getExecuter().setStamina(stamina + c * maxstamina);
                }
            }
        });
        listener.addEventListener(MODIFY_DAMAGE_EVENT, EVENT_UUID, (event) -> {
            ResourceLocation rl = event.getPlayerPatch().getAnimator().getPlayerFor(null).getAnimation().getRegistryName();
            Integer K = container.getDataManager().getDataValue(DAMAGES.get());
            int max = 25;
            if (rl == CorruptAnimations.YAMATO_POWER3_REPEAT.getRegistryName() || rl == CorruptAnimations.YAMATO_POWER3.getRegistryName() ) {
                container.getDataManager().setData(DAMAGES.get(), K + 1);
            }
            float bonus = 0.33F;
            if (rl == CorruptAnimations.YAMATO_POWER3_FINISH.getRegistryName()) {
                K = Math.min(K,max);
                event.setDamage(event.getDamage() * (1F + bonus * K));
            }
        });
        listener.addEventListener(HURT_EVENT_PRE, EVENT_UUID, (event) -> {
            int power2_recover = 2;
            Skill skill = container.getExecuter().getSkill(SkillSlots.WEAPON_INNATE).getSkill();
            ServerPlayerPatch executer = event.getPlayerPatch();
            AnimationPlayer animationPlayer = executer.getAnimator().getPlayerFor(null);
            float elapsedTime = animationPlayer.getElapsedTime();
            int animationId = executer.getAnimator().getPlayerFor(null).getAnimation().getId();
            if (animationId == CorruptAnimations.YAMATO_STRIKE1.getId() || animationId == CorruptAnimations.YAMATO_STRIKE2.getId()) {
                event.setCanceled(true);
                event.setResult(AttackResult.ResultType.MISSED);
            }
            if (elapsedTime <= 0.35F) {
                if (animationId == CorruptAnimations.YAMATO_POWER0_1.getId()) {
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
                                ServerPlayer serverPlayer = (ServerPlayer) container.getExecuter().getOriginal();
                                SPAfterImagine msg = new SPAfterImagine(serverPlayer.position(), serverPlayer.getId());
                                NetworkManager.sendToAllPlayerTrackingThisEntityWithSelf(msg, serverPlayer);
                                POWER0_2(executer);
                                container.getDataManager().setDataSync(CDSkillDataKeys.COUNTER_SUCCESS.get(), true, ((ServerPlayerPatch) container.getExecuter()).getOriginal());
                                scheduler.schedule(() -> executer.getSkill(SkillSlots.WEAPON_INNATE).getDataManager().setData(CDSkillDataKeys.COUNTER_SUCCESS.get(), false), 1500, TimeUnit.MILLISECONDS);
                                if (skill != null) {
                                    this.stackCost(executer, -power2_recover);
                                } else {
                                    event.getPlayerPatch().playAnimationSynchronized(CorruptAnimations.YAMATO_POWER0_2, 0.15F);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.MODIFY_DAMAGE_EVENT, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ANIMATION_BEGIN_EVENT,EVENT_UUID);
    }
    private void STRIKE(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_STRIKE1, 0F);}
    private void STRIKE2(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_STRIKE2, 0.25F);}
    private void POWER_1(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER1, 0.25F);}
    private void POWER_2(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER2, 0.10F);}
    private void POWER_3(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER3, 0.25F);
        }
    private void POWER_REPEAT(ServerPlayerPatch executer) {
        Skill skill = executer.getSkill(SkillSlots.WEAPON_INNATE).getSkill();
        if (skill != null) {
            SkillContainer weaponInnateContainer = executer.getSkill(SkillSlots.WEAPON_INNATE);
            weaponInnateContainer.getSkill().setConsumptionSynchronize(executer, weaponInnateContainer.getResource() + 100F);
        }
        float currentStamina = executer.getStamina();
        float staminaCost = 2.0F;
        if (currentStamina >= staminaCost) {
            executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER3_REPEAT, 0.0F);
            executer.setStamina(currentStamina - staminaCost);
            SkillContainer skillContainer = executer.getSkill(SkillSlots.WEAPON_INNATE);
            skillContainer.getDataManager().setData(CDSkillDataKeys.POWER3.get(), true);
        } else {
            executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER3_FINISH, 0.0F);
            SkillContainer skillContainer = executer.getSkill(SkillSlots.WEAPON_INNATE);
            skillContainer.getDataManager().setData(CDSkillDataKeys.POWER3.get(), false);
        }
    }
    private void POWER_DASH(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER_DASH, 0.05F);}
    private void POWER0_1(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER0_1, 0.0F);}
    private void POWER0_2(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER0_2, 0.05F);}
    private void COUNTER(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_COUNTER1, 0.35F);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        ResourceLocation rl = executer.getAnimator().getPlayerFor(null).getAnimation().getRegistryName();
        if (rl == CorruptAnimations.YAMATO_POWER3.getRegistryName() || rl == CorruptAnimations.YAMATO_POWER3_REPEAT.getRegistryName()
                || rl == CorruptAnimations.YAMATO_ACTIVE_GUARD_HIT.getRegistryName() || rl == CorruptAnimations.YAMATO_ACTIVE_GUARD_HIT2.getRegistryName()
             || rl == CorruptAnimations.YAMATO_COUNTER1.getRegistryName() || rl == CorruptAnimations.YAMATO_POWER0_2.getRegistryName()){
            return true;
        } else if (executer.isLogicalClient()) {
            return executer.getEntityState().canBasicAttack();
        } else {
            ItemStack itemstack = executer.getOriginal().getMainHandItem();
            return executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getInnateSkill(executer, itemstack) == this;
        }
    }

    private void stackCost(ServerPlayerPatch player,int cost){
        this.setStackSynchronize(player, player.getSkill(CDSkills.YAMATOSKILL).getStack() - cost);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch execute, FriendlyByteBuf args) {
        ResourceLocation rl = execute.getAnimator().getPlayerFor(null).getAnimation().getRegistryName();
        SkillContainer skillContainer = execute.getSkill(SkillSlots.WEAPON_INNATE);
        Boolean counterSuccess = skillContainer.getDataManager().getDataValue(COUNTER_SUCCESS.get());
        Boolean counter = skillContainer.getDataManager().getDataValue(COUNTER.get());
        Boolean power3 = skillContainer.getDataManager().getDataValue(POWER3.get());
        if (counterSuccess) {
            STRIKE(execute);
            return;
        }
        if (counter) {
            STRIKE2(execute);
            return;
        }
        if (power3) {
            POWER_REPEAT(execute);
        }
        if (execute.getOriginal().isSprinting() && execute.getSkill(CDSkills.YAMATOSKILL).getStack() >= 0) {
            float stamina = execute.getStamina();
            float maxStamina = execute.getMaxStamina();
            float p = maxStamina * 0.25F;
            if (stamina >= p) {
                POWER_DASH(execute);
                execute.setStamina(stamina - p);
            }
        } else {
            if (this.comboAnimation.containsKey(rl)) {
                execute.playAnimationSynchronized(this.comboAnimation.get(rl).get(), 0.0F);
            } else {
                if (canExecute(execute) && !power3) {
                    POWER0_1(execute);
                } else {
                    return;
                }
                Map<ResourceLocation, Runnable> actionMap = Maps.newHashMap();
                actionMap.put(CorruptAnimations.YAMATO_AUTO1.getRegistryName(), () -> POWER_1(execute));
                actionMap.put(CorruptAnimations.YAMATO_AUTO2.getRegistryName(), () -> POWER_2(execute));
                actionMap.put(CorruptAnimations.YAMATO_AUTO3.getRegistryName(), () -> POWER_3(execute));
                actionMap.put(CorruptAnimations.YAMATO_POWER3.getRegistryName(), () -> POWER_REPEAT(execute));
                actionMap.put(CorruptAnimations.YAMATO_POWER3_REPEAT.getRegistryName(), () -> POWER_REPEAT(execute));
                actionMap.put(CorruptAnimations.YAMATO_STRIKE1.getRegistryName(), () -> POWER_2(execute));
                actionMap.put(CorruptAnimations.YAMATO_STRIKE2.getRegistryName(), () -> POWER_3(execute));
                actionMap.put(CorruptAnimations.YAMATO_COUNTER1.getRegistryName(), () -> STRIKE2(execute));
                actionMap.put(CorruptAnimations.YAMATO_ACTIVE_GUARD_HIT.getRegistryName(), () -> COUNTER(execute));
                actionMap.put(CorruptAnimations.YAMATO_ACTIVE_GUARD_HIT2.getRegistryName(), () -> COUNTER(execute));
                if (actionMap.containsKey(rl)) {
                    actionMap.get(rl).run();
                }
                super.executeOnServer(execute, args);
            }
        }
    }
}
