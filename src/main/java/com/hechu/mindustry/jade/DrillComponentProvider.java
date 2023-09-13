package com.hechu.mindustry.jade;

import com.hechu.mindustry.block.DrillBlockEntity;
import com.hechu.mindustry.block.HealthTestBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum DrillComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity instanceof DrillBlockEntity drillBlockEntity) {
            BlockState miningBlockState = drillBlockEntity.getMiningBlockState();
            if (miningBlockState == null)
                return;
            IElementHelper elements = tooltip.getElementHelper();
            IElement icon = elements.item(new ItemStack(miningBlockState.getBlock().asItem()), 0.8f).size(new Vec2(16, 16)).translate(new Vec2(0, -2));
            tooltip.add(icon);
            tooltip.append(Component.translatable("mindustry.drill_progress", (int) (drillBlockEntity.getProgress() * 100)));
            tooltip.add(Component.translatable("mindustry.drill_speed", String.format("%.2f", drillBlockEntity.getMiningSpeed())));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        DrillBlockEntity drill= (DrillBlockEntity) accessor.getBlockEntity();
        data.putFloat("progress", drill.getProgress());
        data.putFloat("miningSpeed", drill.getMiningSpeed());
    }

    @Override
    public ResourceLocation getUid() {
        return MindustryPlugin.Drill;
    }
}