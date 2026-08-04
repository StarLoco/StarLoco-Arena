/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from qC
 */
public class qc_1
extends ua_0 {
    public final float aeO;

    public qc_1(float f) {
        this.aeO = f;
    }

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        particle2.KT += this.aeO;
    }

    public boolean vI() {
        return true;
    }
}

