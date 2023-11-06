package com.hechu.mindustry.world.level.block.entity;

import com.hechu.mindustry.Static;
import com.hechu.mindustry.world.level.block.BlockRegister;
import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlockEntity;
import com.hechu.mindustry.world.level.block.entity.turrets.TurretBlockEntity;
import com.hechu.mindustry.world.level.block.entity.multiblock.KilnBlockEntity;
import com.hechu.mindustry.world.level.block.entity.multiblock.TestTurretMultiblockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Static.MOD_ID);
    public static final RegistryObject<BlockEntityType<MechanicalDrillBlockEntity>> MECHANICAL_DRILL_BLOCK_ENTITY = BLOCK_ENTITIES.register(MechanicalDrillBlockEntity.NAME, () -> BlockEntityType.Builder.of(MechanicalDrillBlockEntity::new, BlockRegister.MECHANICAL_DRILL.get()).build(null));
    public static final RegistryObject<BlockEntityType<PneumaticDrillBlockEntity>> PNEUMATIC_DRILL_BLOCK_ENTITY = BLOCK_ENTITIES.register(PneumaticDrillBlockEntity.NAME, () -> BlockEntityType.Builder.of(PneumaticDrillBlockEntity::new, BlockRegister.PNEUMATIC_DRILL.get()).build(null));
    public static final RegistryObject<BlockEntityType<HealthTestBlockEntity>> HEALTH_TEST_BLOCK_ENTITY = BLOCK_ENTITIES.register(HealthTestBlockEntity.NAME, () -> BlockEntityType.Builder.of(HealthTestBlockEntity::new, BlockRegister.HEALTH_TEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurretBlockEntity>> TURRET_BLOCK_ENTITY = BLOCK_ENTITIES.register(TurretBlockEntity.NAME, () -> BlockEntityType.Builder.of(TurretBlockEntity::new, BlockRegister.TURRET.get()).build(null));
    public static final RegistryObject<BlockEntityType<TestTurretMultiblockEntity>> TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK_ENTITY = BLOCK_ENTITIES.register(TestTurretMultiblockEntity.NAME, () -> BlockEntityType.Builder.of(TestTurretMultiblockEntity::new, BlockRegister.TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<KilnBlockEntity>> KILN_BLOCK_ENTITY = BLOCK_ENTITIES.register(KilnBlockEntity.NAME, () -> BlockEntityType.Builder.of(KilnBlockEntity::new, BlockRegister.KILN_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<PowerNodeBlockEntity>> POWER_NODE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register(PowerNodeBlockEntity.NAME,
                    () -> BlockEntityType.Builder.of(PowerNodeBlockEntity::new, BlockRegister.POWER_NODE.get()).build(null));
}
