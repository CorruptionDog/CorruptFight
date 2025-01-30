package net.corruptdog.cdm.main;

import com.mojang.logging.LogUtils;
import net.corruptdog.cdm.CDConfig;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.gameasset.CorruptSound;
import net.corruptdog.cdm.network.server.NetworkManager;
import net.corruptdog.cdm.particle.CorruptParticles;
import net.corruptdog.cdm.skill.CDSkillDataKeys;
import net.corruptdog.cdm.world.CorruptWeaponCategories;
import net.corruptdog.cdm.world.Render.A_YamatoRender;
import net.corruptdog.cdm.world.Render.KtanaSheathRenderer;
import net.corruptdog.cdm.world.Render.YamatoRender;
import net.corruptdog.cdm.world.item.CDAddonItems;
import net.corruptdog.cdm.world.item.CorruptfightModTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.property.JointMaskReloadListener;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.client.forgeevent.WeaponCategoryIconRegisterEvent;
import yesman.epicfight.api.client.model.ItemSkins;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import static net.corruptdog.cdm.world.CDWeaponCapabilityPresets.*;

@Mod(CDmoveset.MOD_ID)
public class CDmoveset
{
    public static final String MOD_ID = "cdmoveset";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static float PERCENT = 20F;
    public static double millisF = 0F;
    public static long millis = 0;
    public static Timer timer;
    public static ScheduledExecutorService service;

