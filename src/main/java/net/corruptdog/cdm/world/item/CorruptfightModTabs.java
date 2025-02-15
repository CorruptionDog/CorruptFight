package net.corruptdog.cdm.world.item;

import net.corruptdog.cdm.main.CDmoveset;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CorruptfightModTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CDmoveset.MOD_ID);
    public static final RegistryObject<CreativeModeTab> CORRUPT_FIGHT = REGISTRY.register("corrupt_fight",
            () -> CreativeModeTab.builder().title(Component.translatable("item_group.cdmoveset.corrupt_fight")).icon(() -> new ItemStack(CDAddonItems.KATANA.get())).displayItems((parameters, tabData) -> {
                        tabData.accept(CDAddonItems.KATANA.get());
                        tabData.accept(CDAddonItems.YAMATO.get());
                        tabData.accept(CDAddonItems.S_IRONSPEAR.get());
                        tabData.accept(CDAddonItems.S_DIAMONDSPEAR.get());
                        tabData.accept(CDAddonItems.S_IRONTACHI.get());
                        tabData.accept(CDAddonItems.S_DIAMONDTACHI.get());
                        tabData.accept(CDAddonItems.S_NETHERITETACHI.get());
                        tabData.accept(CDAddonItems.S_IRONSWORD.get());
                        tabData.accept(CDAddonItems.S_DIAMONDSWORD.get());
                        tabData.accept(CDAddonItems.S_NETHERITESWORD.get());
                        tabData.accept(CDAddonItems.S_IRONLONGSWORD.get());
                        tabData.accept(CDAddonItems.S_DIAMONDLONGSWORD.get());
                        tabData.accept(CDAddonItems.S_NETHERITELONGSWORD.get());
                        tabData.accept(CDAddonItems.S_IRONGREATSWORD.get());
                        tabData.accept(CDAddonItems.S_DIAMONDGREATSWORD.get());
                        tabData.accept(CDAddonItems.S_NETHERITEGREATSWORD.get());
                        tabData.accept(CDAddonItems.GREAT_TACHI.get());
//                        tabData.accept(CDAddonItems.S_IRONDAGGER.get());
//                        tabData.accept(CDAddonItems.S_DIAMONDDAGGER.get());
//                        tabData.accept(CDAddonItems.S_NETHERITEDAGGER.get());
//
                    })

                    .build());
    }
