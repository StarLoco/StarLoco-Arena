/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.util.Iterator;
import java.util.LinkedList;

/*
 * Renamed from aFo
 */
public class afo_1 {
    public static final afo_1 dGT = new afo_1();
    private final LinkedList dGU = new LinkedList();
    private volatile boolean ado = true;

    private afo_1() {
    }

    public void ak(boolean bl2) {
        this.ado = bl2;
    }

    public boolean uX() {
        return this.ado;
    }

    public void e(ParticleSystem particleSystem) {
        this.dGU.add(particleSystem);
        akK.cDL.e(particleSystem);
    }

    public void bI(int n2) {
        if (!this.ado) {
            return;
        }
        float f = (float)n2 / 1000.0f;
        Iterator iterator = this.dGU.iterator();
        while (iterator.hasNext()) {
            ParticleSystem particleSystem = (ParticleSystem)iterator.next();
            if (particleSystem.avb() < 0) {
                particleSystem.HF();
                akK.cDL.f(particleSystem);
                iterator.remove();
                continue;
            }
            particleSystem.a(f);
        }
    }
}

