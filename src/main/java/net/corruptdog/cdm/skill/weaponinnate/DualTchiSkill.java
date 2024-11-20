package net.corruptdog.cdm.skill.weaponinnate;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class DualTchiSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("b3c1f1c8-5c4e-4b1e-9f9e-3f8d5b7e3e2f");
    public DualTchiSkill(Builder<? extends Skill> builder) {
        super(builder);
    }
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        PlayerEventListener listener = container.getExecuter().getEventListener();
        listener.addEventListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, (event) -> {
            int id = event.getAnimation().getId();

        });
    }
    private void ONCE(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.DUAL_TACHI_SKILL2, 0.0F);
    }
    @Override
    public void executeOnServer(ServerPlayerPatch execute, FriendlyByteBuf args) {
        ONCE(execute);
    }
}
