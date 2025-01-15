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
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
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

            Minecraft mc = Minecraft.getInstance();
            mc.gameRenderer.lightTexture().turnOnLightLayer();

            bufferBuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_LIGHTMAP);
            RenderSystem.setShaderTexture(0, new ResourceLocation(CDmoveset.MOD_ID, "after_image")
            );
        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.DISTANCE_TO_ORIGIN);
            tesselator.end();

            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();

            Minecraft mc = Minecraft.getInstance();
            mc.gameRenderer.lightTexture().turnOffLightLayer();
        }

        public String toString() {
            return "CDMOVESET:TRANSLUCENT";
        }
    };

    public static ResourceLocation GetTexture(String path){
        return new ResourceLocation(CDmoveset.MOD_ID, "textures/" + path + ".png");
    }

}