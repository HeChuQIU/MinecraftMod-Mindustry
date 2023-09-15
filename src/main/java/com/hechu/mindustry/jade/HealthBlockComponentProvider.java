package com.hechu.mindustry.jade;

import com.hechu.mindustry.world.level.block.entity.HealthTestBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum HealthBlockComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        tooltip.append(Component.translatable("mindustry.block_health", accessor.getServerData().getInt("health")));
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        HealthTestBlockEntity healthBlockEntity = (HealthTestBlockEntity) accessor.getBlockEntity();
        data.putInt("health", healthBlockEntity.getHealth());
    }

    @Override
    public ResourceLocation getUid() {
        return MindustryPlugin.HealthBlock;
    }
}
