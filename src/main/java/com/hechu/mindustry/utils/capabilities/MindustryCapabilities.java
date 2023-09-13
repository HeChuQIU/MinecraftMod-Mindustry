package com.hechu.mindustry.utils.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.energy.IEnergyStorage;

public class MindustryCapabilities {
    public static final Capability<IHealthHandler> HEALTH_HANDLER = CapabilityManager.get(new CapabilityToken<>(){});
}
