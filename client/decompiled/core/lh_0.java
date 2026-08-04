/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Emitter;
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from lh
 */
public class lh_0 {
    public static final int Gn = 100;
    private final aNH Go = do_0.aNC.P();

    public lh_0() {
        this.Go.setEnabled(false);
        assert (this.Go != null);
    }

    public void a(float f, Emitter emitter, ParticleSystem particleSystem) {
        Particle particle = emitter.dnK;
        particle.Lf = emitter.Lf;
        if (particle.KQ > emitter.dnJ.btx) {
            this.Go.setEnabled(true);
        }
        particle.a(particleSystem, f);
        this.Go.qG().d(particle.getX() + particleSystem.getX(), particle.getY() + particleSystem.getY(), particle.id() + particleSystem.id());
        float f2 = particle.IT;
        this.Go.r(particle.IQ * f2, particle.IR * f2, particle.IS * f2);
        this.Go.s(particle.IQ * f2, particle.IR * f2, particle.IS * f2);
        this.Go.u(Math.min(particle.KV * particle.KT, 5.0f));
        if (particle.KQ <= particle.KR && particle.KR != Float.MAX_VALUE) {
            this.unregister();
        }
    }

    public void c(float f, float f2, float f3) {
        this.Go.s(f, f2, f3);
    }

    public void u(float f) {
        this.Go.u(f);
    }

    public void register() {
        do_0.aNC.a(this.Go);
    }

    public void unregister() {
        this.Go.p(System.currentTimeMillis(), 100L);
    }
}

