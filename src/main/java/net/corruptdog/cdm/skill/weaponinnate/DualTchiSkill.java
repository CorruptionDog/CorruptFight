package net.corruptdog.cdm.skill.weaponinnate;

import com.google.common.collect.Maps;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Map;
import java.util.function.Supplier;

public class DualTchiSkill extends WeaponInnateSkill {
    private final Map<ResourceLocation, Supplier<AttackAnimation>> comboAnimation = Maps.newHashMap();
    public DualTchiSkill(Builder<? extends Skill> builder) {
        super(builder);
    }
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
    }
    private void ONCE(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.UCHIGATANA_SKILL1, 0.0F);
        executer.getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 0));
    }
    private void TWIN(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.UCHIGATANA_SKILL2, 0.0F);
    }
    @Override
    public void executeOnServer(ServerPlayerPatch execute, FriendlyByteBuf args) {
        ONCE(execute);
        super.executeOnServer(execute, args);
    }
}
