/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from te
 */
public class te_0
extends axS {
    private int amB;
    private int amC;
    private long gC;

    private te_0(aos aos2) {
        super(aos2);
    }

    public Float m() {
        if (this.gC <= (long)this.amB) {
            return Float.valueOf(0.0f);
        }
        int n2 = this.amC + this.amB;
        if (this.gC <= (long)n2) {
            return Float.valueOf(1.0f);
        }
        this.gC -= (long)n2;
        return Float.valueOf(0.0f);
    }

    public void update(int n2) {
        super.update(n2);
        this.gC += (long)n2;
    }

    public final boolean isActive() {
        return ((Float)this.get()).floatValue() != 0.0f;
    }

    /* synthetic */ te_0(aos aos2, dr_1 dr_12) {
        this(aos2);
    }

    static /* synthetic */ int a(te_0 te_02, int n2) {
        te_02.amB = n2;
        return te_02.amB;
    }

    static /* synthetic */ int b(te_0 te_02, int n2) {
        te_02.amC = n2;
        return te_02.amC;
    }
}

