/*
 * Decompiled with CFR 0.152.
 */
public abstract class akC
implements aBX {
    protected yg_1 cDB;
    protected static final float cDC = 43.0f;
    protected static final float cDD = 21.5f;
    protected static final float cDE = 10.0f;
    private boolean cDF;
    private static int cDG = 1;
    private final int aW = akC.azT();

    public akC() {
        this.reset();
    }

    public final int getId() {
        return this.aW;
    }

    public void dG(boolean bl2) {
        this.cDF = bl2;
    }

    public boolean aiW() {
        return this.cDF;
    }

    public void a(yg_1 yg_12) {
        this.cDB = yg_12;
    }

    public abstract void clear();

    public abstract void reset();

    public abstract void a(float var1);

    public abstract void a(db_2 var1);

    static int azT() {
        if (cDG == Integer.MAX_VALUE) {
            cDG = 0;
        }
        return cDG++;
    }
}

