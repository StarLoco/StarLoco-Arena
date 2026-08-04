/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

public class rd
implements adp_0 {
    public final int r;
    public final kp_0 afC;
    public final boolean afD;

    public rd(int n2, kp_0 kp_02, boolean bl2) {
        this.r = n2;
        this.afC = kp_02;
        this.afD = bl2;
    }

    public boolean a(Particle particle, Particle particle2, float f, ParticleSystem particleSystem) {
        switch (this.afC) {
            case bpy: {
                return particle2.Hk >= (float)this.r + (this.afD ? particleSystem.dPy : particle.Hk);
            }
            case bpz: {
                return particle2.Hk <= (float)this.r + (this.afD ? particleSystem.dPy : particle.Hk);
            }
            case bpA: {
                return particle2.Hl >= (float)this.r + (this.afD ? particleSystem.dPz : particle.Hl);
            }
            case bpB: {
                return particle2.Hl <= (float)this.r + (this.afD ? particleSystem.dPz : particle.Hl);
            }
            case bpC: {
                return particle2.Hm >= (float)this.r + (this.afD ? particleSystem.dPA : particle.Hm);
            }
            case bpD: {
                return particle2.Hm <= (float)this.r + (this.afD ? particleSystem.dPA : particle.Hm);
            }
        }
        return true;
    }
}

