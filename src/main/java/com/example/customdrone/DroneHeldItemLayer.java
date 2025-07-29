package com.example.customdrone;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.desht.pneumaticcraft.common.entity.drone.DroneEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemStack;

public class DroneHeldItemLayer extends RenderLayer<DroneEntity, AllayDroneModel<DroneEntity>> {

    private final ItemInHandRenderer itemRenderer;

    public DroneHeldItemLayer(CustomDroneRenderer parent, ItemInHandRenderer renderer) {
        super(parent);
        this.itemRenderer = renderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, DroneEntity drone, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = drone.getDroneHeldItem();
        if (stack.isEmpty()) return;

        poseStack.pushPose();
        // align with right arm
        AllayDroneModel<?> model = this.getParentModel();
        model.getRightArm().translateAndRotate(poseStack);
        poseStack.translate(0.0F, 0.2F, 0.0F); // lower & forward
        poseStack.scale(0.5F, 0.5F, 0.5F);
        // Rotate so item points forward
        poseStack.mulPose(Axis.XP.rotationDegrees(90f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
        itemRenderer.renderItem(drone, stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
} 