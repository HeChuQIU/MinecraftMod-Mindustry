package com.hechu.mindustry.config;

import java.nio.file.Path;

public abstract class Config {
    public final String configName;
    public final Path configPath;

    public Config(String configName, Path configPath) {
        this.configName = configName;
        this.configPath = configPath;
    }
}
