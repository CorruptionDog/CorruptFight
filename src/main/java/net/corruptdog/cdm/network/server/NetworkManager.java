package net.corruptdog.cdm.network.server;

import net.corruptdog.cdm.main.CDmoveset;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.PacketTarget;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkManager {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(CDmoveset.MOD_ID, "network_manager"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToClient(MSG message, PacketTarget packetTarget) {
        INSTANCE.send(packetTarget, message);
    }


    public static <MSG> void sendToAllPlayerTrackingThisEntityWithSelf(MSG message, ServerPlayer entity) {
        sendToClient(message, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity));
    }

    public static void registerPackets() {
        int id = 0;

        INSTANCE.registerMessage(id++, SPAfterImagine.class, SPAfterImagine::toBytes, SPAfterImagine::fromBytes, SPAfterImagine::handle);
        INSTANCE.registerMessage(id++, SPLockOn.class, SPLockOn::toBytes, SPLockOn::fromBytes, SPLockOn::handle);
    }
}