/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from atm
 */
final class atm_0
implements uu_1 {
    private final aLO cTM;

    atm_0(aLO aLO2) {
        this.cTM = aLO2;
    }

    public final boolean i(long l2, int n2) {
        int n3 = this.cTM.az(l2);
        return n3 >= 0 && this.a(n2, this.cTM.eL(l2));
    }

    private final boolean a(int n2, int n3) {
        return n2 == n3;
    }
}

