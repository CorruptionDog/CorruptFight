package net.corruptdog.cdm.network.server;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.function.Supplier;

public class SPLockOn {

    public SPLockOn() {
    }

    public static SPLockOn fromBytes(FriendlyByteBuf buf) {
        return new SPLockOn();
    }

    public static void toBytes(SPLockOn msg, FriendlyByteBuf buf) {
    }

    public static void handle(SPLockOn msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(Minecraft.getInstance().player != null){
                Minecraft.getInstance().player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent((entityPatch -> {
                    if(entityPatch instanceof LocalPlayerPatch localPlayerPatch){
                        localPlayerPatch.setLockOn(true);
                        localPlayerPatch.toggleLockOn();
                    }
                }));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}