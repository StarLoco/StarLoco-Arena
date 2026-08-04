/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from AF
 */
public class af_0
extends ua_0 {
    public final float aHh;
    public final anm_1 aHi;
    public final float Gv;
    public final float Gw;
    public final float aHj;

    public af_0(float f, anm_1 anm_12, float f2, float f3, float f4) {
        this.aHh = f;
        this.aHi = anm_12;
        this.Gv = f2;
        this.Gw = f3;
        this.aHj = f4;
    }

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        float f2 = this.aHh * f;
        float f3 = this.Gv - particle2.Hk;
        float f4 = this.Gw - particle2.Hl;
        float f5 = this.aHj - particle2.Hm;
        if (!particle.Lh) {
            f3 += particle.getX();
            f4 += particle.getY();
            f5 += particle.id();
        }
        float f6 = (float)Math.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        switch (this.aHi) {
            case cID: {
                particle2.Hl += f4 * f2;
                particle2.Hm += f5 * f2;
                break;
            }
            case cIE: {
                particle2.Hk += f3 * f2;
                particle2.Hm += f5 * f2;
                break;
            }
            case cIF: {
                particle2.Hk += f3 * f2;
                particle2.Hl += f4 * f2;
                break;
            }
            case cIG: {
                particle2.Hk += f3 * f2;
                particle2.Hl += f4 * f2;
                particle2.Hm += f5 * f2;
                break;
            }
            default: {
                assert (false) : "Unknown attractor axis";
                break;
            }
        }
    }

    public boolean vI() {
        return true;
    }
}

