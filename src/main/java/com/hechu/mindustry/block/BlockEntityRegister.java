package com.hechu.mindustry.block;

import com.hechu.mindustry.Mindustry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Mindustry.MODID);
    public static final RegistryObject<BlockEntityType<MechanicalDrillBlockEntity>> MECHANICAL_DRILL_BLOCK_ENTITY = BLOCK_ENTITIES.register(MechanicalDrillBlockEntity.NAME, () -> BlockEntityType.Builder.of(MechanicalDrillBlockEntity::new, BlockRegister.MECHANICAL_DRILL.get()).build(null));
    public static final RegistryObject<BlockEntityType<PneumaticDrillBlockEntity>> PNEUMATIC_DRILL_BLOCK_ENTITY = BLOCK_ENTITIES.register(PneumaticDrillBlockEntity.NAME, () -> BlockEntityType.Builder.of(PneumaticDrillBlockEntity::new, BlockRegister.PNEUMATIC_DRILL.get()).build(null));
    public static final RegistryObject<BlockEntityType<HealthTestBlockEntity>> HEALTH_TEST_BLOCK_ENTITY = BLOCK_ENTITIES.register(HealthTestBlockEntity.NAME, () -> BlockEntityType.Builder.of(HealthTestBlockEntity::new, BlockRegister.HEALTH_TEST.get()).build(null));
}
