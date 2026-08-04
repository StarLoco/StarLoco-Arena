/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aaF
 */
public abstract class aaf_2
implements JG {
    private AW cgs = null;
    private int cgt = 0;
    private boolean cgu = false;
    private boolean cgv = true;
    private final Object no = new Object();
    private int aX;

    protected aaf_2() {
    }

    public AW api() {
        return this.cgs;
    }

    public void c(AW aW) {
        this.cgs = aW;
    }

    public int apj() {
        return this.cgt;
    }

    public void jv(int n2) {
        this.cgt = n2;
    }

    public boolean apk() {
        return this.cgu;
    }

    public void cQ(boolean bl2) {
        this.cgu = bl2;
    }

    public Object apl() {
        return this.no;
    }

    public int ao() {
        return this.aX;
    }

    public void h(int n2) {
        this.aX = n2;
    }

    public boolean abG() {
        return true;
    }

    public boolean apm() {
        return this.cgv;
    }

    public void cR(boolean bl2) {
        this.cgv = bl2;
    }

    private void reset() {
        this.cgs = null;
        this.cgt = 0;
        this.cgu = true;
        this.cgv = true;
        this.aX = -1;
    }

    public void b() {
        this.reset();
    }

    public void j() {
        this.reset();
    }
}

