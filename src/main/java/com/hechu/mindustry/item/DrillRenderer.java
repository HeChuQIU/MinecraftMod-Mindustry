package com.hechu.mindustry.item;

import net.minecraft.world.item.Item;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public abstract class DrillRenderer<TItem extends Item & GeoAnimatable> extends GeoItemRenderer<TItem> {
    public DrillRenderer(GeoModel model) {
        super(model);
    }
}
