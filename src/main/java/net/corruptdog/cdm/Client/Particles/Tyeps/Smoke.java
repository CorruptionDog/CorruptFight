package net.corruptdog.cdm.Client.Particles.Tyeps;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.corruptdog.cdm.Client.Particles.ParticleRenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import java.util.Queue;

import static net.minecraft.util.Mth.nextDouble;

public class Smoke extends NoRenderParticle {
    private final Queue<Wave> waves = Queues.newConcurrentLinkedQueue();
    private int count;

    public Smoke(ClientLevel level, double x, double y, double z, int waveCount, int lifetime) {
        super(level, x, y, z);
        this.lifetime = lifetime;
        this.count = waveCount;
        waves.add(new Wave(9.f, 0.2f, 30));
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime && waves.isEmpty()) {
            this.remove();
        } else if (age < lifetime && age % 30 == 0) { // 增加粒子生成的间隔
            waves.add(new Wave(9.f, 0.2f, 40));
        }

        waves.forEach(wave -> {
            wave.tick();
            wave.pushParticle(level, x, y, z, random);
        });

        waves.removeIf(Wave::isEnd);
    }

    static class Wave {
        private float r = 0.2f;
        private float rO = 0.2f;
        private final float targetR;
        private final float smooth;
        private final int lifetime;
        private int age = 0;

        public Wave(float targetR, float smooth, int lifetime) {
            this.targetR = targetR;
            this.smooth = smooth;
            this.lifetime = lifetime;
        }

        public void tick() {
            rO = r;
            r = Mth.lerp(smooth, r, targetR);
            age++;
        }

        public boolean isEnd() {
            return age >= lifetime;
        }

        public void pushParticle(ClientLevel level, double x, double y, double z, RandomSource random) {
            int inter = Math.max(1, (int) ((r - rO) / 0.5f));
            float perR = (r - rO) / inter;
            float perYAdder = 0.5f / inter;

            for (int j = 0; j < inter; j++) {
                generateParticles(level, x, y, z, random, perR, perYAdder, j, inter);
            }
        }

        private void generateParticles(ClientLevel level, double x, double y, double z, RandomSource random, float perR, float perYAdder, int j, int inter) {
            int cnt = Math.max(1, (int) (Math.PI * (rO + perR * j) * 2 / 0.8)); // 减少每次生成的粒子数量
            double perAng = Math.PI * 2 / cnt;

            for (int i = 0; i < cnt; i++) {
                double x_ = Math.cos(perAng * i) * (rO + perR * j);
                double z_ = Math.sin(perAng * i) * (rO + perR * j);
                RenderUtils.AddParticle(level, new AirParticle(level,
                        x_ + x + nextDouble(random, -0.2, 0.2),
                        y + nextDouble(random, -0.2, 0.2) + perYAdder * j,
                        z_ + z + nextDouble(random, -0.2, 0.2),
                        nextDouble(random, -0.05, 0.05),
                        0.25f,
                        nextDouble(random, -0.05, 0.05),
                        (int) (12.f / inter * (j + 1)))); // 增加粒子的生命周期
            }
        }
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    public static class AirParticle extends Particle {
        private float alphaO;

        public AirParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, int lifetime) {
            super(level, x, y, z, xd, yd, zd);
            this.xd = xd;
            this.yd = yd;
            this.zd = zd;
            this.alphaO = alpha;
            this.hasPhysics = false;
            this.lifetime = lifetime;
            this.rCol = random.nextFloat() ;
            this.gCol = random.nextFloat() ;
            this.bCol = random.nextFloat() ;
        }

        @Override
        public void tick() {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            alphaO = alpha;

            if (this.age++ >= this.lifetime) {
                this.remove();
            } else {
                x += xd;
                y += yd;
                z += zd;
            }

            alpha = Math.max(0, Math.min(0.5f, 0.5f * (lifetime - age) / lifetime));
            setPos(x, y, z);
        }

        @Override
        public void render(VertexConsumer vertexConsumer, Camera camera, float pt) {
            float alp = Mth.lerp(pt, alphaO, alpha);
            float t_ = (age % 10 + pt) / 9.f;
            t_ = (t_ <= 0.5f) ? 4 * t_ - 1 : -4 * t_ + 3;
            float sz = (0.5f + 0.1f * t_) * alp * 1.8f;

            RenderUtils.RenderQuadFaceOnCamera2(vertexConsumer, camera,
                    (float) Mth.lerp(pt, this.xo, this.x),
                    (float) Mth.lerp(pt, this.yo, this.y),
                    (float) Mth.lerp(pt, this.zo, this.z),
                    this.rCol, this.gCol, this.bCol, alp,
                    sz
            );
        }

        static ParticleRenderTypes.AirWave renderType = ParticleRenderTypes.getRenderTypeByTexture(
                RenderUtils.GetTexture("particle/smoke"));

        @Override
        public ParticleRenderType getRenderType() {
            return renderType;
        }

        @Override
        public boolean shouldCull() {
            return false;
        }
    }
}
