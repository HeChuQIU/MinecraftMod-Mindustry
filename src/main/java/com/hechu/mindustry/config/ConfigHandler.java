package com.hechu.mindustry.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static com.hechu.mindustry.Static.config_folder;
import static com.hechu.mindustry.Static.logger;
import static com.hechu.mindustry.Static.MOD_ID;

public class ConfigHandler {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    @Nullable
    public static <T extends Config> T readConfig(String configName, Class<T> configClass) {
        configName = "%s_%s".formatted(MOD_ID, configName);
        Path configPath = config_folder.resolve("%s.json".formatted(configName));
        if (configPath.toFile().isFile()) {
            try {
                return GSON.fromJson(FileUtils.readFileToString(configPath.toFile(), StandardCharsets.UTF_8),
                        configClass);
            } catch (IOException e) {
                logger.error("Fail to read config file.", e);
                return null;
            }
        }
        try {
            T config = configClass.getDeclaredConstructor(String.class, Path.class).newInstance(configName, configPath);
            saveConfig(config);
            return config;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            logger.error("Fail to init config class.", e);
            return null;
        }
    }

    public static <T extends Config> void saveConfig(@NotNull T config) {
        try {
            FileUtils.write(config.configPath.toFile(), GSON.toJson(config), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Fail to save config.", e);
        }
    }

    public static <T extends Config> void onChange(T config) {
        ConfigHandler.saveConfig(config);
    }
}