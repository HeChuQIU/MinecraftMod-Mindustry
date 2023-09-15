package com.hechu.mindustry.world.entity.turrets;

import com.hechu.mindustry.world.entity.turrets.model.DuoModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DuoRenderer extends GeoEntityRenderer<Duo> {
    public DuoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DuoModel("duo"));
    }
}
