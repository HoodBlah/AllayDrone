package com.example.customdrone;

import me.desht.pneumaticcraft.common.entity.drone.DroneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CustomDroneRenderer extends MobRenderer<DroneEntity, AllayDroneModel<DroneEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(CustomDroneMod.MODID, "textures/entity/custom_drone.png");

    private static final java.util.Map<Integer, Float> LAST_YAW = new java.util.WeakHashMap<>();

    public CustomDroneRenderer(EntityRendererProvider.Context context) {
        super(context, new AllayDroneModel<>(context.bakeLayer(AllayDroneModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    protected void setupRotations(DroneEntity entity, com.mojang.blaze3d.vertex.PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        // Orient the drone to face its movement vector rather than its intrinsic yaw (which PNC drones don't update)
        net.minecraft.world.phys.Vec3 delta = entity.getDeltaMovement();

        // Calculate desired yaw from horizontal motion
        float desiredYaw;
        if (delta.lengthSqr() > 1.0E-4) {
            desiredYaw = (float) (net.minecraft.util.Mth.atan2(-delta.x, delta.z) * (180F / Math.PI));
        } else {
            desiredYaw = LAST_YAW.getOrDefault(entity.getId(), entity.getYRot());
        }

        float previous = LAST_YAW.getOrDefault(entity.getId(), desiredYaw);
        float smoothYaw = net.minecraft.util.Mth.rotLerp(0.15F, previous, desiredYaw);
        LAST_YAW.put(entity.getId(), smoothYaw);

        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F - smoothYaw));

        // Pitch a little when ascending / descending (optional subtle effect)
        double horizMag = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
        if (horizMag > 1.0E-4) {
            float desiredPitch = (float) (net.minecraft.util.Mth.atan2(delta.y, horizMag) * (180F / Math.PI));
            float smoothPitch = net.minecraft.util.Mth.clamp(desiredPitch, -30F, 30F) * 0.4F;
            poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(smoothPitch));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(DroneEntity entity) {
        return TEXTURE;
    }
} 