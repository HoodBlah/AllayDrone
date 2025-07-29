package com.example.customdrone;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;

@Mod(CustomDroneMod.MODID)
public class CustomDroneMod {
    public static final String MODID = "customdrone";

    public CustomDroneMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // No mod-init specific logic here yet
    }
} 