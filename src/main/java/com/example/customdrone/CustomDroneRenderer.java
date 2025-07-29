package com.example.customdrone;

import me.desht.pneumaticcraft.common.entity.drone.DroneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CustomDroneRenderer extends MobRenderer<DroneEntity, AllayDroneModel<DroneEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(CustomDroneMod.MODID, "textures/entity/custom_drone.png");

    public CustomDroneRenderer(EntityRendererProvider.Context context) {
        super(context, new AllayDroneModel<>(context.bakeLayer(AllayDroneModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    protected void setupRotations(DroneEntity entity, com.mojang.blaze3d.vertex.PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        // Orient the drone to face its movement vector rather than its intrinsic yaw (which PNC drones don't update)
        net.minecraft.world.phys.Vec3 delta = entity.getDeltaMovement();
        if (delta.lengthSqr() > 1.0E-4) {
            double dx = delta.x;
            double dz = delta.z;
            double dy = delta.y;
            float yaw = (float) (net.minecraft.util.Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
            float pitch = (float) (net.minecraft.util.Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * (180F / Math.PI));
            poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-yaw));
            poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(pitch));
        } else {
            super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(DroneEntity entity) {
        return TEXTURE;
    }
} 