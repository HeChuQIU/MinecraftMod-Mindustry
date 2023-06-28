package com.hechu.mindustry.jade;

import com.hechu.mindustry.block.DrillBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

public enum DrillComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getBlockEntity() instanceof DrillBlockEntity) {
            BlockState miningBlockState = ((DrillBlockEntity) accessor.getBlockEntity()).getMiningBlockState();
            if (miningBlockState == null)
                return;
            IElementHelper elements = tooltip.getElementHelper();
            IElement icon = elements.item(new ItemStack(miningBlockState.getBlock().asItem()), 0.8f).size(new Vec2(16, 16)).translate(new Vec2(0, -2));
            tooltip.add(icon);
            tooltip.append(Component.translatable("mindustry.drill_progress", (int) (((DrillBlockEntity) accessor.getBlockEntity()).getProgress() * 100)));
            tooltip.add(Component.translatable("mindustry.drill_speed", String.format("%.2f", ((DrillBlockEntity) accessor.getBlockEntity()).getMiningSpeed())));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return MindustryPlugin.Drill;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        if (blockEntity instanceof DrillBlockEntity) {
            compoundTag.putFloat("progress", ((DrillBlockEntity) blockEntity).getProgress());
            compoundTag.putFloat("miningSpeed", ((DrillBlockEntity) blockEntity).getMiningSpeed());
        }
    }
}