    public CDmoveset() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        WeaponCategory.ENUM_MANAGER.registerEnumCls(MOD_ID, CorruptWeaponCategories.class);
        CDAddonItems.ITEMS.register(bus);
        CorruptSound.SOUNDS.register(bus);
        CDSkillDataKeys.DATA_KEYS.register(bus);
        bus.addListener(CDmoveset::RegisterWeaponType);
        bus.addListener(CorruptAnimations::registerAnimations);
        bus.addListener(CDmoveset::buildSkillEvent);
        bus.addListener(this::doCommonStuff);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {bus.addListener(CDmoveset::regIcon);});
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {bus.addListener(CDmoveset::registerRenderer);});
        bus.addListener(CDmoveset::addPackFindersEvent);
        CorruptfightModTabs.REGISTRY.register(bus);
        CorruptParticles.PARTICLES.register(bus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CDConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CDConfig.CLIENT_SPEC);
        create();
    }

    @OnlyIn(Dist.CLIENT)
    public static void regIcon(WeaponCategoryIconRegisterEvent event){
        event.registerCategory(CorruptWeaponCategories.YAMATO,new ItemStack(CDAddonItems.YAMATO.get()));
        event.registerCategory(CorruptWeaponCategories.S_SWORD,new ItemStack(CDAddonItems.S_IRONSWORD.get()));
        event.registerCategory(CorruptWeaponCategories.S_GREATSWORD,new ItemStack(CDAddonItems.S_IRONGREATSWORD.get()));
        event.registerCategory(CorruptWeaponCategories.S_LONGSWORD,new ItemStack(CDAddonItems.S_IRONLONGSWORD.get()));
        event.registerCategory(CorruptWeaponCategories.S_TACHI,new ItemStack(CDAddonItems.S_IRONTACHI.get()));
        event.registerCategory(CorruptWeaponCategories.S_SPEAR,new ItemStack(CDAddonItems.S_IRONSPEAR.get()));
//        event.registerCategory(CorruptWeaponCategories.DUAL_TACHI,new ItemStack(CDAddonItems.DUAL_TACHI.get()));
    }

    private void doCommonStuff(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkManager::registerPackets);
    }
    public static void create() {
        if (service == null)
            service = Executors.newSingleThreadScheduledExecutor();

        if (timer == null)
            timer = new Timer();
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    service.scheduleAtFixedRate(CDmoveset::update, 1L, 1L, TimeUnit.MILLISECONDS);
                }
            }, 1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void update() {
        float p = PERCENT / 20.0F;
        millisF = p + millisF;
        millis = (long) millisF;
    }

    public static void changeAll(float percent) {
        PERCENT = percent;
    }


    @OnlyIn(Dist.CLIENT)
    public static void registerRenderer(PatchedRenderersEvent.Add event){
        event.addItemRenderer(CDAddonItems.KATANA.get(), new KtanaSheathRenderer());
        event.addItemRenderer(CDAddonItems.YAMATO.get(), new YamatoRender());
        event.addItemRenderer(CDAddonItems.A_YAMATO.get(), new A_YamatoRender());
    }

    public static void addPackFindersEvent(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(CDmoveset.MOD_ID).getFile().findResource("packs/corrupt_animation");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(CDmoveset.MOD_ID).getFile().getFileName() + ":" + resourcePath, resourcePath, false);
            Pack.ResourcesSupplier resourcesSupplier = (string) -> pack;
            Pack.Info info = Pack.readPackInfo("corrupt_animation", resourcesSupplier);

            if (info != null) {
                event.addRepositorySource((source) ->
                        source.accept(Pack.create("corrupt_animation", Component.translatable("pack.corrupt_animation.title"), false, resourcesSupplier, info, PackType.CLIENT_RESOURCES, Pack.Position.TOP, false, PackSource.BUILT_IN)));
            }
        }
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(CDmoveset.MOD_ID).getFile().findResource("packs/power");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(CDmoveset.MOD_ID).getFile().getFileName() + ":" + resourcePath, resourcePath, false);
            Pack.ResourcesSupplier resourcesSupplier = (string) -> pack;
            Pack.Info info = Pack.readPackInfo("More_Power By zhong004", resourcesSupplier);

            if (info != null) {
                event.addRepositorySource((source) ->
                        source.accept(Pack.create("More_Power", Component.translatable("pack.More_Power.title"), false, resourcesSupplier, info, PackType.CLIENT_RESOURCES, Pack.Position.TOP, false, PackSource.BUILT_IN)));
            }
        }
    }


    @SubscribeEvent
    public static void registerResourcepackReloadListnerEvent(final RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new JointMaskReloadListener());
        event.registerReloadListener(Meshes.INSTANCE);
        event.registerReloadListener(AnimationManager.getInstance());
        event.registerReloadListener(ItemSkins.INSTANCE);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
    public static void RegisterWeaponType(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(CDmoveset.MOD_ID,"katana"), KATANA);
        event.getTypeEntry().put(new ResourceLocation(CDmoveset.MOD_ID,"s_greatsword"), S_GREATSWORD);
        event.getTypeEntry().put(new ResourceLocation(CDmoveset.MOD_ID,"s_tachi"), S_TACHI);
        event.getTypeEntry().put(new ResourceLocation(CDmoveset.MOD_ID,"yamato"), YAMATO);
        event.getTypeEntry().put(new ResourceLocation(CDmoveset.MOD_ID,"s_spear"), S_SPEAR);
        event.getTypeEntry().put(new ResourceLocation(CDmoveset.MOD_ID,"s_sword"), S_SWORD);
        event.getTypeEntry().put(new ResourceLocation(CDmoveset.MOD_ID,"s_longsword"), S_LONGSWORD);
        event.getTypeEntry().put(new ResourceLocation(CDmoveset.MOD_ID,"s_dagger"), S_DAGGER);
        event.getTypeEntry().put(new ResourceLocation(CDmoveset.MOD_ID,"dual_tachi"), DUAL_TACHI);
    }




    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
    public static boolean regGuarded=false;
    public static void buildSkillEvent(RegisterEvent event){
        //if(!event.getRegistryKey().equals(SkillManager.SKILL_REGISTRY_KEY)){return;}
        if (EpicFightSkills.GUARD==null){return;}
        if (regGuarded){return;}
        try {
            regGuard();
        }catch (Exception e){
            e.printStackTrace();
        }
        regGuarded=true;
    }
    public static void regGuard() throws NoSuchFieldException, IllegalAccessException {
        LOGGER.info("buildSkillEvent");
        Map<WeaponCategory, BiFunction<CapabilityItem,PlayerPatch<?>,?>> impactGuardMotions=new HashMap<>();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardMotions=new HashMap<>();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardBreakMotions=new HashMap<>();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> advancedGuardMotions=new HashMap<>();

        guardMotions.put(CapabilityItem.WeaponCategories.AXE,
                (item, player) -> Animations.SWORD_GUARD_HIT);
        guardBreakMotions.put(CapabilityItem.WeaponCategories.AXE,
                (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(CapabilityItem.WeaponCategories.AXE, (itemCap, playerpatch) ->
                new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 });

        guardMotions.put(CorruptWeaponCategories.YAMATO,
                (item, player) -> CorruptAnimations.YAMATO_GUARD_HIT);
        guardBreakMotions.put(CorruptWeaponCategories.YAMATO,
                (item, player) -> CorruptAnimations.GUARD_BREAK1);
        advancedGuardMotions.put(CorruptWeaponCategories.YAMATO, (itemCap, playerpatch) ->
                new StaticAnimation[]{CorruptAnimations.YAMATO_ACTIVE_GUARD_HIT, CorruptAnimations.YAMATO_ACTIVE_GUARD_HIT2});

        guardMotions.put(CorruptWeaponCategories.S_SWORD,
                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? Animations.SWORD_GUARD_HIT : Animations.SWORD_DUAL_GUARD_HIT);
        guardBreakMotions.put(CorruptWeaponCategories.S_SWORD,
                (item, player) -> CorruptAnimations.GUARD_BREAK1);
        advancedGuardMotions.put(CorruptWeaponCategories.S_SWORD, (itemCap, playerpatch) -> itemCap.getStyle(playerpatch) == CapabilityItem.Styles.ONE_HAND ?
                new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 } :
                new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 });

        guardMotions.put(CorruptWeaponCategories.S_GREATSWORD,
                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? Animations.GREATSWORD_GUARD_HIT : Animations.SWORD_DUAL_GUARD_HIT);
        guardBreakMotions.put(CorruptWeaponCategories.S_GREATSWORD,
                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? CorruptAnimations.GUARD_BREAK2 : CorruptAnimations.GUARD_BREAK1);
        advancedGuardMotions.put(CorruptWeaponCategories.S_GREATSWORD, (itemCap, playerpatch) -> itemCap.getStyle(playerpatch) == CapabilityItem.Styles.ONE_HAND ?
                new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 } :
                new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 });
        impactGuardMotions.put(CorruptWeaponCategories.S_GREATSWORD, (item, player) ->
                item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? Animations.GREATSWORD_GUARD_HIT : Animations.SWORD_DUAL_GUARD_HIT);

