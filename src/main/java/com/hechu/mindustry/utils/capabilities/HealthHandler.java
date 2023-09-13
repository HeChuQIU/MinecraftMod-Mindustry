package com.hechu.mindustry.utils.capabilities;

public class HealthHandler implements IHealthHandler {
    private int health = -1;
    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}
