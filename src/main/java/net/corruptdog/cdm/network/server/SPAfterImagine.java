package net.corruptdog.cdm.network.server;

import java.util.function.Supplier;

import net.corruptdog.cdm.particle.CorruptParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import yesman.epicfight.particle.EpicFightParticles;

public class SPAfterImagine {
    private int entityId;

    private Vec3 location;

    public SPAfterImagine(Vec3 location, int entityId) {
        this.location = location;
        this.entityId = entityId;
    }

    public static SPAfterImagine fromBytes(FriendlyByteBuf buf) {
        return new SPAfterImagine(new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble()), buf.readInt());
    }

    public static void toBytes(SPAfterImagine msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.location.x);
        buf.writeDouble(msg.location.y);
        buf.writeDouble(msg.location.z);
        buf.writeInt(msg.entityId);
    }

    public static void handle(SPAfterImagine msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null)
                mc.level.addParticle(CorruptParticles.CORRUPT_AFTER_IMAGE.get(), msg.location.x, msg.location.y, msg.location.z, Double.longBitsToDouble(msg.entityId), 0.0D, 0.0D);
        });
        ctx.get().setPacketHandled(true);
    }
}