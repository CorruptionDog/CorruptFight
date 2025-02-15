package net.corruptdog.cdm.Client.Particles.SkillEvent;

import net.corruptdog.cdm.Client.Particles.Tyeps.Smoke;
import net.corruptdog.cdm.Client.Particles.Tyeps.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;


public class Cut {
    public static void prev(LivingEntityPatch<?> entityPatch){
        Vec3 pos = entityPatch.getOriginal().position();
        Smoke particle = new Smoke(
                Minecraft.getInstance().level, pos.x, pos.y, pos.z, 1, 5
        );

        RenderUtils.AddParticle(Minecraft.getInstance().level, particle);

    }
}
