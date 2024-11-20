package net.corruptdog.cdm.skill.weaponinnate;


import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.BasicAttackEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.List;
import java.util.UUID;

import static net.corruptdog.cdm.skill.CDSkillDataKeys.COMBO_COUNTER;

public class YamatoAttack extends BasicAttack {
    private static final UUID EVENT_UUID = UUID.fromString("6504b895-cd22-d0eb-9b19-509a447ab84f");

    public YamatoAttack(Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        container.getExecuter().getEventListener().addEventListener(EventType.ACTION_EVENT_SERVER, EVENT_UUID, (event) -> {
            if (!event.getAnimation().isBasicAttackAnimation()) {
                container.getDataManager().setData(COMBO_COUNTER.get(), 0);
            }
            if(!(event.getAnimation() instanceof AttackAnimation)){
                this.onUnlock(container);
            }
        });

        container.getExecuter().getEventListener().addEventListener(EventType.ATTACK_ANIMATION_END_EVENT,EVENT_UUID,(event) -> {
            if(container.getDataManager().getDataValue(COMBO_COUNTER.get()) > 0){
                container.getDataManager().setData(COMBO_COUNTER.get(),0);
            }
            if(!(event.getAnimation().getId() == CorruptAnimations.YAMATO_POWER3.getId() || event.getAnimation().getId() == CorruptAnimations.YAMATO_POWER3_REPEAT.getId())) {
                this.onUnlock(container);
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(EventType.ACTION_EVENT_SERVER, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
    }

    private void onUnlock(SkillContainer container) {
        if(container.getStack() > 0){
            this.setStackSynchronize((ServerPlayerPatch) container.getExecuter(),0);
        }
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        if (executer.isLogicalClient()) {
            return executer.getSkill((Skill) this.category).getStack() == 0;
        } else {
            return true;
        }
    }

    @Override
    public boolean isExecutableState(PlayerPatch<?> executer) {
        EntityState playerState = executer.getEntityState();
        Player player = executer.getOriginal();
        return !(player.isSpectator()  || !playerState.canBasicAttack());
    }

    @Override
    public boolean resourcePredicate(PlayerPatch<?> playerpatch) {
        return playerpatch.getSkill((Skill) this.category).getStack() == 0;
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        if (executer.getEventListener().triggerEvents(EventType.BASIC_ATTACK_EVENT, new BasicAttackEvent(executer))) {
            return;
        }

        CapabilityItem cap = executer.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        StaticAnimation attackMotion = null;
        ServerPlayer player = executer.getOriginal();
        SkillDataManager dataManager = executer.getSkill((Skill) this.category).getDataManager();
        int comboCounter = dataManager.getDataValue(COMBO_COUNTER.get());

        if (player.isPassenger()) {
            Entity entity = player.getVehicle();

            if ((entity instanceof PlayerRideableJumping && ((PlayerRideableJumping)entity).canJump()) && cap.availableOnHorse() && cap.getMountAttackMotion() != null) {
                comboCounter %= cap.getMountAttackMotion().size();
                attackMotion = (StaticAnimation) cap.getMountAttackMotion().get(comboCounter);
                comboCounter++;
            }
        } else {
            List<AnimationProvider<?>> combo = cap.getAutoAttckMotion(executer);
            int comboSize = combo.size();
            boolean dashAttack = player.isSprinting();

            if (dashAttack) {
                comboCounter = comboSize - 2;
            } else {
                comboCounter %= comboSize - 2;
            }

            attackMotion = (StaticAnimation) combo.get(comboCounter);
            comboCounter = dashAttack ? 0 : comboCounter + 1;
        }

        dataManager.setData(COMBO_COUNTER.get(), comboCounter);

        if (attackMotion != null) {
            executer.playAnimationSynchronized(attackMotion, 0);
        }
        executer.updateEntityState();
    }

    @Override
    public void updateContainer(SkillContainer container) {
        if (container.getDataManager().getDataValue(COMBO_COUNTER.get()) > 0 && container.getExecuter().getTickSinceLastAction() > 10){
            container.getDataManager().setData(COMBO_COUNTER.get(), 0);
        }
    }
}