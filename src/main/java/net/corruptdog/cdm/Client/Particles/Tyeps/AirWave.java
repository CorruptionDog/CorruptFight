package net.corruptdog.cdm.Client.Particles.Tyeps;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;

@OnlyIn(Dist.CLIENT)
public class AirWave extends HitParticle {
    public AirWave(ClientLevel world, double x, double y, double z, SpriteSet animatedSprite) {
        super(world, x, y, z, animatedSprite);
        this.rCol = 1.0F;
        this.gCol = 0.5F;
        this.bCol = 0.5F;
        this.lifetime = 10;
        this.quadSize = 2.0F;
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha = (float) this.lifetime / 10;
        this.quadSize *= 0.95F; // 减小大小

        this.xd += (Math.random() - 0.5) * 0.1; // 随机水平移动
        this.zd += (Math.random() - 0.5) * 0.1; // 随机深度移动
        this.yd *= 0.9; // 减小垂直速度
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            AirWave particle = new AirWave(worldIn, x, y, z, this.spriteSet);
            return particle;
        }
    }
}