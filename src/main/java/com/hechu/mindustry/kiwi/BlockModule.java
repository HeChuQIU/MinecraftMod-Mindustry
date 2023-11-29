package com.hechu.mindustry.kiwi;

import com.hechu.mindustry.MindustryConstants;
import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlock;
import com.hechu.mindustry.world.level.block.ore.*;
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

@KiwiModule(value = "block")
@KiwiModule.Category(value = MindustryConstants.MOD_ID + ":tab_materials")
public class BlockModule extends AbstractModule {
    @KiwiModule.Category(value = MindustryConstants.MOD_ID + ":tab_main")
    public static final KiwiGO<Block> POWER_NODE = go(PowerNodeBlock::new);
    @KiwiModule.Name("lead_ore")
    public static final KiwiGO<Block> LEAD_ORE_BLOCK = go(LeadOreBlock::new);
    @KiwiModule.Name("coal_ore")
    public static final KiwiGO<Block> COAL_ORE_BLOCK = go(CoalOreBlock::new);
    @KiwiModule.Name("copper_ore")
    public static final KiwiGO<Block> COPPER_ORE_BLOCK = go(CopperOreBlock::new);
    @KiwiModule.Name("scrap_ore")
    public static final KiwiGO<Block> SCRAP_ORE_BLOCK = go(ScrapOreBlock::new);
    @KiwiModule.Name("thorium_ore")
    public static final KiwiGO<Block> THORIUM_ORE_BLOCK = go(ThoriumOreBlock::new);
    @KiwiModule.Name("titanium_ore")
    public static final KiwiGO<Block> TITANIUM_ORE_BLOCK = go(TitaniumOreBlock::new);

    private static Set<Block> blocks = null;
    private static Set<String> registerName = null;

    public static Set<Block> getBlocks() {
        if (blocks == null) {
            blocks = Arrays.stream(BlockModule.class.getFields())
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
                    .filter(field -> field instanceof Block)
                    .map(field -> (Block) field)
                    .collect(Collectors.toSet());
        }
        return blocks;
    }

    public static Set<String> getRegisterName() {
        if (registerName == null) {
            registerName = Arrays.stream(BlockModule.class.getFields())
                    .filter(field -> {
                        int modifier = field.getModifiers();
                        return Modifier.isPublic(modifier) && Modifier.isFinal(modifier) && Modifier.isStatic(modifier);
                    })
                    .map(field -> {
                        KiwiModule.Name annotated = field.getAnnotation(KiwiModule.Name.class);
                        if (annotated == null) {
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
