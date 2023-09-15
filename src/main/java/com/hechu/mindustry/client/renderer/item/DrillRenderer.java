package com.hechu.mindustry.client.renderer.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

@OnlyIn(Dist.CLIENT)
public abstract class DrillRenderer<TItem extends Item & GeoAnimatable> extends GeoItemRenderer<TItem> {
    public DrillRenderer(GeoModel model) {
        super(model);
    }
}
