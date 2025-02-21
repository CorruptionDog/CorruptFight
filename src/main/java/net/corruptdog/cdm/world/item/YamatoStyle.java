package net.corruptdog.cdm.world.item;

import yesman.epicfight.world.capabilities.item.Style;

public enum YamatoStyle implements Style {
    YAMATO(false);

    final boolean canUseOffhand;
    final int id;

    YamatoStyle(boolean canUseOffhand) {
        this.id = Style.ENUM_MANAGER.assign(this);
        this.canUseOffhand = canUseOffhand;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }

    public boolean canUseOffhand() {
        return this.canUseOffhand;
    }
}
