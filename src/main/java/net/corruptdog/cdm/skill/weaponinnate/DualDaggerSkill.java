package net.corruptdog.cdm.skill.weaponinnate;


import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import static net.corruptdog.cdm.skill.CDSkillDataKeys.*;
import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType.*;

public class DualDaggerSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f082557a-b2f9-11eb-8529-0242ac130003");
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public DualDaggerSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        PlayerEventListener listener = container.getExecuter().getEventListener();
        listener.addEventListener(ANIMATION_BEGIN_EVENT, EVENT_UUID, (event) -> {
            StaticAnimation animation = event.getAnimation();
            int r = container.getDataManager().getDataValue(RUSH_COMBO.get());
            int id = event.getAnimation().getId();
            if (id == CorruptAnimations.BLADE_RUSH1.getId()) {
                container.getDataManager().setData(RUSH_STAR.get(), true);
                container.getDataManager().setData(RUSH_COMBO.get(), r + 1);
            }
            if (id == CorruptAnimations.BLADE_RUSH2.getId()) {
                container.getDataManager().setData(RUSH_COMBO.get(), r + 1);
            }
            if (id == CorruptAnimations.BLADE_RUSH3.getId()) {
                container.getDataManager().setData(RUSH_COMBO.get(), r + 1);
            }
            if (id == CorruptAnimations.BLADE_RUSH4.getId()) {
                container.getDataManager().setData(RUSH_STAR.get(), false);
                container.getDataManager().setData(RUSH_COMBO.get(), 0);
            }
            if (!(animation == CorruptAnimations.BLADE_RUSH1
                    || animation == CorruptAnimations.BLADE_RUSH2
                    || animation == CorruptAnimations.BLADE_RUSH3
                    || animation == CorruptAnimations.BLADE_RUSH4 )){
                scheduleResetCombo(container);
            }
        });
    }

    private void scheduleResetCombo(SkillContainer container) {
        scheduler.schedule(() -> {
            container.getDataManager().setData(RUSH_COMBO.get(), 0);
            container.getDataManager().setData(RUSH_STAR.get(), false);
        }, 0, TimeUnit.SECONDS);
    }

    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ANIMATION_BEGIN_EVENT, EVENT_UUID);
        scheduler.shutdown();
    }


    @Override
    public void executeOnServer(ServerPlayerPatch execute, FriendlyByteBuf args) {
        SkillContainer skillContainer = execute.getSkill(SkillSlots.WEAPON_INNATE);
        Boolean rush = skillContainer.getDataManager().getDataValue(RUSH_STAR.get());
        int combo = skillContainer.getDataManager().getDataValue(RUSH_COMBO.get());
        if (!rush) {
            execute.playAnimationSynchronized(CorruptAnimations.BLADE_RUSH1, 0);
        }
        if (combo == 1) {
            execute.playAnimationSynchronized(CorruptAnimations.BLADE_RUSH2, 0);
        }
        if (combo == 2) {
            execute.playAnimationSynchronized(CorruptAnimations.BLADE_RUSH3, 0);
        }
        if (combo == 3) {
            execute.playAnimationSynchronized(CorruptAnimations.BLADE_RUSH4, 0);
        }
    }
}
