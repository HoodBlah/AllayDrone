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
    public ResourceLocation getTextureLocation(DroneEntity entity) {
        return TEXTURE;
    }
} 