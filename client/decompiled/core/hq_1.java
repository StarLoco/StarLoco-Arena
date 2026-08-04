/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.Comparator;
import org.apache.log4j.Logger;

/*
 * Renamed from hq
 */
abstract class hq_1
implements Comparator {
    private static final Logger a = Logger.getLogger(hq_1.class);
    private float vy;
    private float vz;

    hq_1() {
    }

    public final void h(float f, float f2) {
        this.vy = f;
        this.vz = f2;
    }

    public final int a(aph_0 aph_02, aph_0 aph_03) {
        float f;
        kC kC2 = this.a(aph_02);
        kC kC3 = this.a(aph_03);
        float f2 = hq_1.a(kC2, this.vy, this.vz);
        if (f2 == (f = hq_1.a(kC3, this.vy, this.vz))) {
            return 0;
        }
        return f2 < f ? 1 : -1;
    }

    private static float a(kC kC2, float f, float f2) {
        if (kC2 == null) {
            return Float.POSITIVE_INFINITY;
        }
        float f3 = (float)kC2.pi() - f;
        float f4 = (float)kC2.pj() - f2;
        return f3 * f3 + f4 * f4;
    }

    protected abstract kC a(aph_0 var1);
}

