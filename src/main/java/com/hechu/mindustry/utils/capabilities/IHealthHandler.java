package com.hechu.mindustry.utils.capabilities;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IHealthHandler {
    int getHealth();
    void setHealth(int health);
}
