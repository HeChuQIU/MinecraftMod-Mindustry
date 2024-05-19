package com.hechu.mindustry.world.entity.turrets.model;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.world.entity.turrets.Honeycomb;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class HoneycombModel extends EntityModel<Honeycomb> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MindustryConstants.MOD_ID, "honeycomb"), "main");
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone3;
    private final ModelPart bone5;

    public HoneycombModel() {
        ModelPart root = createBodyLayer().bakeRoot();
        root.xRot = (float) Math.PI / 2;
        this.bone = root.getChild("bone");
        this.bone2 = root.getChild("bone2");
        this.bone3 = root.getChild("bone3");
        this.bone5 = root.getChild("bone5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(33, 19).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-6.0F, -9.0F, -6.0F, 12.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(48, 0).addBox(5.0F, -10.0F, 3.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(1.0F, -10.0F, 3.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -10.0F, 3.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(-7.0F, -10.0F, 3.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-7.0F, -7.0F, 3.0F, 14.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(51, 19).addBox(-1.0F, -8.0F, 3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(50, 42).addBox(-5.0F, -8.0F, 3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(17, 37).addBox(3.0F, -8.0F, 3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(38, 42).addBox(6.0F, -10.0F, -5.0F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(18, 42).addBox(-8.0F, -10.0F, -5.0F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(0, 37).addBox(-6.0F, -11.0F, -6.0F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(29, 31).addBox(2.0F, -11.0F, -6.0F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(30, 42).addBox(-2.0F, -11.0F, 1.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, 53).addBox(-2.0F, -11.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r1 = bone5.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 37).addBox(-1.4928F, -2.0F, -0.7296F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -8.0F, -5.0F, 0.0F, 2.0508F, 0.0F));

        PartDefinition cube_r2 = bone5.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 19).addBox(-0.8045F, -2.0F, -1.6463F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -8.0F, -5.0F, 0.0F, 2.7053F, 0.0F));

        PartDefinition cube_r3 = bone5.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(30, 53).addBox(-0.031F, -1.0F, -1.0984F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -10.0F, -3.0F, 0.0F, 0.6109F, 0.0F));

        PartDefinition cube_r4 = bone5.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(40, 53).addBox(-2.0F, -1.0F, -7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(55, 37).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -10.0F, 1.0F, 0.0F, -0.7418F, 0.0F));

        PartDefinition cube_r5 = bone5.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(50, 53).addBox(-1.031F, -1.0F, -1.0984F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -10.0F, 2.0F, 0.0F, 0.6109F, 0.0F));

        PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(0, 48).addBox(-5.0F, -3.0F, 1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(46, 28).addBox(-11.0F, -3.0F, 1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -9.0F, -6.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(Honeycomb honeycomb, float v, float v1, float v2, float v3, float v4) {

    }
}