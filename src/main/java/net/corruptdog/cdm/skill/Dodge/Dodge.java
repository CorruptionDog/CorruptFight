package net.corruptdog.cdm.skill.Dodge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.SkillConsumeEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class Dodge extends DodgeSkill {
    public Dodge(DodgeSkill.Builder builder) {
        super(builder);
    }

    @OnlyIn(Dist.CLIENT)
    public Object getExecutionPacket(LocalPlayerPatch executer, FriendlyByteBuf args) {
        Input input = executer.getOriginal().input;
        float pulse = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(executer.getOriginal()), 0.0F, 1.0F);
        input.tick(false, pulse);

        int forward = input.up ? 1 : 0;
        int backward = input.down ? -1 : 0;
        int left = input.left ? 1 : 0;
        int right = input.right ? -1 : 0;
        int vertic = forward + backward;
        int horizon = left + right;
        float yRot = Minecraft.getInstance().gameRenderer.getMainCamera().getYRot();
        float degree = -(90 * horizon * (1 - Math.abs(vertic)) + 45 * vertic * horizon) + yRot;

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

        CPExecuteSkill packet = new CPExecuteSkill(executer.getSkill(this).getSlotId());
        packet.getBuffer().writeInt(animation);
        packet.getBuffer().writeFloat(degree);
        return packet;
    }

    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        SkillConsumeEvent event = new SkillConsumeEvent(executer, this, this.resource);
        executer.getEventListener().triggerEvents(EventType.SKILL_CONSUME_EVENT, event);
        if (!event.isCanceled()) {
            event.getResourceType().consumer.consume(this, executer, event.getAmount());
        }

        executer.getSkill(this).activate();
        int i = args.readInt();
        float yaw = args.readFloat();
        executer.playAnimationSynchronized(this.animations[i].get(), 0.0F);
        executer.setModelYRot(yaw, true);
    }

    public Skill getPriorSkill() {
        return EpicFightSkills.STEP;
    }
}
