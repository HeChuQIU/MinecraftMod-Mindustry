package com.hechu.mindustry.client.renderer.item;

import com.hechu.mindustry.world.item.drill.PneumaticDrill;
import com.hechu.mindustry.world.item.model.MechanicalDrillModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MechanicalDrillRenderer extends DrillRenderer<PneumaticDrill> {
    public MechanicalDrillRenderer() {
        super(new MechanicalDrillModel());
    }
}
