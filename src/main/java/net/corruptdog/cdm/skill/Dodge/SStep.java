package net.corruptdog.cdm.skill.Dodge;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.gameasset.CorruptSound;
import net.corruptdog.cdm.network.server.NetworkManager;
import net.corruptdog.cdm.network.server.SPAfterImagine;
import net.corruptdog.cdm.skill.CDSkillDataKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.StaticAnimationProvider;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;
import java.util.UUID;

public class SStep extends Skill {
    private static final UUID EVENT_UUID = UUID.fromString("e33b7c1a-8909-47c1-9b95-81f9d86c412e");
    public static final int RESET_TICKS = 100;
    protected final StaticAnimationProvider[][] animations;

    public static SStep.Builder createDodgeBuilder() {
        return (new SStep.Builder()).setCategory(SkillCategories.DODGE).setActivateType(ActivateType.ONE_SHOT).setResource(Resource.STAMINA);
    }

    public SStep(SStep.Builder builder) {
        super(builder);
        animations = builder.animations;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DODGE_SUCCESS_EVENT, EVENT_UUID, (event -> {

            Player player = event.getPlayerPatch().getOriginal();
            if(!container.getDataManager().getDataValue(CDSkillDataKeys.DODGE_PLAYED.get())){
                if(player.level() instanceof ServerLevel){
                    float consumption = container.getExecuter().getModifiedStaminaConsume(container.getExecuter().getSkill(SkillSlots.DODGE).getSkill().getConsumption());
                    container.getExecuter().setStamina(container.getExecuter().getStamina() + consumption * 0.5F);
                    ServerPlayer serverPlayer = (ServerPlayer)container.getExecuter().getOriginal();
                    SPAfterImagine msg = new SPAfterImagine(serverPlayer.position(), serverPlayer.getId());
                    NetworkManager.sendToAllPlayerTrackingThisEntityWithSelf(msg, serverPlayer);
                    event.getPlayerPatch().playSound(CorruptSound.DODGE.get(), 1F, 1.2F);

                    Skill skill = container.getExecuter().getSkill(SkillSlots.WEAPON_INNATE).getSkill();
                    if (skill != null) {
                        SkillContainer weaponInnateContainer = event.getPlayerPatch().getSkill(SkillSlots.WEAPON_INNATE);
                        weaponInnateContainer.getSkill().setConsumptionSynchronize(event.getPlayerPatch(), weaponInnateContainer.getResource() + 5F);
                    } else {
                        event.getPlayerPatch().playAnimationSynchronized(CorruptAnimations.WOLFDODGE_BACKWARD, 0.25F);
                    }
                }
                container.getDataManager().setData(CDSkillDataKeys.DODGE_PLAYED.get(), true);
                event.getPlayerPatch().playAnimationSynchronized(this.animations[3][container.getDataManager().getDataValue(CDSkillDataKeys.DIRECTION.get())].get(), 0.0F);
            }
        }));
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DODGE_SUCCESS_EVENT, EVENT_UUID);
    }

    @OnlyIn(Dist.CLIENT)
    public Object getExecutionPacket(LocalPlayerPatch executer, FriendlyByteBuf args) {
        Input input = Minecraft.getInstance().player.input;
        float pulse = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(Minecraft.getInstance().player), 0.0F, 1.0F);
        input.tick(false, pulse);
        int forward = input.up ? 1:0;
        int backward = input.down ? -1:0;
        int left = input.left ? 1 : 0;
        int right = input.right ? -1 : 0;
        int vertic = forward + backward;
        int horizon = left + right;
        float degree = vertic == 0 ? 0 : -(90 * horizon * (1 - Math.abs(vertic)) + 45 * vertic * horizon);
        int animation;

        if (vertic == 0) {
            if (horizon == 0) {
                animation = 0;
            } else {
                animation = horizon >= 0 ? 2 : 3;
            }
        } else {
            animation = vertic >= 0 ? 0 : 1;
        }
        degree= MathUtils.lerpBetween(degree,executer.getOriginal().getYRot(),1);
        CPExecuteSkill packet = new CPExecuteSkill(executer.getSkill(this).getSlotId());
        packet.getBuffer().writeInt(animation);
        packet.getBuffer().writeFloat(degree);
        return packet;
    }


    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
        int i = args.readInt();
        float yaw = args.readFloat();
        SkillDataManager dataManager = executer.getSkill(SkillSlots.DODGE).getDataManager();
        dataManager.setData(CDSkillDataKeys.DODGE_PLAYED.get(), false);
        int count = dataManager.getDataValue(CDSkillDataKeys.COUNT.get());
        executer.playAnimationSynchronized(this.animations[count][i].get(), 0.0F);
        dataManager.setDataSync(CDSkillDataKeys.DIRECTION.get(), i, executer.getOriginal());
        if(count != 0){
            dataManager.setDataSync(CDSkillDataKeys.RESET_TIMER.get(), RESET_TICKS, executer.getOriginal());
        }
        dataManager.setDataSync(CDSkillDataKeys.COUNT.get(), ++count % 3, executer.getOriginal());
        executer.setModelYRot(yaw, true);
    }
    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        SkillDataManager manager = container.getDataManager();
        if(manager.hasData(CDSkillDataKeys.RESET_TIMER.get()) && manager.getDataValue(CDSkillDataKeys.RESET_TIMER.get()) > 0){
            manager.setData(CDSkillDataKeys.RESET_TIMER.get(), manager.getDataValue(CDSkillDataKeys.RESET_TIMER.get()) - 1);
            if(manager.getDataValue(CDSkillDataKeys.RESET_TIMER.get()) == 1 && manager.hasData(CDSkillDataKeys.COUNT.get())){
                manager.setData(CDSkillDataKeys.COUNT.get(), 0);
            }
        }
    }

    public boolean isExecutableState(PlayerPatch<?> executer) {
        EntityState playerState = executer.getEntityState();
        return !executer.isInAir() && playerState.canUseSkill() && !executer.getOriginal().isInWater() && !executer.getOriginal().onClimbable() && executer.getOriginal().getVehicle() == null;
    }

    public static class Builder extends Skill.Builder<SStep> {
        protected StaticAnimationProvider[][] animations = new StaticAnimationProvider[4][4];
        public Builder() {
        }

        public SStep.Builder setCategory(SkillCategory category) {
            this.category = category;
            return this;
        }

        public SStep.Builder setActivateType(Skill.ActivateType activateType) {
            this.activateType = activateType;
            return this;
        }

        public SStep.Builder setResource(Skill.Resource resource) {
            this.resource = resource;
            return this;
        }
        public SStep.Builder setAnimations1(StaticAnimationProvider... animations) {
            this.animations[0] = animations;
            return this;
        }

        public SStep.Builder setAnimations2(StaticAnimationProvider... animations) {
            this.animations[1] = animations;
            return this;
        }

        public SStep.Builder setAnimations3(StaticAnimationProvider... animations) {
            this.animations[2] = animations;
            return this;
        }
        public SStep.Builder setPerfectAnimations(StaticAnimationProvider... animations) {
            this.animations[3] = animations;
            return this;
        }
    }
}
