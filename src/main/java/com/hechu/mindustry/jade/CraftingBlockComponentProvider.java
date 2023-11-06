package com.hechu.mindustry.jade;

import com.hechu.mindustry.world.level.block.entity.multiblock.MultiblockCraftingBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.impl.ui.ProgressStyle;

public enum CraftingBlockComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity instanceof MultiblockCraftingBlockEntity craftingBlockEntity) {
            IElementHelper elements = tooltip.getElementHelper();

            tooltip.add(Component.empty());

            craftingBlockEntity.getInputs().forEach(itemStack -> {
                IElement icon = elements.item(itemStack, 0.8f).size(new Vec2(16, 16)).translate(new Vec2(0, -2));
                tooltip.append(icon);
            });

            int craftTicks = craftingBlockEntity.getCraftTicks();
            int currentCraftTicks = accessor.getServerData().getInt("currentCraftTicks");

            ProgressStyle progressStyle = new ProgressStyle();
            progressStyle.color = 0xffffff | 0xff << 24;
//            progressStyle.color2 = 0x000000 | 0x88 << 24;
            progressStyle.glowText = true;

            BoxStyle boxStyle = new BoxStyle();
            IElement progress = elements.progress((float) currentCraftTicks / craftTicks, Component.literal("          "), progressStyle, boxStyle, true);
            tooltip.append(progress);

            craftingBlockEntity.getOutputs().forEach(itemStack -> {
                IElement icon = elements.item(itemStack, 0.8f).size(new Vec2(16, 16)).translate(new Vec2(0, -2));
                tooltip.append(icon);
            });
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (accessor.getBlockEntity() instanceof MultiblockCraftingBlockEntity blockEntity)
            data.putInt("currentCraftTicks", blockEntity.getCurrentCraftTicks());
    }

    @Override
    public ResourceLocation getUid() {
        return MindustryPlugin.CraftingBlock;
    }
}
