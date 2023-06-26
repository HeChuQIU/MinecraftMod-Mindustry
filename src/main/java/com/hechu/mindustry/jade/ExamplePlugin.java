package com.hechu.mindustry.jade;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.block.MechanicalDrill;
import com.hechu.mindustry.block.MechanicalDrillBlockEntity;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.*;

@WailaPlugin
public class ExamplePlugin implements IWailaPlugin {

    public static final ResourceLocation Drill = new ResourceLocation(Mindustry.MODID, "mechanical_drill");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(ExampleComponentProvider.INSTANCE, MechanicalDrillBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(ExampleComponentProvider.INSTANCE, MechanicalDrill.class);
    }

}
