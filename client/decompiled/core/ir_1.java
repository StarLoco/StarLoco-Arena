/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from IR
 */
public class ir_1
extends ua_0 {
    public final float aeO;
    public final float biy;
    public final float KS;
    public final float biz;
    public final float biA;
    public final float biB;

    public ir_1(float f, float f2, float f3, float f4, float f5, float f6) {
        this.aeO = f;
        this.biy = f2;
        this.KS = f3;
        this.biz = f4;
        this.biA = f5;
        this.biB = f6;
    }

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        particle2.KT += this.aeO;
        particle2.KU += this.biy;
        particle2.KS += this.KS;
    }

    public boolean vI() {
        return true;
    }
}

