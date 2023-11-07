package com.hechu.mindustry.world.level.block.ore;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CoalOreBlock extends Block {
    public static final String NAME = "coal_ore";

    public CoalOreBlock() {
        super(BlockBehaviour.Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }
}
