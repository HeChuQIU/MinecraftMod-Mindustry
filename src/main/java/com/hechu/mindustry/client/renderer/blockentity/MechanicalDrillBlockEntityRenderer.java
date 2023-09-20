package com.hechu.mindustry.client.renderer.blockentity;

import com.hechu.mindustry.world.level.block.entity.MechanicalDrillBlockEntity;
import com.hechu.mindustry.world.level.block.model.MechanicalDrillModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MechanicalDrillBlockEntityRenderer extends DrillBlockEntityRenderer<MechanicalDrillBlockEntity> {

    public MechanicalDrillBlockEntityRenderer() {
        super(new MechanicalDrillModel());
    }
}
