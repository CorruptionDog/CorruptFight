package net.corruptdog.cdm.Client.Particles.Tyeps;

import net.corruptdog.cdm.Client.Particles.ParticleRenderTypes;
import net.corruptdog.cdm.main.CDmoveset;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.MeshProvider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.QuaternionUtils;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.particle.CustomModelParticle;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.client.renderer.shader.AnimationShaderInstance;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class AfterImageParticle extends CustomModelParticle<AnimatedMesh> {
    private final OpenMatrix4f[] poseMatrices;
    private final Matrix4f modelMatrix;
    private float alphaO;
    public static final ResourceLocation AFTER_IMAGE_TEXTURE = new ResourceLocation(CDmoveset.MOD_ID, "textures/particle/after_image.png");


    public AfterImageParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, MeshProvider<AnimatedMesh> particleMesh, OpenMatrix4f[] matrices, Matrix4f modelMatrix) {
        super(level, x, y, z, xd, yd, zd, particleMesh);
        this.poseMatrices = matrices;
        this.modelMatrix = modelMatrix;
        this.lifetime = 20;
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.alphaO = 0.3F;
        this.alpha = 0.3F;
    }

    @Override
    public void tick() {
        super.tick();
        this.alphaO = this.alpha;
        this.alpha = (float)(this.lifetime - this.age) / (float)this.lifetime * 0.8F;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        PoseStack poseStack = new PoseStack();
        poseStack.mulPoseMatrix(RenderSystem.getModelViewMatrix());
        this.setupPoseStack(poseStack, camera, partialTicks);
        poseStack.mulPoseMatrix(this.modelMatrix);
        float alpha = this.alphaO + (this.alpha - this.alphaO) * partialTicks;

        AnimationShaderInstance animShader = null;
        if (GameRenderer.getPositionTexLightmapColorShader() != null) {
            animShader = EpicFightRenderTypes.getAnimationShader(GameRenderer.getPositionTexLightmapColorShader());
        }
        this.particleMeshProvider.get().drawWithShader(poseStack, animShader, this.getLightColor(partialTicks), this.rCol, this.gCol, this.bCol, alpha, OverlayTexture.NO_OVERLAY, null, this.poseMatrices);
    }

    @Override
    protected void setupPoseStack(PoseStack poseStack, Camera camera, float partialTicks) {
        Quaternionf rotation = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
        float roll = Mth.lerp(partialTicks, this.oRoll, this.roll);
        float pitch = Mth.lerp(partialTicks, this.pitchO, this.pitch);
        float yaw = Mth.lerp(partialTicks, this.yawO, this.yaw);
        rotation.mul(QuaternionUtils.YP.rotationDegrees(yaw));
        rotation.mul(QuaternionUtils.XP.rotationDegrees(pitch));
        rotation.mul(QuaternionUtils.ZP.rotationDegrees(roll));

        Vec3 vec3 = camera.getPosition();
        float x = (float)(Mth.lerp(partialTicks, this.xo, this.x) - vec3.x());
        float y = (float)(Mth.lerp(partialTicks, this.yo, this.y) - vec3.y());
        float z = (float)(Mth.lerp(partialTicks, this.zo, this.z) - vec3.z());
        float scale = (float)Mth.lerp((double)partialTicks, this.scaleO, this.scale);

        poseStack.translate(x, y, z);
        poseStack.mulPose(rotation);
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderTypes.TRANSLUCENT;
    }
    public static ResourceLocation getEntityTexture(Entity entity) {
        if (Minecraft.getInstance().level != null && entity != null) {
            EntityRenderer<?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
            if(renderer instanceof HumanoidMobRenderer<?,?>){
                return Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity).getTextureLocation(entity);
            }
        }
        return AFTER_IMAGE_TEXTURE;
    }
    public static void GLSetTexture(AbstractTexture abstractTexture) {
       RenderSystem.bindTexture(abstractTexture.getId());
      RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
       RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
       RenderSystem.setShaderTexture(0, abstractTexture.getId());
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public Particle createParticle(@NotNull SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            Entity entity = level.getEntity((int) Double.doubleToLongBits(xSpeed));
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (entitypatch != null && ClientEngine.getInstance().renderEngine.hasRendererFor(entitypatch.getOriginal())) {
                PatchedEntityRenderer renderer = ClientEngine.getInstance().renderEngine.getEntityRenderer(entitypatch.getOriginal());
                Armature armature = entitypatch.getArmature();
                PoseStack poseStack = new PoseStack();
                renderer.mulPoseStack(poseStack, armature, entitypatch.getOriginal(), entitypatch, 1.0F);
                Pose pose = entitypatch.getAnimator().getPose(1.0F);
                renderer.mulPoseStack(poseStack, armature, entitypatch.getOriginal(), entitypatch, 1.0F);
                OpenMatrix4f[] matrices = armature.getPoseAsTransformMatrix(pose, true);
                MeshProvider<AnimatedMesh> meshProvider = renderer.getMeshProvider(entitypatch);

                return new AfterImageParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, meshProvider, matrices, poseStack.last().pose());
            }

            return null;
        }
    }
}