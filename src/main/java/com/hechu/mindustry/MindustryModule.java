package com.hechu.mindustry;

import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlock;
import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlockEntity;
import com.hechu.mindustry.world.level.block.HealthTestBlock;
import com.hechu.mindustry.world.level.block.MechanicalDrillBlock;
import com.hechu.mindustry.world.level.block.PneumaticDrillBlock;
import com.hechu.mindustry.world.level.block.entity.HealthTestBlockEntity;
import com.hechu.mindustry.world.level.block.entity.MechanicalDrillBlockEntity;
import com.hechu.mindustry.world.level.block.entity.PneumaticDrillBlockEntity;
import com.hechu.mindustry.world.level.block.entity.multiblock.KilnBlockEntity;
import com.hechu.mindustry.world.level.block.entity.multiblock.TestTurretMultiblockEntity;
import com.hechu.mindustry.world.level.block.entity.turrets.TurretBlockEntity;
import com.hechu.mindustry.world.level.block.multiblock.KilnBlock;
import com.hechu.mindustry.world.level.block.multiblock.TestMultiblockCoreBlock;
import com.hechu.mindustry.world.level.block.multiblock.TestTurretMultiblockEntityBlock;
import com.hechu.mindustry.world.level.block.turrets.TurretBlock;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static com.hechu.mindustry.MindustryConstants.MOD_ID;

@KiwiModule
@KiwiModule.Category
public class MindustryModule extends AbstractModule {

    //#region Blocks
    public static final KiwiGO<Block> KILN_BLOCK = go(KilnBlock::new);
    public static final KiwiGO<Block> MECHANICAL_DRILL = go(MechanicalDrillBlock::new);
    public static final KiwiGO<Block> PNEUMATIC_DRILL = go(PneumaticDrillBlock::new);
    public static final KiwiGO<Block> HEALTH_TEST = go(HealthTestBlock::new);
    public static final KiwiGO<Block> TURRET = go(TurretBlock::new);
    public static final KiwiGO<Block> TEST_MULTIBLOCK_CORE = go(TestMultiblockCoreBlock::new);
    public static final KiwiGO<Block> TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK = go(TestTurretMultiblockEntityBlock::new);
    public static final KiwiGO<Block> POWER_NODE = go(PowerNodeBlock::new);
//#endregion

    //#region BlockEntities
    public static final KiwiGO<BlockEntityType<MechanicalDrillBlockEntity>> MECHANICAL_DRILL_BLOCK_ENTITY =
            go(() -> BlockEntityType.Builder.of(MechanicalDrillBlockEntity::new, MECHANICAL_DRILL.get()).build(null));
    public static final KiwiGO<BlockEntityType<PneumaticDrillBlockEntity>> PNEUMATIC_DRILL_BLOCK_ENTITY =
            go(() -> BlockEntityType.Builder.of(PneumaticDrillBlockEntity::new, PNEUMATIC_DRILL.get()).build(null));
    public static final KiwiGO<BlockEntityType<HealthTestBlockEntity>> HEALTH_TEST_BLOCK_ENTITY =
            go(() -> BlockEntityType.Builder.of(HealthTestBlockEntity::new, HEALTH_TEST.get()).build(null));
    public static final KiwiGO<BlockEntityType<TurretBlockEntity>> TURRET_BLOCK_ENTITY =
            go(() -> BlockEntityType.Builder.of(TurretBlockEntity::new, TURRET.get()).build(null));
    public static final KiwiGO<BlockEntityType<TestTurretMultiblockEntity>> TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK_ENTITY =
            go(() -> BlockEntityType.Builder.of(TestTurretMultiblockEntity::new, TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK.get()).build(null));
    public static final KiwiGO<BlockEntityType<PowerNodeBlockEntity>> POWER_NODE_BLOCK_ENTITY =
            go(() -> BlockEntityType.Builder.of(PowerNodeBlockEntity::new, POWER_NODE.get()).build(null));
    public static final KiwiGO<BlockEntityType<KilnBlockEntity>> KILN_BLOCK_ENTITY =
            go(() -> BlockEntityType.Builder.of(KilnBlockEntity::new, MindustryModule.KILN_BLOCK.get()).build(null));
    //#endregion

    //BlockItems 默认的创造模式物品栏
    public static final CreativeModeTab MINDUSTRY_CREATIVE_TAB = CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MOD_ID + ".mindustry"))
            .icon(() -> new ItemStack(POWER_NODE.get().asItem())).build();

    public static List<Block> blocks = null;

    public static List<Block> getAllBlocks() {
        if (blocks == null) {
            blocks = Arrays.stream(MindustryModule.class.getFields())
                    .filter(field -> {
                        int modifiers = field.getModifiers();
                        return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
                    })
                    .map(field -> {
                        try {
                            return field.get(null);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(obj -> obj instanceof KiwiGO<?>)
                    .map(obj -> (KiwiGO<?>) obj)
                    .map(KiwiGO::get)
                    .filter(obj -> obj instanceof Block)
                    .map(obj -> (Block) obj).toList();
        }
        return blocks;
    }
}
