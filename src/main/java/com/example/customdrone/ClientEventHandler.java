package com.example.customdrone;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import me.desht.pneumaticcraft.common.entity.drone.DroneEntity;

@Mod.EventBusSubscriber(modid = CustomDroneMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AllayDroneModel.LAYER_LOCATION, AllayDroneModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void clientSetup(net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            @SuppressWarnings("unchecked")
            EntityType<? extends DroneEntity> droneType = (EntityType<? extends DroneEntity>) ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("pneumaticcraft:drone"));
            if (droneType != null) {
                net.minecraft.client.renderer.entity.EntityRenderers.register(droneType, CustomDroneRenderer::new);
            }
        });
    }
} 