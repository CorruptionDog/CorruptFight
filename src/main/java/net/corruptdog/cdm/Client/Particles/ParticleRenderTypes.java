package net.corruptdog.cdm.Client.Particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexSorting;

import net.corruptdog.cdm.Client.Particles.Tyeps.AfterImageParticle;
import net.corruptdog.cdm.main.CDmoveset;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.world.entity.Entity;

@OnlyIn(Dist.CLIENT)
public class ParticleRenderTypes {
    public static final ResourceLocation NONE_TEXTURE = new ResourceLocation("none");
    public static final ResourceLocation AFTER_IMAGE_TEXTURE = new ResourceLocation(CDmoveset.MOD_ID, "textures/particle/after_image.png");

    public static final ParticleRenderType TRANSLUCENT = new ParticleRenderType() {
        private final TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        private final ResourceLocation defaultTextureLocation = NONE_TEXTURE;
        private boolean shouldGetTexture = false;

        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.setShader(GameRenderer::getPositionTexLightmapColorShader);

            shouldGetTexture = true;
            setTexture(textureManager);

            bufferBuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
        }

        @Override
        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.ORTHOGRAPHIC_Z);
            tesselator.end();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();
            AbstractTexture defaultTexture = textureManager.getTexture(defaultTextureLocation);
            GLSetTexture(defaultTexture);
            shouldGetTexture = false;
        }

        @Override
        public String toString() {
            return "CDMOVESET:TRANSLUCENT";
        }


        //        private void setTexture(TextureManager textureManager) {
//            Entity entity = Minecraft.getInstance().crosshairPickEntity;
//
//            if (entity instanceof AbstractClientPlayer) {
//                ResourceLocation textureLocation = ((AbstractClientPlayer) entity).getSkinTextureLocation();
//                AbstractTexture texture = textureManager.getTexture(textureLocation);
//                GLSetTexture(texture);
//            } else if (entity != null) {
//                ResourceLocation textureLocation = getEntityTexture(entity);
//                AbstractTexture texture = textureManager.getTexture(textureLocation);
//                GLSetTexture(texture);
//            } else {
//                AbstractClientPlayer player = Minecraft.getInstance().player;
//                if (player != null) {
//                    ResourceLocation textureLocation = player.getSkinTextureLocation();
//                    AbstractTexture texture = textureManager.getTexture(textureLocation);
//                    GLSetTexture(texture);
//                } else {
//                    AbstractTexture defaultTexture = textureManager.getTexture(AFTER_IMAGE_TEXTURE);
//                    GLSetTexture(defaultTexture);
//                }
//            }
//        }
        private void setTexture(TextureManager textureManager) {
            Entity entity = Minecraft.getInstance().crosshairPickEntity;

            if (entity instanceof AbstractClientPlayer) {
                ResourceLocation textureLocation = ((AbstractClientPlayer) entity).getSkinTextureLocation();
                AbstractTexture texture = textureManager.getTexture(textureLocation);
                AfterImageParticle.GLSetTexture(texture);
            } else if (entity != null) {
                ResourceLocation textureLocation = AfterImageParticle.getEntityTexture(entity);
                AbstractTexture texture = textureManager.getTexture(textureLocation);
                AfterImageParticle.GLSetTexture(texture);
            } else {
                AbstractClientPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    ResourceLocation textureLocation = player.getSkinTextureLocation();
                    AbstractTexture texture = textureManager.getTexture(textureLocation);
                    AfterImageParticle.GLSetTexture(texture);
                }else {
                    AbstractTexture texture = textureManager.getTexture(AFTER_IMAGE_TEXTURE);
                    AfterImageParticle.GLSetTexture(texture);
               }
            }
        }
    };

    public static void GLSetTexture(AbstractTexture abstractTexture) {
        RenderSystem.bindTexture(abstractTexture.getId());
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        RenderSystem.setShaderTexture(0, abstractTexture.getId());
    }
}
