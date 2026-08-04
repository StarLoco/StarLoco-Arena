/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aOT
 */
class aot_2
implements px_1 {
    final /* synthetic */ int[] emT;
    final /* synthetic */ hu_2 cDw;

    aot_2(hu_2 hu_22, int[] nArray) {
        this.cDw = hu_22;
        this.emT = nArray;
    }

    public boolean aM(long l2) {
        ee_2 ee_22 = adY.atu().dz(l2);
        if (ee_22 != null && ee_22.NB() != 1) {
            this.emT[0] = this.emT[0] + 1;
        }
        return true;
    }
}

