/*
 * Decompiled with CFR 0.152.
 */
final class HP
implements di_2 {
    private int hN = 0;
    final /* synthetic */ asc bfz;

    private HP(asc asc2) {
        this.bfz = asc2;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean c(int n2, byte by) {
        this.hN += this.bfz.dYI.ie(n2) ^ ha_0.aQ(by);
        return true;
    }

    /* synthetic */ HP(asc asc2, cs_0 cs_02) {
        this(asc2);
    }
}

