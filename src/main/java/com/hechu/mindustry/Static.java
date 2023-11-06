package com.hechu.mindustry;

import com.hechu.mindustry.config.CommonConfig;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.nio.file.Path;

public class Static {
    public static final Logger logger = LogUtils.getLogger();
    public static final String MOD_ID = "mindustry";
    public static Path config_folder;
    public static CommonConfig commonConfig;
}
