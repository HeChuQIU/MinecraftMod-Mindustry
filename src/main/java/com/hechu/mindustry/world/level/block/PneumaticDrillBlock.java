package com.hechu.mindustry.world.level.block;

import com.hechu.mindustry.world.item.drill.PneumaticDrill;
import com.hechu.mindustry.world.level.block.entity.PneumaticDrillBlockEntity;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import snownee.kiwi.block.IKiwiBlock;

public class PneumaticDrillBlock extends DrillBlock<PneumaticDrillBlockEntity> implements IKiwiBlock {
    public static final String NAME = "pneumatic_drill";

    public PneumaticDrillBlock() {
        super(Properties.of().noOcclusion().destroyTime(3).strength(3.0F, 3.0F),
                PneumaticDrillBlockEntity.class);
    }

    @Override
    public Vec3i getSize() {
        return new Vec3i(2, 1, 2);
    }

    @Override
    public BlockItem createItem(Item.Properties builder) {
        return new PneumaticDrill(this, new Item.Properties());
    }
}
