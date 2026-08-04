/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.util.ArrayList;

/*
 * Renamed from UA
 */
public abstract class ua_0 {
    ArrayList jG;

    public void a(adp_0 adp_02) {
        if (this.jG == null) {
            this.jG = new ArrayList(1);
        }
        this.jG.add(adp_02);
    }

    public void b(adp_0 adp_02) {
        if (this.jG == null) {
            return;
        }
        this.jG.remove(adp_02);
    }

    public ArrayList agO() {
        return this.jG;
    }

    public void b(float f, Particle particle, Particle particle2, ParticleSystem particleSystem) {
        if (this.jG != null) {
            int n2 = this.jG.size();
            for (int j = 0; j < n2; ++j) {
                adp_0 adp_02 = (adp_0)this.jG.get(j);
                if (adp_02.a(particle, particle2, f, particleSystem)) continue;
                return;
            }
        }
        this.a(f, particle, particle2, particleSystem);
    }

    public boolean vI() {
        return false;
    }

    protected abstract void a(float var1, Particle var2, Particle var3, ParticleSystem var4);
}

