package net.corruptdog.cdm.mixins;

import javax.annotation.Nullable;

import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.guard.ParryingSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

@Mixin(
        value = {ParryingSkill.class},
        priority = 5000,
        remap = false
)
public abstract class ParryingMixin extends GuardSkill {
    @Shadow
    @Nullable
    protected abstract StaticAnimation getGuardMotion(PlayerPatch<?> var1, CapabilityItem var2, GuardSkill.BlockType var3);

    public ParryingMixin(GuardSkill.Builder builder) {
        super(builder);
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"onInitiate"},
            cancellable = true
    )
    public void Parrying(SkillContainer container, CallbackInfo ci) {
        ci.cancel();
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
            CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(InteractionHand.MAIN_HAND);
            if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD) && this.isExecutableState(event.getPlayerPatch())) {
                event.getPlayerPatch().getOriginal().startUsingItem(InteractionHand.MAIN_HAND);
            }

            int lastActive = (Integer)container.getDataManager().getDataValue((SkillDataKey)SkillDataKeys.LAST_ACTIVE.get());
            if (event.getPlayerPatch().getOriginal().tickCount - lastActive >= 0) {
                container.getDataManager().setData(SkillDataKeys.LAST_ACTIVE.get(), event.getPlayerPatch().getOriginal().tickCount);
            }

        });
    }
}
