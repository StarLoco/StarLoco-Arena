/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from oO
 */
public class oo_0
extends ua_0 {
    public final float IQ;
    public final float IR;
    public final float IS;
    public final float IT;
    public final float aaS;

    public oo_0(float f, float f2, float f3, float f4, float f5) {
        this.IQ = f;
        this.IR = f2;
        this.IS = f3;
        this.IT = f4;
        this.aaS = f5;
    }

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        float f2 = this.aaS * f;
        particle2.IQ -= (particle2.IQ - this.IQ) * f2;
        particle2.IR -= (particle2.IR - this.IR) * f2;
        particle2.IS -= (particle2.IS - this.IS) * f2;
        particle2.IT -= (particle2.IT - this.IT) * f2;
    }
}

