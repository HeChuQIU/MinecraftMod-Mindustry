package com.hechu.mindustry.jade;

import com.hechu.mindustry.world.level.block.entity.HealthTestBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IBoxStyle;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IProgressStyle;
import snownee.jade.impl.ui.ProgressElement;
import snownee.jade.impl.ui.ProgressStyle;

public enum HealthBlockComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        float health = accessor.getServerData().getFloat("health");
        float maxHealth = accessor.getServerData().getFloat("maxHealth");

        ProgressStyle progressStyle = new ProgressStyle();
        progressStyle.color = 0xff0000|0xff<<24;
        progressStyle.color2 = 0x000000|0x88<<24;
        progressStyle.glowText = true;

        BoxStyle boxStyle = new BoxStyle();

        ProgressElement progressElement = new ProgressElement(health / maxHealth,
                Component.translatable("mindustry.block_health", health, maxHealth),
                progressStyle, boxStyle, true);
//        progressElement.size(new Vec2(100f,progressElement.getSize().y));
        tooltip.add(progressElement);
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        HealthTestBlockEntity healthBlockEntity = (HealthTestBlockEntity) accessor.getBlockEntity();
        data.putFloat("health", healthBlockEntity.getHealth());
        data.putFloat("maxHealth", healthBlockEntity.getMaxHealth());
    }

    @Override
    public ResourceLocation getUid() {
        return MindustryPlugin.HealthBlock;
    }
}
