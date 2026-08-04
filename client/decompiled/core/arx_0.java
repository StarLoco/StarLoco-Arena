/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from arx
 */
public class arx_0
extends ua_0 {
    public final float aHh;

    public arx_0(float f) {
        this.aHh = f;
    }

    protected void a(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        float f2 = this.aHh * f;
        Particle particle3 = particle.Lf == null ? particle : particle.Lf;
        particle2.Hk += (particle2.Hl - particle3.getY()) * f2;
        particle2.Hl -= (particle2.Hk - particle3.getX()) * f2;
    }
}

