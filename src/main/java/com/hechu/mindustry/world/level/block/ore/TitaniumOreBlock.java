package com.hechu.mindustry.world.level.block.ore;

import net.minecraft.world.level.block.Block;

public class TitaniumOreBlock extends Block {
    public static final String NAME = "titanium_ore";

    public TitaniumOreBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }
}
