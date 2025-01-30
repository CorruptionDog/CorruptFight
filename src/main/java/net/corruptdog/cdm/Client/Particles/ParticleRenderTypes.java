package net.corruptdog.cdm.Client.Particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexSorting;

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
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@OnlyIn(Dist.CLIENT)
public class ParticleRenderTypes {
    public static final ResourceLocation NoneTexture = new ResourceLocation("none");
    public static final ParticleRenderType TRANSLUCENT = new ParticleRenderType() {
        private final TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        private final AbstractTexture noneTexture = textureManager.getTexture(NoneTexture);
        private final AbstractTexture afterImageTexture;

        {

            AbstractClientPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                afterImageTexture = textureManager.getTexture(player.getSkinTextureLocation());
            } else {
                afterImageTexture = textureManager.getTexture(new ResourceLocation(CDmoveset.MOD_ID, "textures/particle/after_image.png"));
            }
        }

        public void begin(BufferBuilder bufferBuilder, @NotNull TextureManager textureManager) {
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);
            GLSetTexture(afterImageTexture);
            bufferBuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.ORTHOGRAPHIC_Z);
            tesselator.end();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();
            GLSetTexture(noneTexture);
        }

        public String toString() {
            return "CDMOVESET:TRANSLUCENT";
        }
    };

    public static void GLSetTexture(AbstractTexture abstractTexture) {
        RenderSystem.bindTexture(abstractTexture.getId());
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE);
        RenderSystem.setShaderTexture(0, abstractTexture.getId());
    }
}