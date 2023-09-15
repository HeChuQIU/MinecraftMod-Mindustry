package com.hechu.mindustry.client.renderer.item;

import com.hechu.mindustry.world.item.PneumaticDrill;
import com.hechu.mindustry.world.item.model.PneumaticDrillModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PneumaticDrillRenderer extends DrillRenderer<PneumaticDrill>{
    public PneumaticDrillRenderer() {
        super(new PneumaticDrillModel());
    }
}
