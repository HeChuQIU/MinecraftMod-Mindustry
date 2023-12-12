package com.hechu.mindustry;

import com.hechu.mindustry.config.CommonConfig;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.nio.file.Path;

public class MindustryConstants {
    public static final Logger logger = LogUtils.getLogger();
    public static final String MOD_ID = "mindustry";
    public static Path configFolder;
    public static CommonConfig commonConfig;

    /**
     * 用于规范翻译文档
     */
    public enum Chat {
        CHAT("chat." + MOD_ID),
        CHAT_WARN(CHAT.msg + ".warning."),
        CHAT_INFO(CHAT.msg + ".info."),
        CHAT_COMMAND(CHAT.msg + ".command.");
        public final String msg;

        Chat(String string) {
            msg = string;
        }
    }

    public enum Desc {
        DESC("desc." + MOD_ID),
        DESC_INFO(DESC.msg + ".info."),
        DESC_FLAVOUR(DESC.msg + ".flavour.");
        public final String msg;

        Desc(String string) {
            msg = string;
        }
    }

    public enum Gui {
        GUI("gui." + MOD_ID),
        GUI_CONFIG(GUI.msg + ".config.");
        public final String msg;

        Gui(String string) {
            msg = string;
        }
    }
}
