package net.corruptdog.cdm.skill.Dodge;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.events.engine.ControllEngine;
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
    @Override
    public FriendlyByteBuf gatherArguments(LocalPlayerPatch executer, ControllEngine controllEngine) {
        Input input = executer.getOriginal().input;
        float pulse = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(executer.getOriginal()), 0.0F, 1.0F);
        input.tick(false, pulse);


        int forward = input.up ? 1 : 0; // 输入后退时，实际向前移动
        int backward = input.down ? -1 : 0; // 输入前进时，实际向后移动
        int left = input.right ? 1 : 0;    // 输入右移时，实际向左移动
        int right = input.left ? -1 : 0;    // 输入左移时，实际向右移动

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(forward);
        buf.writeInt(backward);
        buf.writeInt(left);
        buf.writeInt(right);

        return buf;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Object getExecutionPacket(LocalPlayerPatch executer, FriendlyByteBuf args) {
        int forward = args.readInt();
        int backward = args.readInt();
        int left = args.readInt();
        int right = args.readInt();
        int vertic = forward + backward;
        int horizon = left + right;
        int degree = -(90 * horizon * (1 - Math.abs(vertic)) + 45 * vertic * horizon);

        CPExecuteSkill packet = new CPExecuteSkill(executer.getSkill(this).getSlotId());
        packet.getBuffer().writeInt(vertic >= 0 ? 0 : 1);
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
        executer.playAnimationSynchronized(this.animations[i].get(), 0);
        executer.setModelYRot(yaw,true);
    }

    public Skill getPriorSkill() {
        return EpicFightSkills.STEP;
    }
}
