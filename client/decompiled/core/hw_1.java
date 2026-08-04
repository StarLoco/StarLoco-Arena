/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from hw
 */
final class hw_1
implements tr_2 {
    private int hN = 0;
    final /* synthetic */ aGz vF;

    private hw_1(aGz aGz2) {
        this.vF = aGz2;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean f(short s, short s2) {
        this.hN += this.vF.aqw.ad(s) ^ ha_0.aQ(s2);
        return true;
    }

    /* synthetic */ hw_1(aGz aGz2, ig ig2) {
        this(aGz2);
    }
}

