package net.corruptdog.cdm.world.damagesource;

public enum CDStunType {
    LONG2("damage_source.epicfight.stun_long", true);
    private final String tooltip;
    private final boolean fixedStunTime;

    CDStunType(String tooltip, boolean fixedStunTime) {
        this.tooltip = tooltip;
        this.fixedStunTime = fixedStunTime;
    }

    public boolean hasFixedStunTime() {
        return this.fixedStunTime;
    }

    @Override
    public String toString() {
        return this.tooltip;
    }
}