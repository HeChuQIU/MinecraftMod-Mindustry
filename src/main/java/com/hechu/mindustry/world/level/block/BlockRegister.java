package com.hechu.mindustry.world.level.block;

import com.hechu.mindustry.Mindustry;
import com.hechu.mindustry.world.level.block.multiblock.KilnBlock;
import com.hechu.mindustry.world.level.block.multiblock.TestMultiblockCoreBlock;
import com.hechu.mindustry.world.level.block.multiblock.TestTurretMultiblockEntityBlock;
import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlock;
import com.hechu.mindustry.world.level.block.turrets.TurretBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegister {
    // Create a Deferred Register to hold Blocks which will all be registered under the "mindustry" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Mindustry.MODID);
    public static final RegistryObject<Block> MECHANICAL_DRILL = BLOCKS.register(MechanicalDrillBlock.NAME, MechanicalDrillBlock::new);
    public static final RegistryObject<Block> PNEUMATIC_DRILL = BLOCKS.register(PneumaticDrillBlock.NAME, PneumaticDrillBlock::new);
    public static final RegistryObject<Block> HEALTH_TEST = BLOCKS.register(HealthTestBlock.NAME, HealthTestBlock::new);
    public static final RegistryObject<Block> TURRET = BLOCKS.register(TurretBlock.NAME, TurretBlock::new);
    public static final RegistryObject<Block> TEST_MULTIBLOCK_CORE = BLOCKS.register(TestMultiblockCoreBlock.NAME, TestMultiblockCoreBlock::new);
    public static final RegistryObject<Block> TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK = BLOCKS.register(TestTurretMultiblockEntityBlock.NAME, TestTurretMultiblockEntityBlock::new);
    public static final RegistryObject<Block> KILN_BLOCK = BLOCKS.register(KilnBlock.NAME, KilnBlock::new);

    public static final RegistryObject<Block> POWER_NODE = BLOCKS.register(PowerNodeBlock.NAME, PowerNodeBlock::new);
}
