package net.corruptdog.cdm.Client;

import net.corruptdog.cdm.main.CDmoveset;
import net.corruptdog.cdm.world.Render.YamatoRender;
import net.corruptdog.cdm.world.item.CDAddonItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid= CDmoveset.MOD_ID, value=Dist.CLIENT, bus=EventBusSubscriber.Bus.MOD)
public class ClientModBusEvent {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void RenderRegistry(final PatchedRenderersEvent.Add event) {
        event.addItemRenderer(CDAddonItems.YAMATO.get(),new YamatoRender());
    }

    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event){
        event.enqueueWork(() -> ItemProperties.register(CDAddonItems.YAMATO.get(),new ResourceLocation(CDmoveset.MOD_ID,"unsheathed"),(Stack, World, Entity, i) -> {
            if (Stack.getTag() != null && Stack.getTag().getBoolean("unsheathed")) {
                return 1;
            }
            return 0;
        }));
    }
}
