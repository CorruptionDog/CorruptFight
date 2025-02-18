package net.corruptdog.cdm.world.item;

import net.corruptdog.cdm.main.CDmoveset;
import net.corruptdog.cdm.world.item.items.*;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.item.EpicFightCreativeTabs;

public class CDAddonItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CDmoveset.MOD_ID);
    public static final CreativeModeTab CREATIVE_MODE_TAB = new CreativeModeTab(CDmoveset.MOD_ID + ".items") {
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(CDAddonItems.KATANA.get());
        }
    };

    public static final RegistryObject<Item> KATANA = ITEMS.register("katana", () -> new Katanaitem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> KATANA_SHEATH = ITEMS.register("katana_sheath", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> YAMATO = ITEMS.register("yamato", () -> new YamatoItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> YAMATO_BLADE = ITEMS.register("yamato_blade", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));


    public static final RegistryObject<Item> A_YAMATO = ITEMS.register("a_yamato", () -> new YamatoItem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> A_YAMATO_IN_SHEATH = ITEMS.register("a_yamato_in_sheath", () -> new YamatoItem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> A_YAMATO_SHEATH = ITEMS.register("a_yamato_sheath", () -> new YamatoItem(new Item.Properties().rarity(Rarity.RARE)));


    public static final RegistryObject<Item> S_IRONSPEAR = ITEMS.register("s_ironspear", () -> new SpearItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDSPEAR = ITEMS.register("s_diamondspear", () -> new SpearItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.DIAMOND));


    public static final RegistryObject<Item> S_IRONTACHI = ITEMS.register("s_irontachi", () -> new TachiItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDTACHI = ITEMS.register("s_diamondtachi", () -> new TachiItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.DIAMOND));
    public static final RegistryObject<Item> S_NETHERITETACHI = ITEMS.register("s_netheritetachi", () -> new LongswordItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.NETHERITE));


    public static final RegistryObject<Item> S_IRONSWORD = ITEMS.register("s_ironsword", () -> new LongswordItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDSWORD = ITEMS.register("s_diamondsword", () -> new LongswordItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.DIAMOND));
    public static final RegistryObject<Item> S_NETHERITESWORD = ITEMS.register("s_netheritesword", () -> new LongswordItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.NETHERITE));

    public static final RegistryObject<Item> S_IRONLONGSWORD = ITEMS.register("s_ironlongsword", () -> new LongswordItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDLONGSWORD = ITEMS.register("s_diamondlongsword", () -> new LongswordItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.DIAMOND));
    public static final RegistryObject<Item> S_NETHERITELONGSWORD = ITEMS.register("s_netheritelongsword", () -> new LongswordItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.NETHERITE));

    public static final RegistryObject<Item> S_IRONGREATSWORD = ITEMS.register("s_irongreatsword", () -> new GreatSwordItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDGREATSWORD = ITEMS.register("s_diamondgreatsword", () -> new GreatSwordItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.DIAMOND));
    public static final RegistryObject<Item> S_NETHERITEGREATSWORD = ITEMS.register("s_netheritegreatsword", () -> new GreatSwordItem(new Item.Properties().tab(CDAddonItems.CREATIVE_MODE_TAB), Tiers.NETHERITE));

    public static final RegistryObject<Item> S_IRONDAGGER = ITEMS.register("s_irondagger", () -> new DaggerItem(new Item.Properties(), Tiers.IRON));
    public static final RegistryObject<Item> S_DIAMONDDAGGER = ITEMS.register("s_diamonddagger", () -> new DaggerItem(new Item.Properties(), Tiers.DIAMOND));
    public static final RegistryObject<Item> S_NETHERITEDAGGER = ITEMS.register("s_netheritedagger", () -> new DaggerItem(new Item.Properties(), Tiers.NETHERITE));

    public static final RegistryObject<Item> GREAT_TACHI = ITEMS.register("great_tachi", () -> new GreatTachiItem(new Item.Properties().rarity(Rarity.RARE)));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}