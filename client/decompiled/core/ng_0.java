/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ng
 */
class ng_0 {
    private int NQ;
    private int NR;
    private int NS;
    private int NT;
    private boolean NU = true;

    private ng_0() {
        this.reset();
    }

    private void reset() {
        this.NT = Integer.MAX_VALUE;
        this.NS = Integer.MAX_VALUE;
        this.NR = Integer.MAX_VALUE;
        this.NQ = Integer.MAX_VALUE;
        this.NU = true;
    }

    public boolean j(int n2, int n3, int n4, int n5) {
        boolean bl2 = this.NU = this.NQ != n2 || this.NR != n3 || this.NS != n4 || this.NT != n5;
        if (this.NU) {
            this.NQ = n2;
            this.NR = n3;
            this.NS = n4;
            this.NT = n5;
        }
        return this.NU;
    }

    /* synthetic */ ng_0(lH lH2) {
        this();
    }

    static /* synthetic */ void a(ng_0 ng_02) {
        ng_02.reset();
    }

    static /* synthetic */ boolean b(ng_0 ng_02) {
        return ng_02.NU;
    }
}

