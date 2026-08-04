/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from amy
 */
public enum amy_2 {
    cHn,
    cHo,
    cHp,
    cHq,
    cHr,
    cHs,
    cHt,
    cHu,
    cHv,
    cHw,
    cHx;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static amy_2 lv(int n2) {
        amy_2[] amy_2Array = (amy_2[])amy_2.class.getEnumConstants();
        if (n2 < amy_2Array.length && n2 >= 0 && amy_2Array[n2].hU == n2) {
            return amy_2Array[n2];
        }
        for (amy_2 amy_22 : amy_2Array) {
            if (amy_22.hU != n2) continue;
            return amy_22;
        }
        throw new IllegalArgumentException("No enum " + amy_2.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private amy_2() {
        this.hU = atw_0.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private amy_2() {
        void var3_1;
        this.hU = var3_1;
        atw_0.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private amy_2() {
        void var3_1;
        this.hU = var3_1.hU;
        atw_0.aT(this.hU + 1);
    }
}

