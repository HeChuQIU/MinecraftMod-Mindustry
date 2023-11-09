package com.hechu.mindustry.world.level.block.multiblock;

import com.hechu.mindustry.annotation.Block;
import com.hechu.mindustry.world.level.block.entity.multiblock.KilnBlockEntity;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import snownee.kiwi.block.IKiwiBlock;

@Block(name = KilnBlock.NAME)
public class KilnBlock extends MultiblockCraftingBlock<KilnBlockEntity> implements IKiwiBlock {
    public static final String NAME = "kiln_block";
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 3);

    public KilnBlock() {
        super(Properties.of().destroyTime(3).strength(3.0F, 3.0F), KilnBlockEntity.class);
    }

    @Override
    public IntegerProperty getPartProperty() {
        return PART;
    }

    @Override
    public Vec3i getSize() {
        return new Vec3i(2,1,2);
    }

    @Override
    public String getBlockName() {
        return NAME;
    }

    @Override
    public boolean isSingleTexture() {
        return true;
    }
}
