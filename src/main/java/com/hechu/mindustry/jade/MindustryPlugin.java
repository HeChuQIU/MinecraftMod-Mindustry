package com.hechu.mindustry.jade;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.block.DrillBlock;
import com.hechu.mindustry.block.DrillBlockEntity;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.*;

@WailaPlugin
public class MindustryPlugin implements IWailaPlugin {

    public static final ResourceLocation Drill = new ResourceLocation(Mindustry.MODID, "drill");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(DrillComponentProvider.INSTANCE, DrillBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(DrillComponentProvider.INSTANCE, DrillBlock.class);
    }

}
