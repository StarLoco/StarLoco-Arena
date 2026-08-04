/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aep
 */
final class aep_2
implements uu_1 {
    private int hN = 0;
    final /* synthetic */ aLO cor;

    private aep_2(aLO aLO2) {
        this.cor = aLO2;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean i(long l2, int n2) {
        this.hN += this.cor.atz.aP(l2) ^ ha_0.aQ(n2);
        return true;
    }

    /* synthetic */ aep_2(aLO aLO2, aeq_1 aeq_12) {
        this(aLO2);
    }
}

