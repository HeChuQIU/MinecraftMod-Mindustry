package com.hechu.mindustry.world.level.block.ore;

import net.minecraft.world.level.block.Block;

public class LeadOreBlock extends Block {
    public static final String NAME = "lead_ore";

    public LeadOreBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F));
    }

}
