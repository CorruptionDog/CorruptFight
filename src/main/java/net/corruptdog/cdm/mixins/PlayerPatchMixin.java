package net.corruptdog.cdm.mixins;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import static yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch.STAMINA;

@Mixin(
        value = {PlayerPatch.class},
        remap = false)

public abstract class PlayerPatchMixin<T extends Player> extends LivingEntityPatch<T> {

    @Shadow
    protected int tickSinceLastAction;
    @Shadow
    protected double xo;
    @Shadow
    protected double yo;
    @Shadow
    protected double zo;

    @Shadow public abstract float getStamina();

    @Shadow public abstract float getMaxStamina();

    @Shadow public abstract void setStamina(float value);

    @Override
    public StaticAnimation getHitAnimation(StunType stunType) {
        if (this.original.getVehicle() != null) {
            return Animations.BIPED_HIT_ON_MOUNT;
        } else {
            switch(stunType) {
                case LONG:
                    return Animations.BIPED_HIT_LONG;
                case SHORT:
                    return Animations.BIPED_HIT_SHORT;
                case HOLD:
                    return Animations.BIPED_HIT_SHORT;
                case KNOCKDOWN:
                    return Animations.BIPED_KNOCKDOWN;
                case NEUTRALIZE:
                    return Animations.BIPED_COMMON_NEUTRALIZED;
                case FALL:
                    return Animations.BIPED_LANDING;
                case NONE:
                    return null;
            }
        }

        return null;
    }

    public void serverTick(LivingEvent.LivingTickEvent event) {
        super.serverTick(event);

        if (this.state.canBasicAttack()) {
            this.tickSinceLastAction++;
        }

        float stamina = this.getStamina();
        float maxStamina = this.getMaxStamina();
        float staminaRegen = (float)this.original.getAttributeValue(EpicFightAttributes.STAMINA_REGEN.get());
        int regenStandbyTime = 600 / (int)(35 * staminaRegen);

        if (stamina < maxStamina && this.tickSinceLastAction > regenStandbyTime) {
            float staminaFactor = 1.0F + (float)Math.pow((stamina / (maxStamina - stamina * 0.5F)), 2);
            this.setStamina(stamina + maxStamina * 0.01F * staminaFactor * staminaRegen);
        }

        if (maxStamina < stamina) {
            this.setStamina(maxStamina);
        }

        this.xo = this.original.getX();
        this.yo = this.original.getY();
        this.zo = this.original.getZ();
    }

}