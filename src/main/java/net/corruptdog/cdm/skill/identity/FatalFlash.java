package net.corruptdog.cdm.skill.identity;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.gameasset.CorruptSound;
import net.corruptdog.cdm.main.CDmoveset;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public class FatalFlash extends Skill {
    private static final UUID EVENT_UUID = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
    private static final ResourceLocation FATALFLASH_TEXTURE = new ResourceLocation("cdmoveset", "textures/gui/skills/fatalfalsh.png");
    private static final ResourceLocation FLASH1_TEXTURE = new ResourceLocation("cdmoveset", "textures/gui/screen/flash1.png");
    private static final ResourceLocation FLASH2_TEXTURE = new ResourceLocation("cdmoveset", "textures/gui/screen/flash2.png");
    private static final ResourceLocation FLASH3_TEXTURE = new ResourceLocation("cdmoveset", "textures/gui/screen/flash3.png");
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final long cooldownTime = 10000;
    private long lastUsedTime = 0;
    private int currentFlashIndex = 0;
    private int flashDuration = 15;
    private int flashCounter = 0;

    private boolean isOnCooldown = false;
    private static boolean skillTrue = false;

    private final ResourceLocation[] flashTextures = {FLASH1_TEXTURE, FLASH2_TEXTURE, FLASH3_TEXTURE};


    public static FatalFlash.Builder createRevelationSkillBuilder() {
        return (new Builder())
                .setCategory(SkillCategories.IDENTITY)
                .setResource(Resource.COOLDOWN);
    }

    public static class Builder extends Skill.Builder<FatalFlash> {
        protected final Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, StaticAnimation>> motions = Maps.newHashMap();
        protected final Set<WeaponCategory> availableWeapons = Sets.newHashSet();

        public FatalFlash.Builder setCategory(SkillCategory category) {
            this.category = category;
            return this;
        }

        public FatalFlash.Builder setResource(Resource resource) {
            this.resource = resource;
            return this;
        }

        public FatalFlash.Builder addAvailableWeaponCategory(WeaponCategory... wc) {
            this.availableWeapons.addAll(Arrays.asList(wc));
            return this;
        }
    }

    private final Set<WeaponCategory> availableWeapons;

    public FatalFlash(Builder builder) {
        super(builder);
        this.availableWeapons = builder.availableWeapons;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        PlayerEventListener listener = container.getExecuter().getEventListener();
        PlayerPatch<?> playerPatch = container.getExecuter();
        Player original = playerPatch.getOriginal();
        listener.addEventListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID, (event) -> {
            LivingEntity target = container.getExecuter().getTarget();
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastUsedTime < cooldownTime || !this.availableWeapons.contains(container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory())) {
                return;
            }
            if (target != null) {
                double distance = original.distanceTo(target);
                if (distance <= 10.0 && target.getHealth() < target.getMaxHealth() * 0.3) {

                    event.setCanceled(true);
                    container.getExecuter().playSound(CorruptSound.FATALFLASH.get(), 0.8F, 1.2F);
                    playerPatch.playAnimationSynchronized(CorruptAnimations.FATALFLASH_READY, 0.2F);
                    lastUsedTime = currentTime;
                    if (isOnCooldown) {
                        return;
                    }
                    isOnCooldown = true;
                    scheduler.schedule(() -> {
                        isOnCooldown = false;
                    }, cooldownTime, TimeUnit.MILLISECONDS);
                    skillTrue = true;
                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        playerPatch.playAnimationSynchronized(CorruptAnimations.FATALFLASH, 0.0F);
                        skillTrue = false;
                    }).start();
                }
            }
        });
        listener.addEventListener(PlayerEventListener.EventType.TARGET_INDICATOR_ALERT_CHECK_EVENT, EVENT_UUID, (event) -> {
            LivingEntity target = playerPatch.getTarget();
            double distance = original.distanceTo(target);
            if (!isOnCooldown && distance <= 10.0 && target.getHealth() < target.getMaxHealth() * 0.3) {
                event.setCanceled(false);
            }
        });
    }
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.TARGET_INDICATOR_ALERT_CHECK_EVENT, EVENT_UUID);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean shouldDraw(SkillContainer container) {
        PlayerPatch<?> playerPatch = container.getExecuter();
        LivingEntity target = playerPatch.getTarget();
        if (isOnCooldown || !this.availableWeapons.contains(container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory())) {
            return true;
        }
        if (target != null) {
            double distance = playerPatch.getOriginal().distanceTo(target);
            return distance < 10.0;
        }

        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(0.0F, (float) gui.getSlidingProgression(), 0.0F);
        if (isOnCooldown) {
            RenderSystem.setShaderColor(0.5F, 0.5F, 0.5F, 1.0F);
        } else {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }


        guiGraphics.blit(FATALFLASH_TEXTURE, (int) x, (int) y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);

        if (isOnCooldown) {
            long remainingCooldown = cooldownTime - (System.currentTimeMillis() - lastUsedTime);
            String cooldownString = String.valueOf(remainingCooldown / 1000);
            guiGraphics.drawString(gui.font, cooldownString, x + 13.0F - (float) (gui.font.width(cooldownString)), y + 10.0F, 16777215, true);
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void onScreen(LocalPlayerPatch playerpatch, float resolutionX, float resolutionY) {
        if (skillTrue) {
            flashCounter++;
            if (flashCounter >= flashDuration) {
                flashCounter = 0;
                currentFlashIndex = (currentFlashIndex + 1) % 3;
            }
            ResourceLocation currentTexture = switch (currentFlashIndex) {
                case 1 -> FLASH2_TEXTURE;
                case 2 -> FLASH3_TEXTURE;
                default -> FLASH1_TEXTURE;
            };
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, currentTexture);
            GlStateManager._enableBlend();
            GlStateManager._disableDepthTest();
            GlStateManager._blendFunc(770, 771);

            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0, 0.0, 1.0).uv(0.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0, resolutionY, 1.0).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(resolutionX, resolutionY, 1.0).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(resolutionX, 0.0, 1.0).uv(1.0F, 0.0F).endVertex();
            tessellator.end();
        }

    }
}

