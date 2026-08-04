/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

/*
 * Renamed from MD
 */
public class md_2
implements adp_0 {
    public final float buk;
    public final float bul;

    public md_2(float f, float f2) {
        this.buk = f;
        this.bul = f2;
    }

    public boolean a(Particle particle, Particle particle2, float f, ParticleSystem particleSystem) {
        float f2 = particle2.KQ;
        if (f2 > this.bul && f2 - f < this.buk) {
            return true;
        }
        return f2 >= this.buk && f2 <= this.bul;
    }
}

