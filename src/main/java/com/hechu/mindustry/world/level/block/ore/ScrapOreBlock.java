package com.hechu.mindustry.world.level.block.ore;

import net.minecraft.world.level.block.Block;

public class ScrapOreBlock extends Block {
    public static final String NAME = "scrap_ore";

    public ScrapOreBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }
}