//        guardMotions.put(CorruptWeaponCategories.DUAL_TACHI,
//                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? CorruptAnimations.TACHI_GUARD_HIT : Animations.SWORD_DUAL_GUARD_HIT);
//        guardBreakMotions.put(CorruptWeaponCategories.DUAL_TACHI,
//                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? CorruptAnimations.GUARD_BREAK2 : CorruptAnimations.GUARD_BREAK1);
//        advancedGuardMotions.put(CorruptWeaponCategories.DUAL_TACHI, (itemCap, playerpatch) -> itemCap.getStyle(playerpatch) == CapabilityItem.Styles.ONE_HAND ?
//                new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 } :
//                new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 });

        guardMotions.put(CorruptWeaponCategories.S_SPEAR,
                (item, player) -> Animations.SPEAR_GUARD_HIT);
        guardBreakMotions.put(CorruptWeaponCategories.S_SPEAR,
                (item, player) -> CorruptAnimations.GUARD_BREAK2);
        advancedGuardMotions.put(CorruptWeaponCategories.S_SPEAR, (itemCap, playerpatch) ->
                new StaticAnimation[]{Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2});
        impactGuardMotions.put(CorruptWeaponCategories.S_SPEAR, (item, player) -> Animations.SPEAR_GUARD_HIT);

        guardMotions.put(CorruptWeaponCategories.S_TACHI,
                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? Animations.LONGSWORD_GUARD_HIT : Animations.SWORD_DUAL_GUARD_HIT);
        guardBreakMotions.put(CorruptWeaponCategories.S_TACHI,
                (item, player) -> item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? CorruptAnimations.GUARD_BREAK2 : CorruptAnimations.GUARD_BREAK1);
        advancedGuardMotions.put(CorruptWeaponCategories.S_TACHI, (itemCap, playerpatch) -> itemCap.getStyle(playerpatch) == CapabilityItem.Styles.ONE_HAND ?
                new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 } :
                new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 });
        impactGuardMotions.put(CorruptWeaponCategories.S_TACHI, (item, player) -> Animations.LONGSWORD_GUARD);

        guardMotions.put(CorruptWeaponCategories.S_LONGSWORD,
                (item, player) -> Animations.LONGSWORD_GUARD_HIT);
        guardBreakMotions.put(CorruptWeaponCategories.S_LONGSWORD,
                (item, player) -> CorruptAnimations.GUARD_BREAK2);
        advancedGuardMotions.put(CorruptWeaponCategories.S_LONGSWORD, (itemCap, playerpatch) ->
                new StaticAnimation[]{Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2});
        impactGuardMotions.put(CorruptWeaponCategories.S_LONGSWORD, (item, player) -> Animations.LONGSWORD_GUARD_HIT);



        Field temp;
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> target;
        temp=GuardSkill.class.getDeclaredField("guardMotions");
        temp.setAccessible(true);
        target= (Map) temp.get(EpicFightSkills.GUARD);
        for (WeaponCategory weaponCapability:guardMotions.keySet()){
            target.put(weaponCapability,guardMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.PARRYING);
        for (WeaponCategory weaponCapability:guardMotions.keySet()){
            target.put(weaponCapability,guardMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.IMPACT_GUARD);
        for (WeaponCategory weaponCapability:guardMotions.keySet()){
            target.put(weaponCapability,guardMotions.get(weaponCapability));
        }


        temp=GuardSkill.class.getDeclaredField("guardBreakMotions");
        temp.setAccessible(true);
        target= (Map) temp.get(EpicFightSkills.GUARD);
        for (WeaponCategory weaponCapability:guardBreakMotions.keySet()){
            target.put(weaponCapability,guardBreakMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.PARRYING);
        for (WeaponCategory weaponCapability:guardBreakMotions.keySet()){
            target.put(weaponCapability,guardBreakMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.IMPACT_GUARD);
        for (WeaponCategory weaponCapability:guardBreakMotions.keySet()){
            target.put(weaponCapability,guardBreakMotions.get(weaponCapability));
        }


        temp=GuardSkill.class.getDeclaredField("advancedGuardMotions");
        temp.setAccessible(true);
        target=(Map) temp.get(EpicFightSkills.PARRYING);
        for (WeaponCategory weaponCapability:advancedGuardMotions.keySet()){
            target.put(weaponCapability,advancedGuardMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.IMPACT_GUARD);
        for(WeaponCategory weaponCapability:impactGuardMotions.keySet()){
            target.put(weaponCapability,impactGuardMotions.get(weaponCapability));
        }
    }
}
