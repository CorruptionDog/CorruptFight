package net.corruptdog.cdm.skill.Dodge;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.netty.buffer.Unpooled;
import java.util.UUID;

import net.corruptdog.cdm.gameasset.CDSkills;
import net.corruptdog.cdm.world.CorruptWeaponCategories;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.Input;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.SkillConsumeEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import static net.corruptdog.cdm.skill.CDSkillDataKeys.TARGET_ID;

public class EXYamatoDodge extends DodgeSkill {
    private static final UUID EVENT_UUID = UUID.fromString("5fcb382d-1ebe-4502-bf10-1bc5aa47cae4");
    private static final ResourceLocation EXTX = new ResourceLocation("cdmoveset", "textures/gui/skills/ex_yamato_step.png");

    public EXYamatoDodge(DodgeSkill.Builder builder) {
        super(builder);
    }
    public static EXYamatoDodge.Builder createDodgeBuilder() {
        return (new EXYamatoDodge.Builder()).setCategory(SkillCategories.DODGE).setActivateType(ActivateType.ONE_SHOT).setResource(Resource.STAMINA);
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        container.getExecuter().getEventListener().addEventListener(EventType.DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID, (event) -> {
            container.getDataManager().setDataSync(TARGET_ID.get(), event.getTarget().getId(), event.getPlayerPatch().getOriginal());
        });
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(EventType.DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID);
    }

    @OnlyIn(Dist.CLIENT)
    public FriendlyByteBuf gatherArguments(LocalPlayerPatch executer, ControllEngine controllEngine) {
        Input input = executer.getOriginal().input;
        float pulse = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(Minecraft.getInstance().player), 0.0F, 1.0F);
        input.tick(false,pulse);

        int forward = input.up ? 1 : 0;
        int backward = input.down ? -1 : 0;
        int left = input.left ? 1 : 0;
        int right = input.right ? -1 : 0;
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(forward);
        buf.writeInt(backward);
        buf.writeInt(left);
        buf.writeInt(right);
        return buf;
    }

    @OnlyIn(Dist.CLIENT)
    public Object getExecutionPacket(LocalPlayerPatch executer, FriendlyByteBuf args) {
        int forward = args.readInt();
        int backward = args.readInt();
        int left = args.readInt();
        int right = args.readInt();
        int vertic = forward + backward;
        int horizon = left + right;
        int degree = vertic == 0 ? 0 : -(90 * horizon * (1 - Math.abs(vertic)) + 45 * vertic * horizon);
        int animation;
        if (vertic == 0) {
            if (horizon == 0) {
                animation = 4;
            } else {
                animation = horizon >= 0 ? 2 : 3;
            }
        } else {
            animation = vertic >= 0 ? 0 : 1;
        }

        CPExecuteSkill packet = new CPExecuteSkill(executer.getSkill(this).getSlotId());
        packet.getBuffer().writeInt(animation);
        packet.getBuffer().writeFloat((float)degree);
        return packet;
    }

    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        if(!(executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.YAMATO)){
            SkillContainer dodge = executer.getSkillCapability().skillContainers[SkillCategories.DODGE.universalOrdinal()];
            dodge.setSkill(CDSkills.EX_YAMATO_STEP);
        }
        SkillConsumeEvent event = new SkillConsumeEvent(executer, this, this.resource);
        executer.getEventListener().triggerEvents(EventType.SKILL_CONSUME_EVENT, event);
        if (!event.isCanceled()) {
            event.getResourceType().consumer.consume(this, executer, event.getAmount());
        }

        executer.getSkill(this).activate();
        int i = args.readInt();
        float yaw = args.readFloat();
        boolean tag = false;
        LivingEntity target = (LivingEntity) executer.getOriginal().level().getEntity(executer.getSkill(this).getDataManager().getDataValue(TARGET_ID.get()));
        if (target != null) {
            if (target.distanceTo(executer.getOriginal()) < 30.0F) {
                tag = true;
            }

            if (!tag) {
                i = 1;
            }
        }

        StaticAnimation animation = this.animations[i].get();
        executer.playAnimationSynchronized(animation, 0.0F);
        executer.setYRot(yaw);
    }

    public Skill getPriorSkill() {
        return CDSkills.YAMATO_STEP;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        LivingEntity target = (LivingEntity) container.getExecuter().getOriginal().level().getEntity(container.getDataManager().getDataValue(TARGET_ID.get()));

        return target != null && target.distanceTo(container.getExecuter().getOriginal()) < 30.0F;
    }

    @OnlyIn(Dist.CLIENT)
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y) {
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.pushPose();
        poseStack.translate(0.0, (float)gui.getSlidingProgression(), 0.0);
        RenderSystem.setShaderTexture(0, this.getSkillTexture());
        guiGraphics.blit(EXTX, (int)x, (int)y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);
        poseStack.popPose();
    }

    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (!container.getExecuter().isLogicalClient() && container.getDataManager().getDataValue(TARGET_ID.get()) != null) {
            Entity target = container.getExecuter().getOriginal().level().getEntity(container.getDataManager().getDataValue(TARGET_ID.get()));
            if (target instanceof LivingEntity && !(target.isAlive())) {
                container.getDataManager().setDataSync(TARGET_ID.get(), -1, (ServerPlayer)container.getExecuter().getOriginal());
            }
        }

    }

}