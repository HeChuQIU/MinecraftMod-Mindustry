package com.hechu.mindustry.world.entity.turrets;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.kiwi.EntityModule;
import com.hechu.mindustry.world.entity.turrets.model.HoneycombModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class HoneycombRenderer extends EntityRenderer<Honeycomb> {
    private final EntityModel<Honeycomb> model;
    public static final ResourceLocation TEXTURE = new ResourceLocation(MindustryConstants.MOD_ID, "textures/entity/turrets/honeycomb.png");
    public HoneycombRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new HoneycombModel();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Honeycomb honeycomb) {
        return TEXTURE;
    }

    @Override
    public void render(Honeycomb pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.pushPose();
        pPoseStack.rotateAround(new Quaternionf().rotateX((float)Math.PI), 0, 0.75f, 0);
        VertexConsumer ivertexbuilder = pBuffer.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
        this.model.renderToBuffer(pPoseStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }
}
