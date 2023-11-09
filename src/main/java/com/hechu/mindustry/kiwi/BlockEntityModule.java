package com.hechu.mindustry.kiwi;

import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlockEntity;
import com.hechu.mindustry.world.level.block.entity.HealthTestBlockEntity;
import com.hechu.mindustry.world.level.block.entity.MechanicalDrillBlockEntity;
import com.hechu.mindustry.world.level.block.entity.PneumaticDrillBlockEntity;
import com.hechu.mindustry.world.level.block.entity.multiblock.KilnBlockEntity;
import com.hechu.mindustry.world.level.block.entity.multiblock.TestTurretMultiblockEntity;
import com.hechu.mindustry.world.level.block.entity.turrets.TurretBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

@KiwiModule(value = "blockentity")
public class BlockEntityModule extends AbstractModule {
    public static final KiwiGO<BlockEntityType<MechanicalDrillBlockEntity>> MECHANICAL_DRILL_BLOCK_ENTITY = blockEntity(MechanicalDrillBlockEntity::new, null, MutilBlockModule.MECHANICAL_DRILL);
    public static final KiwiGO<BlockEntityType<PneumaticDrillBlockEntity>> PNEUMATIC_DRILL_BLOCK_ENTITY = blockEntity(PneumaticDrillBlockEntity::new, null, MutilBlockModule.PNEUMATIC_DRILL);
    public static final KiwiGO<BlockEntityType<HealthTestBlockEntity>> HEALTH_TEST_BLOCK_ENTITY = blockEntity(HealthTestBlockEntity::new, null, MutilBlockModule.HEALTH_TEST);
    public static final KiwiGO<BlockEntityType<TurretBlockEntity>> TURRET_BLOCK_ENTITY = blockEntity(TurretBlockEntity::new, null, MutilBlockModule.TURRET);
    public static final KiwiGO<BlockEntityType<TestTurretMultiblockEntity>> TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK_ENTITY = blockEntity(TestTurretMultiblockEntity::new, null, MutilBlockModule.TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK);
    public static final KiwiGO<BlockEntityType<KilnBlockEntity>> KILN_BLOCK_ENTITY = blockEntity(KilnBlockEntity::new, null, MutilBlockModule.KILN_BLOCK);
    public static final KiwiGO<BlockEntityType<PowerNodeBlockEntity>> POWER_NODE_BLOCK_ENTITY = blockEntity(PowerNodeBlockEntity::new, null, BlockModule.POWER_NODE);
}
