package com.hechu.mindustry.world.level.block.ore;

import net.minecraft.world.level.block.Block;

public class ThoriumOreBlock extends Block {
    public static final String NAME = "thorium_ore";

    public ThoriumOreBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }
}
