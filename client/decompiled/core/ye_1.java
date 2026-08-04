/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from ye
 */
public abstract class ye_1 {
    public final float KX;
    public final float KY;
    public final float KT;
    public final float KU;
    public final boolean aAe;
    public final float aAf;
    public final float aAg;
    public final float aAh;
    public final float aAi;
    protected boolean aAj;
    public int aAk;
    public final float aAl;
    public final float aAm;
    public final float aAn;
    public final float aAo;
    public final float aAp;
    public final float aAq;
    public final float aAr;
    public final float aAs;
    private int tj;
    public float KZ;
    public float La;
    public float Lb;
    public float Lc;
    public float KV;
    public float KW;

    public ye_1(int n2, float f, float f2, float f3, float f4, float f5, float f6, boolean bl2, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16) {
        this.KX = f;
        this.KY = f2;
        this.KT = f3;
        this.KU = f4;
        this.aAe = bl2;
        this.aAf = f5;
        this.aAg = f6;
        this.aAh = f7;
        this.aAi = f8;
        this.aAk = n2;
        this.aAl = f9;
        this.aAm = f10;
        this.aAn = f11;
        this.aAo = f12;
        this.aAp = f13;
        this.aAq = f14;
        this.aAr = f15;
        this.aAs = f16;
        this.aAj = true;
    }

    public Particle b(ParticleSystem particleSystem) {
        Particle particle = (Particle)yW.FL().a(Particle.it(), Particle.class);
        particle.Le = this;
        return particle;
    }

    public void a(Particle particle) {
        float f = this.KT;
        float f2 = this.KU;
        float f3 = this.aAh;
        if (this.aAe) {
            float f4 = ej_0.hL() * this.aAf;
            f += f4;
            f2 += f4;
        } else {
            if (this.aAf != 0.0f) {
                f += ej_0.hL() * this.aAf;
            }
            if (this.aAg != 0.0f) {
                f2 += ej_0.hL() * this.aAg;
            }
        }
        if (this.aAi != 0.0f) {
            f3 += (ej_0.hL() - 0.5f) * this.aAi;
        }
        particle.KX = this.KX;
        particle.KY = this.KY;
        particle.IT = this.aAo + ej_0.hL() * this.aAs;
        particle.IQ = this.aAl + ej_0.hL() * this.aAp;
        particle.IR = this.aAm + ej_0.hL() * this.aAq;
        particle.IS = this.aAn + ej_0.hL() * this.aAr;
        particle.KT = f;
        particle.KU = f2;
        particle.KS = f3 * ((float)Math.PI / 180);
        particle.KV = this.KV;
        particle.KW = this.KW;
        particle.KZ = this.KZ;
        particle.La = this.La;
        particle.Lb = this.Lb;
        particle.Lc = this.Lc;
    }

    public void b(Particle particle) {
    }

    public abstract boolean isSequence();

    public final int EH() {
        return this.tj;
    }

    public final void et(int n2) {
        this.tj = n2;
    }

    public void j(float f, float f2, float f3, float f4) {
        this.KZ = f;
        this.La = f2;
        this.Lb = f3;
        this.Lc = f4;
    }

    public void v(float f, float f2) {
        this.KV = f;
        this.KW = f2;
    }

    public int EI() {
        return this.aAk;
    }

    public void eu(int n2) {
        this.aAk = n2;
        this.aAj = true;
    }
}

