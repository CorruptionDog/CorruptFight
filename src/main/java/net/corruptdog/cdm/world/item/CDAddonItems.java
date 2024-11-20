package net.corruptdog.cdm.world.item;

import net.corruptdog.cdm.main.CDmoveset;
import net.corruptdog.cdm.world.item.items.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CDAddonItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CDmoveset.MOD_ID);

    public static final RegistryObject<Item> KATANA = ITEMS.register("katana", () -> new Katanaitem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> KATANA_SHEATH = ITEMS.register("katana_sheath", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> YAMATO_BLADE = ITEMS.register("yamato_blade", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> YAMATO = ITEMS.register("yamato", () -> new Yamatoitem(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> S_IRONSPEAR = ITEMS.register("s_ironspear", () -> new SpearItem(new Item.Properties(), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDSPEAR = ITEMS.register("s_diamondspear", () -> new SpearItem(new Item.Properties(), Tiers.DIAMOND));

    public static final RegistryObject<Item> S_IRONTACHI = ITEMS.register("s_irontachi", () -> new TachiItem(new Item.Properties(), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDTACHI = ITEMS.register("s_diamondtachi", () -> new TachiItem(new Item.Properties(), Tiers.DIAMOND));
    public static final RegistryObject<Item> S_NETHERITETACHI = ITEMS.register("s_netheritetachi", () -> new LongswordItem(new Item.Properties(), Tiers.NETHERITE));

    public static final RegistryObject<Item> S_IRONSWORD = ITEMS.register("s_ironsword", () -> new LongswordItem(new Item.Properties(), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDSWORD = ITEMS.register("s_diamondsword", () -> new LongswordItem(new Item.Properties(), Tiers.DIAMOND));
    public static final RegistryObject<Item> S_NETHERITESWORD = ITEMS.register("s_netheritesword", () -> new LongswordItem(new Item.Properties(), Tiers.NETHERITE));

    public static final RegistryObject<Item> S_IRONLONGSWORD = ITEMS.register("s_ironlongsword", () -> new LongswordItem(new Item.Properties(), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDLONGSWORD = ITEMS.register("s_diamondlongsword", () -> new LongswordItem(new Item.Properties(), Tiers.DIAMOND));
    public static final RegistryObject<Item> S_NETHERITELONGSWORD = ITEMS.register("s_netheritelongsword", () -> new LongswordItem(new Item.Properties(), Tiers.NETHERITE));

    public static final RegistryObject<Item> S_IRONGREATSWORD = ITEMS.register("s_irongreatsword", () -> new GreatSwordItem(new Item.Properties(), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDGREATSWORD = ITEMS.register("s_diamondgreatsword", () -> new GreatSwordItem(new Item.Properties(), Tiers.DIAMOND));
    public static final RegistryObject<Item> S_NETHERITEGREATSWORD = ITEMS.register("s_netheritegreatsword", () -> new GreatSwordItem(new Item.Properties(), Tiers.NETHERITE));

    public static final RegistryObject<Item> DUAL_TACHI = ITEMS.register("dual_tachi", () -> new TachiItem(new Item.Properties(), Tiers.NETHERITE));


     public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}