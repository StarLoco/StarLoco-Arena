/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

public class qP
extends ua_0 {
    public final float Hk;
    public final float Hl;
    public final float Hm;

    public qP(float f, float f2, float f3) {
        this.Hk = f;
        this.Hl = f2;
        this.Hm = f3;
    }

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        particle2.KM += this.Hk;
        particle2.KN += this.Hl;
        particle2.KO += this.Hm;
    }
}

