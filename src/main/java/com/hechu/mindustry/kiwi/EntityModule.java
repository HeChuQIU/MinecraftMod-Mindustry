package com.hechu.mindustry.kiwi;

import com.hechu.mindustry.world.entity.Turret;
import com.hechu.mindustry.world.entity.projectile.BasicBullet;
import com.hechu.mindustry.world.entity.projectile.MissileBullet;
import com.hechu.mindustry.world.entity.turrets.Duo;
import com.hechu.mindustry.world.entity.turrets.Honeycomb;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

@KiwiModule(value = "entity")
public class EntityModule extends AbstractModule {
    public static final KiwiGO<EntityType<Duo>> DUO = go(() ->
            EntityType.Builder.of(Duo::new, MobCategory.MISC)
                    .sized(0.5F, 0.8F)
                    .build("duo"));

    public static final KiwiGO<EntityType<MissileBullet>> MISSILE_BULLET = go(() ->
            EntityType.Builder.<MissileBullet>of(MissileBullet::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .build("missile_bullet"));
//    public static final KiwiGO<EntityType<Turret>> TURRET = go(() ->
//            EntityType.Builder.of(Turret::new, MobCategory.MISC)
//                    .sized(0.5f, 0.8f)
//                    .build("turret"));

    public static final KiwiGO<EntityType<BasicBullet>> BASIC_BULLET = go(() ->
            EntityType.Builder.<BasicBullet>of(BasicBullet::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .build("basic_bullet"));

    public static final KiwiGO<EntityType<Honeycomb>> HONEYCOMB = go(() ->
            EntityType.Builder.of(Honeycomb::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .build("honeycomb"));
}
