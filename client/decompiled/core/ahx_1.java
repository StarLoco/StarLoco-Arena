/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ahx
 */
final class ahx_1
implements aom_1 {
    private int hN = 0;
    final /* synthetic */ afj_0 cvG;

    private ahx_1(afj_0 afj_02) {
        this.cvG = afj_02;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean a(byte by, Object object) {
        this.hN += this.cvG.auF.F(by) ^ ha_0.q(object);
        return true;
    }

    /* synthetic */ ahx_1(afj_0 afj_02, akt_0 akt_02) {
        this(afj_02);
    }
}

