/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aPw
 */
final class apw_0
implements ro_1 {
    private int hN = 0;
    final /* synthetic */ kl_1 epq;

    private apw_0(kl_1 kl_12) {
        this.epq = kl_12;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean g(long l2, short s) {
        this.hN += this.epq.atz.aP(l2) ^ ha_0.aQ(s);
        return true;
    }

    /* synthetic */ apw_0(kl_1 kl_12, akc_2 akc_22) {
        this(kl_12);
    }
}

