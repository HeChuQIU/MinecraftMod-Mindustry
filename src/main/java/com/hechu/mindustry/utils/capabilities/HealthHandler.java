package com.hechu.mindustry.utils.capabilities;

public class HealthHandler implements IHealthHandler {
    private float health = -1;
    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }
}
