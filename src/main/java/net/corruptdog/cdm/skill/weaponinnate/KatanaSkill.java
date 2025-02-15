package net.corruptdog.cdm.skill.weaponinnate;

import com.google.common.collect.Maps;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class KatanaSkill extends WeaponInnateSkill {
    public KatanaSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
    }

    private void SKILL1(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.BLADE_RUSH_FINISHER, 0.1F);
    }

    private void SKILL2(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.FATAL_DRAW_DASH, 0.0F);
    }


    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        boolean isSheathed = executer.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(SkillDataKeys.SHEATH.get());
        if (executer.getOriginal().isSprinting()) {
            float stamina = executer.getStamina();
            float maxStamina = executer.getMaxStamina();
            float p = maxStamina * 0.15F;
            if (stamina >= p) {
                SKILL2(executer);
                executer.setStamina(stamina - p);
            }
        } else if (isSheathed) {
            executer.playAnimationSynchronized(CorruptAnimations.FATAL_DRAW, -0.33F);
        } else {
            SKILL1(executer);
        }
        super.executeOnServer(executer, args);
    }
}
