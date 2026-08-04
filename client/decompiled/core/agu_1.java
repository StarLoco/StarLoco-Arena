/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from agU
 */
public abstract class agu_1 {
    private ArrayList cuF;
    private ArrayList cuG;

    public final boolean awA() {
        return this.cuG != null;
    }

    public final int awB() {
        return this.cuG.size();
    }

    public final ua_0 kD(int n2) {
        return (ua_0)this.cuG.get(n2);
    }

    public final boolean awC() {
        return this.cuF != null;
    }

    public final int awD() {
        return this.cuF.size();
    }

    public final ua_0 kE(int n2) {
        return (ua_0)this.cuF.get(n2);
    }

    public final void a(ua_0 ua_02) {
        if (this.cuF == null) {
            this.cuF = new ArrayList(1);
        }
        this.cuF.add(ua_02);
    }

    public final void b(ua_0 ua_02) {
        if (this.cuF == null) {
            return;
        }
        this.cuF.remove(ua_02);
    }

    public final void c(ua_0 ua_02) {
        if (this.cuG == null) {
            this.cuG = new ArrayList();
        }
        this.cuG.add(ua_02);
    }

    public final void d(ua_0 ua_02) {
        if (this.cuG == null) {
            return;
        }
        this.cuG.remove(ua_02);
    }

    protected final void awE() {
        if (this.cuF != null) {
            this.cuF.clear();
            this.cuF = null;
        }
        if (this.cuG != null) {
            this.cuG.clear();
            this.cuG = null;
        }
    }
}

