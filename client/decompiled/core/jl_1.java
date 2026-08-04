/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from JL
 */
public class jl_1
extends ua_0 {
    public final float tP;
    public final float tR;
    public final float bmh;
    public final float tQ;
    public final float tS;
    public final float bmi;
    public final float bmj;
    public final float bmk;
    public final float bml;
    public final float bmm;
    public final float bmn;
    public final float bmo;

    public jl_1(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12) {
        this.tP = f;
        this.tR = f2;
        this.bmh = f3;
        this.tQ = f4;
        this.tS = f5;
        this.bmi = f6;
        this.bmj = f7;
        this.bmk = f8;
        this.bml = f9;
        this.bmm = f10;
        this.bmn = f11;
        this.bmo = f12;
    }

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        float f2 = particle.getX() + this.tP;
        float f3 = particle.getY() + this.tR;
        float f4 = particle.id() + this.bmh;
        float f5 = particle.getX() + this.tQ;
        float f6 = particle.getY() + this.tS;
        float f7 = particle.id() + this.bmi;
        boolean bl2 = false;
        if (particle2.Hk < f2 && particle2.KJ >= f2) {
            bl2 = true;
            particle2.Hk = f2;
            particle2.KM = -particle2.KM;
        }
        if (particle2.Hk > f5 && particle2.KJ <= f5) {
            bl2 = true;
            particle2.Hk = f5;
            particle2.KM = -particle2.KM;
        }
        if (particle2.Hl < f3 && particle2.KK >= f3) {
            bl2 = true;
            particle2.Hl = f3;
            particle2.KN = -particle2.KN;
        }
        if (particle2.Hl > f6 && particle2.KK <= f6) {
            bl2 = true;
            particle2.Hl = f6;
            particle2.KN = -particle2.KN;
        }
        if (particle2.Hm < f4 && particle2.KL >= f4) {
            bl2 = true;
            particle2.Hm = f4;
            particle2.KO = -particle2.KO;
        }
        if (particle2.Hm > f7 && particle2.KL <= f7) {
            bl2 = true;
            particle2.Hm = f7;
            particle2.KO = -particle2.KO;
        }
        if (bl2) {
            particle2.KM *= this.bmj + ej_0.hL() * this.bmm;
            particle2.KN *= this.bmk + ej_0.hL() * this.bmn;
            particle2.KO *= this.bml + ej_0.hL() * this.bmo;
        }
        particle2.KJ = particle2.Hk;
        particle2.KK = particle2.Hl;
        particle2.KL = particle2.Hm;
    }
}

