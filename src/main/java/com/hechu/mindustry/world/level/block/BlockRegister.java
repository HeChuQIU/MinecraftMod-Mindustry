package com.hechu.mindustry.world.level.block;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.world.level.block.multiblock.KilnBlock;
import com.hechu.mindustry.world.level.block.multiblock.TestMultiblockCoreBlock;
import com.hechu.mindustry.world.level.block.multiblock.TestTurretMultiblockEntityBlock;
import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlock;
import com.hechu.mindustry.world.level.block.ore.*;
import com.hechu.mindustry.world.level.block.turrets.TurretBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MindustryConstants.MOD_ID);
    public static final RegistryObject<Block> MECHANICAL_DRILL = BLOCKS.register(MechanicalDrillBlock.NAME, MechanicalDrillBlock::new);
    public static final RegistryObject<Block> PNEUMATIC_DRILL = BLOCKS.register(PneumaticDrillBlock.NAME, PneumaticDrillBlock::new);
    public static final RegistryObject<Block> HEALTH_TEST = BLOCKS.register(HealthTestBlock.NAME, HealthTestBlock::new);
    public static final RegistryObject<Block> TURRET = BLOCKS.register(TurretBlock.NAME, TurretBlock::new);
    public static final RegistryObject<Block> TEST_MULTIBLOCK_CORE = BLOCKS.register(TestMultiblockCoreBlock.NAME, TestMultiblockCoreBlock::new);
    public static final RegistryObject<Block> TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK = BLOCKS.register(TestTurretMultiblockEntityBlock.NAME, TestTurretMultiblockEntityBlock::new);
    public static final RegistryObject<Block> KILN_BLOCK = BLOCKS.register(KilnBlock.NAME, KilnBlock::new);
    public static final RegistryObject<Block> POWER_NODE = BLOCKS.register(PowerNodeBlock.NAME, PowerNodeBlock::new);
    public static final RegistryObject<Block> LEAD_ORE_BLOCK = BLOCKS.register(LeadOreBlock.NAME, LeadOreBlock::new);
    public static final RegistryObject<Block> COAL_ORE_BLOCK = BLOCKS.register(CoalOreBlock.NAME, CoalOreBlock::new);
    public static final RegistryObject<Block> COPPER_ORE_BLOCK = BLOCKS.register(CopperOreBlock.NAME, CopperOreBlock::new);
    public static final RegistryObject<Block> SCRAP_ORE_BLOCK = BLOCKS.register(ScrapOreBlock.NAME, ScrapOreBlock::new);
    public static final RegistryObject<Block> THORIUM_ORE_BLOCK = BLOCKS.register(ThoriumOreBlock.NAME, ThoriumOreBlock::new);
    public static final RegistryObject<Block> TITANIUM_ORE_BLOCK = BLOCKS.register(TitaniumOreBlock.NAME, TitaniumOreBlock::new);
}
