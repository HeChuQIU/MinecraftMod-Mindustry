package com.hechu.mindustry.client.renderer.blockentity;

import com.hechu.mindustry.world.level.block.DrillBlock;
import com.hechu.mindustry.world.level.block.entity.DrillBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import static com.hechu.mindustry.world.level.block.DrillBlock.PART;

@OnlyIn(Dist.CLIENT)
public abstract class DrillBlockEntityRenderer<T extends DrillBlockEntity & GeoAnimatable> extends GeoBlockRenderer<T> {
    public DrillBlockEntityRenderer(GeoModel<T> model) {
        super(model);
    }

    @Override
    public boolean shouldRender(T blockEntity, @NotNull Vec3 p_173569_) {
        if (DrillBlock.DrillPart.MASTER.equals(blockEntity.getBlockState().getValue(PART)))
            return super.shouldRender(blockEntity, p_173569_);
        else
            return false;
    }
}
