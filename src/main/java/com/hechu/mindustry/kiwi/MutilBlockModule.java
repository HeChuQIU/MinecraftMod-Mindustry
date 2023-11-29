package com.hechu.mindustry.kiwi;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.world.level.block.HealthTestBlock;
import com.hechu.mindustry.world.level.block.MechanicalDrillBlock;
import com.hechu.mindustry.world.level.block.PneumaticDrillBlock;
import com.hechu.mindustry.world.level.block.multiblock.KilnBlock;
import com.hechu.mindustry.world.level.block.multiblock.MultiblockCoreBlock;
import com.hechu.mindustry.world.level.block.multiblock.TestMultiblockCoreBlock;
import com.hechu.mindustry.world.level.block.multiblock.TestTurretMultiblockEntityBlock;
import com.hechu.mindustry.world.level.block.turrets.SpectreTurretBlock;
import com.hechu.mindustry.world.level.block.turrets.SwarmerTurretBlock;
import net.minecraft.world.level.block.Block;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hechu.mindustry.MindustryConstants.logger;

@KiwiModule(value = "mutilblock")
@KiwiModule.Category(value = MindustryConstants.MOD_ID + ":tab_main")
public class MutilBlockModule extends AbstractModule {
    public static final KiwiGO<Block> MECHANICAL_DRILL = go(MechanicalDrillBlock::new);
    public static final KiwiGO<Block> PNEUMATIC_DRILL = go(PneumaticDrillBlock::new);
    public static final KiwiGO<Block> HEALTH_TEST = go(HealthTestBlock::new);
    public static final KiwiGO<Block> SWARMER_TURRET = go(SwarmerTurretBlock::new);
    public static final KiwiGO<Block> SPECTRE_TURRET = go(SpectreTurretBlock::new);
    public static final KiwiGO<Block> TEST_MULTIBLOCK_CORE = go(TestMultiblockCoreBlock::new);
    public static final KiwiGO<Block> TEST_TURRET_MULTIBLOCK_ENTITY_BLOCK = go(TestTurretMultiblockEntityBlock::new);
    public static final KiwiGO<Block> KILN_BLOCK = go(KilnBlock::new);
    private static Set<MultiblockCoreBlock> blocks = null;
    private static Set<String> registerName = null;

    public static Set<MultiblockCoreBlock> getBlocks() {
        if (blocks == null) {
            blocks = Arrays.stream(MutilBlockModule.class.getFields())
                    .filter(field -> {
                        int modifier = field.getModifiers();
                        return Modifier.isPublic(modifier) && Modifier.isFinal(modifier) && Modifier.isStatic(modifier);
                    })
                    .map(field -> {
                        try {
                            return field.get(null);
                        } catch (IllegalAccessException e) {
                            logger.error("Error to get field", e);
                            return null;
                        }
                    })
                    .filter(field -> field instanceof KiwiGO<?>)
                    .map(field -> ((KiwiGO<?>) field).get())
                    .filter(field -> field instanceof MultiblockCoreBlock)
                    .map(field -> (MultiblockCoreBlock) field)
                    .collect(Collectors.toSet());
        }
        return blocks;
    }

    public static Set<String> getRegisterName() {
        if (registerName == null) {
            registerName = Arrays.stream(MutilBlockModule.class.getFields())
                    .filter(field -> {
                        int modifier = field.getModifiers();
                        return Modifier.isPublic(modifier) && Modifier.isFinal(modifier) && Modifier.isStatic(modifier);
                    })
                    .map(field -> {
                        KiwiModule.Name annotated = field.getAnnotation(KiwiModule.Name.class);
                        if (annotated == null){
                            return field.getName();
                        }
                        return annotated.value();
                    })
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
        }
        return registerName;
    }
}
