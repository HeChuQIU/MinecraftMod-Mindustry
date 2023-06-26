package com.hechu.mindustry.jade;

import com.hechu.mindustry.block.MechanicalDrillBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

public enum ExampleComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("progress")) {
            tooltip.append(Component.translatable("mindustry.drill_progress", accessor.getServerData().getInt("progress")));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ExamplePlugin.Drill;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        if (blockEntity instanceof MechanicalDrillBlockEntity) {
            compoundTag.putInt("progress", (int) (((MechanicalDrillBlockEntity) blockEntity).getProgress() * 100));
        }
    }
}