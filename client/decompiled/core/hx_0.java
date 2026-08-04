/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from HX
 */
final class hx_0
implements st_1 {
    private int hN = 0;
    final /* synthetic */ zy_0 bgd;

    private hx_0(zy_0 zy_02) {
        this.bgd = zy_02;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean d(byte by, byte by2) {
        this.hN += this.bgd.auF.F(by) ^ ha_0.aQ(by2);
        return true;
    }

    /* synthetic */ hx_0(zy_0 zy_02, adt_0 adt_02) {
        this(zy_02);
    }
}

