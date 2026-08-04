/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

public class nt
extends ua_0 {
    public final float Ox;

    public nt(float f) {
        this.Ox = f;
    }

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        float f2 = (33.0f - this.Ox) * f;
        if (this.Ox < 1.0E-4f) {
            particle2.KM = 0.0f;
            particle2.KN = 0.0f;
            particle2.KO = 0.0f;
        } else {
            particle2.KM *= 1.0f - f2;
            particle2.KN *= 1.0f - f2;
            particle2.KO *= 1.0f - f2;
        }
    }
}

