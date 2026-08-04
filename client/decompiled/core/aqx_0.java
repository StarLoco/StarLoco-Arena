/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aqx
 */
final class aqx_0
implements sg_1 {
    private int hN = 0;
    final /* synthetic */ aba_0 cOm;

    private aqx_0(aba_0 aba_02) {
        this.cOm = aba_02;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean f(long l2, long l3) {
        this.hN += this.cOm.atz.aP(l2) ^ ha_0.S(l3);
        return true;
    }

    /* synthetic */ aqx_0(aba_0 aba_02, ahd_2 ahd_22) {
        this(aba_02);
    }
}

