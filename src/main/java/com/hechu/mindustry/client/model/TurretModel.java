package com.hechu.mindustry.client.model;

import com.hechu.mindustry.world.entity.Turret;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class TurretModel extends EntityModel<Turret> {
    private final ModelPart body;

    public TurretModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.body = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition up = body.addOrReplaceChild("up", CubeListBuilder.create().texOffs(0, 17).addBox(-16.0F, -5.0F, 0.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(27, 34).addBox(-12.0F, -6.0F, 2.0F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 27).addBox(-11.0F, -6.0F, 1.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 52).addBox(-9.0F, -6.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 0).addBox(-9.0F, -6.0F, 5.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition left2 = up.addOrReplaceChild("left2", CubeListBuilder.create().texOffs(47, 34).addBox(-12.0F, -6.0F, 5.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 52).addBox(-12.0F, -6.0F, 6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 47).addBox(-13.0F, -6.0F, 3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right2 = up.addOrReplaceChild("right2", CubeListBuilder.create().texOffs(0, 30).addBox(-7.0F, -6.0F, 5.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(9, 30).addBox(-6.0F, -6.0F, 6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 47).addBox(-4.0F, -6.0F, 3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition down = body.addOrReplaceChild("down", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, 0.0F, 0.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition edge = body.addOrReplaceChild("edge", CubeListBuilder.create(), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition left = edge.addOrReplaceChild("left", CubeListBuilder.create().texOffs(49, 24).addBox(-13.0F, -5.0F, 1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 47).addBox(-14.0F, -5.0F, 2.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(17, 34).addBox(-15.0F, -5.0F, 4.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(49, 17).addBox(-14.0F, -5.0F, 11.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(49, 7).addBox(-13.0F, -5.0F, 12.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(49, 0).addBox(-12.0F, -5.0F, 13.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 34).addBox(-11.0F, -5.0F, 14.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-10.0F, -5.0F, 10.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right = edge.addOrReplaceChild("right", CubeListBuilder.create().texOffs(48, 46).addBox(-4.0F, -5.0F, 1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(41, 46).addBox(-3.0F, -5.0F, 2.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-2.0F, -5.0F, 4.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(25, 47).addBox(-3.0F, -5.0F, 11.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 34).addBox(-4.0F, -5.0F, 12.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 4).addBox(-5.0F, -5.0F, 13.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-7.0F, -5.0F, 14.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -5.0F, 10.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition front = edge.addOrReplaceChild("front", CubeListBuilder.create().texOffs(32, 46).addBox(-9.0F, -5.0F, 8.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition behind = edge.addOrReplaceChild("behind", CubeListBuilder.create().texOffs(34, 39).addBox(-12.0F, -5.0F, 0.0F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    @Override
    public void renderToBuffer(@NotNull PoseStack pPoseStack, @NotNull VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    public void render(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.body.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    @Override
    public void setupAnim(Turret pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }
}
