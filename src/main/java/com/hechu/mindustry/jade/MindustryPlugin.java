package com.hechu.mindustry.jade;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.world.level.block.DrillBlock;
import com.hechu.mindustry.world.level.block.entity.DrillBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class MindustryPlugin implements IWailaPlugin {

    public static final ResourceLocation Drill = new ResourceLocation(Mindustry.MODID, "drill");
    public static final ResourceLocation HealthBlock = new ResourceLocation(Mindustry.MODID, "health_block");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(DrillComponentProvider.INSTANCE, DrillBlockEntity.class);
        registration.registerBlockDataProvider(HealthBlockComponentProvider.INSTANCE, BlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(DrillComponentProvider.INSTANCE, DrillBlock.class);
        registration.registerBlockComponent(HealthBlockComponentProvider.INSTANCE, BaseEntityBlock.class);
    }

}
