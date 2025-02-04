//package net.corruptdog.cdm.Client;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.corruptdog.cdm.main.CDmoveset;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.entity.EntityRenderer;
//import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
//import net.minecraft.client.renderer.texture.AbstractTexture;
//import net.minecraft.client.renderer.texture.TextureManager;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.Entity;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import org.lwjgl.opengl.GL11;
//import org.lwjgl.opengl.GL12;
//
//@OnlyIn(Dist.CLIENT)
//public class EntityTextureHelper {
//
//    public static final ResourceLocation AFTER_IMAGE_TEXTURE = new ResourceLocation(CDmoveset.MOD_ID, "textures/particle/after_image.png");
//
//    public static ResourceLocation getEntityTexture(Entity entity) {
//        if (Minecraft.getInstance().level != null && entity != null) {
//            EntityRenderer<?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
//            if(renderer instanceof HumanoidMobRenderer<?,?>){
//                return Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity).getTextureLocation(entity);
//            }
//        }
//        return AFTER_IMAGE_TEXTURE;
//    }
//
//
//    public static void GLSetTexture(TextureManager textureManager, AbstractTexture abstractTexture) {
//        RenderSystem.bindTexture(abstractTexture.getId());
//        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
//        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
//        RenderSystem.setShaderTexture(0, abstractTexture.getId());
//    }
//}
