package com.hechu.mindustry.client.renderer.block;

import com.hechu.mindustry.world.level.block.entity.PneumaticDrillBlockEntity;
import com.hechu.mindustry.world.level.block.model.PneumaticDrillModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PneumaticDrillBlockEntityRenderer extends DrillBlockEntityRenderer<PneumaticDrillBlockEntity>{
    public PneumaticDrillBlockEntityRenderer() {
        super(new PneumaticDrillModel());
    }
}
