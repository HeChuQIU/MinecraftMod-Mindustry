package com.hechu.mindustry.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.nio.file.Path;

public class CommonConfig extends Config{
    public CommonConfig(String configName, Path configPath) {
        super(configName, configPath);
    }
    @Expose()
    @SerializedName("common")
    private Common common = new Common();
    public Common getCommon() {
        return this.common;
    }
    public static class Common {
        @Expose()
        @SerializedName("mining_speed")
        public int miningSpeed = 0;

        public int getMiningSpeed() {
            return miningSpeed;
        }

        public void setMiningSpeed(int miningSpeed) {
            this.miningSpeed = miningSpeed;
        }
    }
}