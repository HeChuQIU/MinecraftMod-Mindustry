package com.hechu.mindustry.world.item.drill;

import com.hechu.mindustry.utils.Utils;
import com.hechu.mindustry.world.level.block.DrillBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Drill extends BlockItem implements GeoItem {
    public Drill(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public abstract void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer);

    @Override
    public abstract void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar);

    @Override
    public abstract AnimatableInstanceCache getAnimatableInstanceCache();

    @Override
    public @NotNull InteractionResult place(BlockPlaceContext context) {
        Player player = context.getPlayer();
        if (player == null)
            return super.place(context);
        BlockPos pos = context.getClickedPos();
        Vec3i size = ((DrillBlock<?>) getBlock()).getSize();
        List<BlockPos> posList = Utils.checkPlayerFace(pos, size, player);
        for (BlockPos blockPos : posList) {
            if (!context.getLevel().getBlockState(blockPos).is(BlockTags.REPLACEABLE))
                return InteractionResult.FAIL;
        }
        return super.place(context);
    }
}
