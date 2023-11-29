package com.hechu.mindustry.client.renderer.blockentity;

import com.hechu.mindustry.world.level.block.entity.turrets.SwarmerTurretBlockEntity;
import com.hechu.mindustry.world.level.block.entity.turrets.TurretBlockEntityBase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TurretRenderer implements BlockEntityRenderer<TurretBlockEntityBase> {
    public static final Material TURRET_LOCATION = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/turret"));

    public TurretRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(@NotNull TurretBlockEntityBase blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();

//        poseStack.pushPose();
//        BlockState blockState1 = Blocks.DISPENSER.defaultBlockState();
//        blockRenderer.renderSingleBlock(blockState1, poseStack, buffer, packedLight, packedOverlay, ModelData.EMPTY, RenderType.solid());
//        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.0F, 0.5F);
        float f = (float) blockEntity.time + partialTick;
        poseStack.translate(0.0F, 0.1F + Mth.sin(f * 0.1F) * 0.1F, 0.0F);

        float f1;
        f1 = blockEntity.rot - blockEntity.oRot;
        while (f1 >= (float) Math.PI) {
            f1 -= ((float) Math.PI * 2F);
        }

        while (f1 < -(float) Math.PI) {
            f1 += ((float) Math.PI * 2F);
        }

        float f2 = blockEntity.oRot + f1 * partialTick;
        float f3;
        if (blockEntity.target != null) {
            f3 = (float) Mth.atan2(blockEntity.targetPos.y - blockEntity.getBlockPos().getCenter().y,
                    Mth.sqrt((float) (Mth.square(blockEntity.targetPos.x - blockEntity.getBlockPos().getCenter().x)
                            + Mth.square(blockEntity.targetPos.z - blockEntity.getBlockPos().getCenter().z))));
//            poseStack.mulPose(Axis.XN.rotationDegrees(90.0f));
        } else {
            f3 = 0f;
        }

        poseStack.mulPose(Axis.YN.rotationDegrees(90.0f));
        poseStack.mulPose(Axis.YN.rotation(f2));

        poseStack.mulPose(Axis.XP.rotation(f3));

//        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
//        ItemStack stack = new ItemStack(Items.ARROW);
//        BakedModel bakedModel = itemRenderer.getItemModelShaper().getItemModel(stack);
//        itemRenderer.render(stack, ItemDisplayContext.FIXED,false,poseStack,buffer,packedLight,packedOverlay,bakedModel);


        poseStack.translate(-0.25F, 0, -0.25F);
        poseStack.scale(0.5f, 0.5f, 0.5f);


//        BlockState blockState = BlockRegister.HEALTH_TEST.get().defaultBlockState();
        BlockState blockState2 = Blocks.DISPENSER.defaultBlockState();
        blockRenderer.renderSingleBlock(blockState2, poseStack, buffer, 256, packedOverlay, ModelData.EMPTY, RenderType.solid());
        poseStack.popPose();
    }
}
