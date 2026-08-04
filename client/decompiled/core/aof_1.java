/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from aOF
 */
public class aof_1
extends ua_0 {
    public final float emD;
    public float emE;

    public aof_1(float f) {
        this.emD = f;
        this.emE = 180.0f;
    }

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        this.emE += this.emD * f;
        if (this.emE >= 360.0f) {
            this.emE -= 360.0f;
        }
        particle2.Hk = particle.getX() + ej_0.l(this.emE) * particle2.KM + 0.4f;
        particle2.Hl = particle.getY() + ej_0.k(this.emE) * particle2.KN - 0.7f;
    }
}

