/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from bW
 */
final class bw_0
implements aht_0 {
    private int hN = 0;
    final /* synthetic */ vy_1 hO;

    private bw_0(vy_1 vy_12) {
        this.hO = vy_12;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean a(short s, byte by) {
        this.hN += this.hO.aqw.ad(s) ^ ha_0.aQ(by);
        return true;
    }

    /* synthetic */ bw_0(vy_1 vy_12, dK dK2) {
        this(vy_12);
    }
}

