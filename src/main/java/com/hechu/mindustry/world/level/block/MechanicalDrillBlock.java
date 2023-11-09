package com.hechu.mindustry.world.level.block;

import com.hechu.mindustry.world.item.drill.MechanicalDrill;
import com.hechu.mindustry.world.level.block.entity.MechanicalDrillBlockEntity;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import snownee.kiwi.block.IKiwiBlock;

public class MechanicalDrillBlock extends DrillBlock<MechanicalDrillBlockEntity> implements IKiwiBlock {
    public static final String NAME = "mechanical_drill";

    public MechanicalDrillBlock() {
        super(Properties.of().noOcclusion().destroyTime(3).strength(3.0F, 3.0F),
                MechanicalDrillBlockEntity.class);
    }

    @Override
    public Vec3i getSize() {
        return  new Vec3i(2,1,2);
    }

    @Override
    public BlockItem createItem(Item.Properties builder) {
        return new MechanicalDrill(this, new Item.Properties());
    }
}
