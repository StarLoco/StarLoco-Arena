/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from em
 */
final class em_0
implements gg_1 {
    private int hN = 0;
    final /* synthetic */ axu of;

    private em_0(axu axu2) {
        this.of = axu2;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean a(byte by, short s) {
        this.hN += this.of.auF.F(by) ^ ha_0.aQ(s);
        return true;
    }

    /* synthetic */ em_0(axu axu2, awp_0 awp_02) {
        this(axu2);
    }
}

