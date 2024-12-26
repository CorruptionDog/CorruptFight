package net.corruptdog.cdm.world.item.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import yesman.epicfight.world.item.WeaponItem;

public class DaggerItem extends WeaponItem {
    public DaggerItem(Item.Properties build, Tier materialIn) {
        super(materialIn, 1, -1.6F, build);
    }
}