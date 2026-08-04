/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from oe
 */
final class oe_1 {
    byte QG;
    byte QH;
    int QI;
    int QJ;
    int QK;

    private oe_1() {
        this.reset();
    }

    final void reset() {
        this.QG = 0;
        this.QH = 0;
        this.QI = Integer.MAX_VALUE;
        this.QJ = Integer.MAX_VALUE;
        this.QK = Integer.MAX_VALUE;
    }

    public final boolean initialized() {
        return this.QK != Integer.MAX_VALUE;
    }

    final boolean i(int n2, int n3, int n4) {
        return this.QI == n2 && this.QJ == n3 && this.QK == n4;
    }

    public final void setPosition(int n2, int n3, int n4) {
        this.QI = n2;
        this.QJ = n3;
        this.QK = n4;
    }

    public final String toString() {
        return "{" + this.QI + "," + this.QJ + "," + this.QK + "}";
    }

    /* synthetic */ oe_1(xq_0 xq_02) {
        this();
    }
}

