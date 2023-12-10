package com.hechu.mindustry;

import com.hechu.mindustry.config.CommonConfig;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.nio.file.Path;

public class MindustryConstants {
    public static final Logger logger = LogUtils.getLogger();
    public static final String MOD_ID = "mindustry";
    public static Path config_folder;
    public static CommonConfig commonConfig;

    /**
     * 用于规范翻译文档
     */
    public static final String CHAT = "chat." + MOD_ID + ".";
    public static final String CHAT_WARN = CHAT + "warning.";
    public static final String CHAT_INFO = CHAT + "info.";
    public static final String CHAT_COMMAND = CHAT + "command.";

    public static final String DESC = "desc." + MOD_ID + ".";
    public static final String DESC_INFO = DESC + "info.";
    public static final String DESC_FLAVOUR = DESC + "flavour.";

    public static final String GUI = "gui." + MOD_ID + ".";
    public static final String GUI_CONFIG = "gui." + MOD_ID + ".config.";
}
