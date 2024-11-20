package net.corruptdog.cdm.world.item.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import yesman.epicfight.world.item.WeaponItem;

public class TachiItem extends WeaponItem {
	public TachiItem(Item.Properties build, Tier materialIn) {
		super(materialIn, 4, -2.8F, build);
	}
}