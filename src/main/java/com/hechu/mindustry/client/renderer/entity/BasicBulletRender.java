package com.hechu.mindustry.client.renderer.entity;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.client.model.MissileBulletModel;
import com.hechu.mindustry.world.entity.projectile.BasicBullet;
import com.hechu.mindustry.world.entity.projectile.MissileBullet;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BasicBulletRender extends EntityRenderer<BasicBullet> {
    private EntityModel<BasicBullet> model;

    public static final ResourceLocation TEXTURE = new ResourceLocation(MindustryConstants.MOD_ID, "textures/entity/bullets/missile_bullet.png");

    public BasicBulletRender(EntityRendererProvider.Context pContext) {
        super(pContext);
        model = new MissileBulletModel(pContext.bakeLayer(MissileBulletModel.LAYER_LOCATION));
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param pEntity
     */
    @Override
    public ResourceLocation getTextureLocation(BasicBullet pEntity) {
        return TEXTURE;
    }
}
