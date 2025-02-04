package net.corruptdog.cdm.particle;

import net.corruptdog.cdm.main.CDmoveset;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.particle.HitParticleType;

public class CorruptParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CDmoveset.MOD_ID);

    public static final RegistryObject<SimpleParticleType> CORRUPT_AFTER_IMAGE = PARTICLES.register("corrupt_after_image", () -> new SimpleParticleType(true));
    public static final RegistryObject<HitParticleType> FORESIGHT = PARTICLES.register("foresight", () -> new HitParticleType(true, HitParticleType.RANDOM_WITHIN_BOUNDING_BOX, HitParticleType.ZERO));
      }