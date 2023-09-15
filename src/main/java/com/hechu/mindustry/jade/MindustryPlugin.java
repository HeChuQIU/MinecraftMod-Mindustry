package com.hechu.mindustry.jade;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.world.level.block.DrillBlock;
import com.hechu.mindustry.world.level.block.entity.DrillBlockEntity;
import com.hechu.mindustry.world.level.block.HealthTestBlock;
import com.hechu.mindustry.world.level.block.entity.HealthTestBlockEntity;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.*;

@WailaPlugin
public class MindustryPlugin implements IWailaPlugin {

    public static final ResourceLocation Drill = new ResourceLocation(Mindustry.MODID, "drill");
    public static final ResourceLocation HealthBlock = new ResourceLocation(Mindustry.MODID, "health_block");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(DrillComponentProvider.INSTANCE, DrillBlockEntity.class);
        registration.registerBlockDataProvider(HealthBlockComponentProvider.INSTANCE, HealthTestBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(DrillComponentProvider.INSTANCE, DrillBlock.class);
        registration.registerBlockComponent(HealthBlockComponentProvider.INSTANCE, HealthTestBlock.class);
    }

}
