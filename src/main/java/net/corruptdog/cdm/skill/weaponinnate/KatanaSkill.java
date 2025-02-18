package net.corruptdog.cdm.skill.weaponinnate;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.skill.BattojutsuPassive;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

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

    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }
    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        boolean isSheathed = executer.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(BattojutsuPassive.SHEATH);
        if (isSheathed) {
            executer.playAnimationSynchronized(CorruptAnimations.FATAL_DRAW, -0.33F);
        }else if (executer.getOriginal().isSprinting()) {
            float stamina = executer.getStamina();
            float maxStamina = executer.getMaxStamina();
            float p = maxStamina * 0.15F;
            if (stamina >= p) {
                SKILL2(executer);
                executer.setStamina(stamina - p);
            }
        } else {
            SKILL1(executer);
        }
        super.executeOnServer(executer, args);
    }
}
