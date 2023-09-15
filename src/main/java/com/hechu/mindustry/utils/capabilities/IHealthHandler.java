package com.hechu.mindustry.utils.capabilities;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IHealthHandler {
    float getHealth();
    void setHealth(float health);
}
