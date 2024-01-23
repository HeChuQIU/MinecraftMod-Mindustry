package com.hechu.mindustry.client.renderer.blockentity;

import com.hechu.mindustry.world.level.block.entity.distribution.ConveyorBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ConveyorBlockEntityRenderer implements BlockEntityRenderer<ConveyorBlockEntity> {
    @Override
    public void render(@NotNull ConveyorBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < ConveyorBlockEntity.MAX_ITEMS; i++) {
            ItemStack stack = blockEntity.getItemHandler().getStackInSlot(i);
            items.add(stack);
        }

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        for (int i = 0; i < ConveyorBlockEntity.MAX_ITEMS; i++) {
            poseStack.pushPose();
            int c = ConveyorBlockEntity.MAX_ITEMS / 2 + 1;
            float offset = (i - c) * 1F / ConveyorBlockEntity.MAX_ITEMS;
            var diff = blockEntity.getBlockPos().getCenter().subtract(Vec3.atLowerCornerOf(blockEntity.getBlockPos()));
            poseStack.translate(diff.x, diff.y, diff.z);
            poseStack.translate(0f, 0f, offset);
//            poseStack.scale(0.5f, 0.5f, 0.5f);
            itemRenderer.renderStatic(items.get(i), ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, buffer, Minecraft.getInstance().level, 0);
            poseStack.popPose();
        }
    }
}
