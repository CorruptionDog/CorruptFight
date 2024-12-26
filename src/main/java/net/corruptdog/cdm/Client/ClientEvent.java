package net.corruptdog.cdm.Client;

import net.corruptdog.cdm.Client.Particles.AfterImageParticle;
import net.corruptdog.cdm.Client.Particles.BladeRushParticle;
import net.corruptdog.cdm.main.CDmoveset;
import net.corruptdog.cdm.particle.CorruptParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.client.ClientEngine;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid= CDmoveset.MOD_ID, value=Dist.CLIENT, bus=EventBusSubscriber.Bus.MOD)
public class ClientEvent {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onParticleRegistry(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(CorruptParticles.FORESIGHT.get(), BladeRushParticle.Provider::new);
        event.registerSpecial(CorruptParticles.CORRUPT_AFTER_IMAGE.get(), new AfterImageParticle.Provider());

    }


    @SubscribeEvent
    public static void addLayersEvent(EntityRenderersEvent.AddLayers event) {
        ClientEngine.getInstance().renderEngine.bootstrap(event.getContext());
    }
}