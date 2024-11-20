//package net.corruptdog.cdm.world.RanDer;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.corruptdog.cdm.world.item.CDAddonItems;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.item.ItemDisplayContext;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import yesman.epicfight.api.utils.math.OpenMatrix4f;
//import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
//import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
//
//@OnlyIn(Dist.CLIENT)
//public class RenderYamato extends RenderItemBase {
//    private final ItemStack yamatoBlade = new ItemStack(CDAddonItems.YAMATO_BLADE.get());
//
//    @Override
//    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
//        OpenMatrix4f modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
//        modelMatrix.mulFront(entitypatch.getArmature().searchJointByName("Tool_L").getLocalTrasnform());
//
//        poseStack.pushPose();
//        this.mulPoseStack(poseStack, modelMatrix);
//        Minecraft.getInstance().getItemRenderer().renderStatic(stack,  ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, null, 0);
//        poseStack.popPose();
//
//        ItemStack blade = entitypatch.getOriginal().getMainHandItem();
//        if (blade.getTag() != null && blade.getTag().getBoolean("unsheathed")) {
//            modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
//            modelMatrix.mulFront(entitypatch.getArmature().searchJointByName("Tool_R").getLocalTrasnform());
//
//            poseStack.pushPose();
//            this.mulPoseStack(poseStack, modelMatrix);
//            Minecraft.getInstance().getItemRenderer().renderStatic(this.yamatoBlade,  ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, null, 0);
//            poseStack.popPose();
//        }
//    }
//}
