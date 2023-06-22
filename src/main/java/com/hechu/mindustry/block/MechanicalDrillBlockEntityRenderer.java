package com.hechu.mindustry.block;

import com.hechu.mindustry.model.MechanicalDrillModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class MechanicalDrillBlockEntityRenderer extends GeoBlockRenderer<MechanicalDrillBlockEntity> {

    public MechanicalDrillBlockEntityRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(rendererProvider, new MechanicalDrillModel());
    }


}
