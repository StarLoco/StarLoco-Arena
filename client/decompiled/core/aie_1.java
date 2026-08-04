/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from aiE
 */
public class aie_1
extends ua_0 {
    private final sd cyQ = new sd();
    private static final float cyR = 1.0f;
    private static final float cyS = 0.5f;
    private static final float cyT = 0.116279066f;

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        if (!particle2.Lh) {
            return;
        }
        float f2 = particle2.Hk + particle.getX();
        float f3 = particle2.Hl + particle.getY();
        float f4 = particle2.Hm + particle.id();
        f2 += particleSystem.getX();
        f3 += particleSystem.getY();
        f4 += particleSystem.id();
        if (!Float.isNaN(particle2.KJ)) {
            float f5 = f2 - particle2.KJ;
            float f6 = f3 - particle2.KK;
            float f7 = f4 - particle2.KL;
            if (f5 == 0.0f && f6 == 0.0f && f7 == 0.0f) {
                return;
            }
            float f8 = (f5 - f6) / 4.0f;
            float f9 = (f5 + f6) * 0.5f + f7 * 0.116279066f;
            if ((double)Math.abs(f8) > 1.0E-5) {
                float f10 = (float)Math.atan(f9 / f8);
                float f11 = this.cyQ.I(particle2);
                particle2.KS -= f11 - f10;
                this.cyQ.a(particle2, f10);
            }
        }
        particle2.KJ = f2;
        particle2.KK = f3;
        particle2.KL = f4;
    }
}

