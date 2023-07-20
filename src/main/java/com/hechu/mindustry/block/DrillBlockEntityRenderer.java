package com.hechu.mindustry.block;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import static com.hechu.mindustry.block.DrillBlock.PART;

public abstract class DrillBlockEntityRenderer<T extends DrillBlockEntity & GeoAnimatable> extends GeoBlockRenderer<T> {
    public DrillBlockEntityRenderer(GeoModel<T> model) {
        super(model);
    }

    @Override
    public boolean shouldRender(T blockEntity, @NotNull Vec3 p_173569_) {
        if(DrillBlock.DrillPart.MASTER.equals(blockEntity.getBlockState().getValue(PART)))
            return super.shouldRender(blockEntity,p_173569_);
        else
            return false;
    }
}
