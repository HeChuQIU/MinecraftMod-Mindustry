package com.hechu.mindustry;

import com.hechu.mindustry.config.CommonConfig;
import com.hechu.mindustry.world.item.materials.*;
import com.hechu.mindustry.world.level.block.Equipment.PowerNodeBlock;
import com.hechu.mindustry.world.level.block.ore.*;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static com.hechu.mindustry.world.level.block.BlockRegister.*;

public class MindustryConstants {
    public static final Logger logger = LogUtils.getLogger();
    public static final String MOD_ID = "mindustry";
    public static Path config_folder;
    public static CommonConfig commonConfig;
}
