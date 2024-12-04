package net.corruptdog.cdm.Client.Particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexSorting;

import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleRenderTypes {
    public static final ParticleRenderType TRANSLUCENT = new ParticleRenderType() {
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);
            bufferBuilder.begin(VertexFormat.Mode.TRIANGLES,DefaultVertexFormat.POSITION_COLOR_LIGHTMAP);

        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.DISTANCE_TO_ORIGIN);
            tesselator.end();

            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();

        }

        public String toString() {
            return "CDMOVESET:TRANSLUCENT";
        }
    };
}