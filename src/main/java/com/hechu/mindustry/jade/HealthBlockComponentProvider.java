package com.hechu.mindustry.jade;

import com.hechu.mindustry.utils.capabilities.MindustryCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.impl.ui.ProgressElement;
import snownee.jade.impl.ui.ProgressStyle;

public enum HealthBlockComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity blockEntity = accessor.getBlockEntity();

        if (blockEntity.getCapability(MindustryCapabilities.HEALTH_HANDLER).isPresent()) {
            float health = accessor.getServerData().getFloat("health");
            float maxHealth = accessor.getServerData().getFloat("maxHealth");

            ProgressStyle progressStyle = new ProgressStyle();
            progressStyle.color = 0xff0000 | 0xff << 24;
            progressStyle.color2 = 0x88 << 24;
            progressStyle.glowText = true;

            BoxStyle boxStyle = new BoxStyle();

            ProgressElement progressElement = new ProgressElement(health / maxHealth,
                    Component.translatable("mindustry.block_health", health, maxHealth),
                    progressStyle, boxStyle, true);
            tooltip.add(progressElement);
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        BlockEntity blockEntity = accessor.getBlockEntity();

        blockEntity.getCapability(MindustryCapabilities.HEALTH_HANDLER).ifPresent(healthHandler -> {
            data.putFloat("health", healthHandler.getHealth());
            data.putFloat("maxHealth", healthHandler.getMaxHealth());
        });
    }

    @Override
    public ResourceLocation getUid() {
        return MindustryPlugin.HealthBlock;
    }
}