package net.corruptdog.cdm.mixins;

import java.util.UUID;

import net.corruptdog.cdm.CDConfig;
import net.corruptdog.cdm.gameasset.CorruptSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;


@Mixin(
        value = {DodgeSkill.class},
        remap = false
)
public class DodgeSkillMixin extends Skill {
    @Unique
    private static final UUID EVENT_UUID = UUID.fromString("99e5c782-fdaf-11eb-9a03-0242ac130005");

    public DodgeSkillMixin(Skill.Builder<? extends Skill> builder) {
        super(builder);
    }

    @Unique
    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DODGE_SUCCESS_EVENT, EVENT_UUID, (event) -> {
            if (CDConfig.ENABLE_DODGESUCCESS_SOUND.get() ) {
                container.getExecuter().playSound(CorruptSound.FORESIGHT.get(), 0.8F, 1.2F);
            }
        });
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ANIMATION_BEGIN_EVENT, EVENT_UUID, (event) -> {
            StaticAnimation animation = event.getAnimation();
            if (CDConfig.ENABLE_DODGE_SOUND.get()) {
                if (animation == Animations.BIPED_STEP_FORWARD || animation == Animations.BIPED_STEP_BACKWARD || animation == Animations.BIPED_STEP_LEFT || animation == Animations.BIPED_STEP_RIGHT) {
                    event.getPlayerPatch().playSound(CorruptSound.STEP.get(), 0.8F, 1.0F);
                }
            }
        });
    }


    @Unique
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DODGE_SUCCESS_EVENT, EVENT_UUID);
    }
}
