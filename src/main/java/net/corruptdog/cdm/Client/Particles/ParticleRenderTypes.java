package net.corruptdog.cdm.Client.Particles;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.corruptdog.cdm.Client.Particles.Tyeps.RenderUtils;
import net.corruptdog.cdm.main.CDmoveset;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class ParticleRenderTypes {

    public static final ResourceLocation NONE_TEXTURE = new ResourceLocation("none");
    public static final ResourceLocation AFTER_IMAGE_TEXTURE = new ResourceLocation(CDmoveset.MOD_ID, "textures/particle/after_image.png");




    private static int quadIdx = 0;
    public static final HashMap<ResourceLocation, AirWave> QuadRenderTypes = Maps.newHashMap();
    public static AirWave getRenderTypeByTexture(ResourceLocation texture){
        if(QuadRenderTypes.containsKey(texture)){
            return QuadRenderTypes.get(texture);
        }
        else {
            AirWave rdt = new AirWave("epicacg:quad_particle_" + quadIdx++, texture);
            QuadRenderTypes.put(texture,rdt);
            return rdt;
        }
    }

    private static int triangleIdx = 0;
    public static final HashMap<ResourceLocation, AirWave> TriangleRenderTypes = Maps.newHashMap();
    public static AirWave getTriangleRenderTypeByTexture(ResourceLocation texture){
        if(TriangleRenderTypes.containsKey(texture)){
            return TriangleRenderTypes.get(texture);
        }
        else {
            AirWave rdt = new AirWave("epicacg:triangle_particle_" + triangleIdx++, texture);
            TriangleRenderTypes.put(texture,rdt);
            return rdt;
        }
    }

    public static class AirWave implements ParticleRenderType {
        private final ResourceLocation Texture;
        private final String Name;

        public AirWave(String name, ResourceLocation tex) {
            this.Texture = tex;
            Name = name;
        }

        public void begin(BufferBuilder p_107448_, TextureManager p_107449_) {
            RenderSystem.enableBlend();
            RenderSystem.disableCull();

            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.setShader(GameRenderer::getParticleShader);

            if(Texture != null) RenderUtils.GLSetTexture(Texture);

            p_107448_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.ORTHOGRAPHIC_Z);
            tesselator.end();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();
        }

        public String toString() {
            return Name;
        }
    };
    public static final ParticleRenderType TRANSLUCENT = new ParticleRenderType() {
        private static final TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        private final AbstractTexture afterImageTexture;

        {
            AbstractClientPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                afterImageTexture = textureManager.getTexture(player.getSkinTextureLocation());
            } else {
                afterImageTexture = textureManager.getTexture(AFTER_IMAGE_TEXTURE);
            }
        }
        @Override
        public void begin(@NotNull BufferBuilder bufferBuilder, @NotNull TextureManager textureManager) {
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.setShader(GameRenderer::getParticleShader);
            GLSetTexture(afterImageTexture);
            Minecraft mc = Minecraft.getInstance();
            mc.gameRenderer.lightTexture().turnOnLightLayer();
            bufferBuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
        }

        @Override
        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.ORTHOGRAPHIC_Z);
            tesselator.end();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();
            RenderSystem.bindTexture(0);
        }


        @Override
        public String toString() {
            return "CDMOVESET:TRANSLUCENT";
        }
        public static void GLSetTexture(AbstractTexture abstractTexture) {
            RenderSystem.bindTexture(abstractTexture.getId());
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            RenderSystem.setShaderTexture(0, abstractTexture.getId());
        }
    };
}