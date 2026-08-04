/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

public class lv
extends ua_0 {
    public final float Hk;
    public final float Hl;
    public final float Hm;
    public final boolean Hn;

    public lv(float f, float f2, float f3, boolean bl2) {
        this.Hk = f;
        this.Hl = f2;
        this.Hm = f3;
        this.Hn = bl2;
    }

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        f *= 33.0f;
        if (this.Hn) {
            particle2.KM += this.Hk * f;
            particle2.KN += this.Hl * f;
            particle2.KO += this.Hm * f;
        } else {
            particle2.Hk += this.Hk * f;
            particle2.Hl += this.Hl * f;
            particle2.Hm += this.Hm * f;
        }
    }
}

