package com.hechu.mindustry.world.entity;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.world.entity.turrets.Duo;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Mindustry.MODID);
    public static final RegistryObject<EntityType<Duo>> DUO = ENTITIES.register("duo", () ->
            EntityType.Builder.of(Duo::new, MobCategory.MISC)
                    .sized(0.5F, 0.8F)
                    .build("duo"));

}
