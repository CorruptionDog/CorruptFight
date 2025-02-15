package net.corruptdog.cdm.world;

import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.function.Function;

public enum CorruptWeaponCategories implements WeaponCategory, Function<Item, CapabilityItem.Builder> {
KATANA,S_GREATSWORD,YAMATO,S_SWORD,S_LONGSWORD,S_TACHI,S_SPEAR,GREAT_TACHI,S_DAGGER,SANJI;

    final int id;

    CorruptWeaponCategories() {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }

    @Override
    public CapabilityItem.Builder apply(Item item) {
        return WeaponCategoryMapper.apply(item, this);
    }

    public static class Builder {
    }
}
