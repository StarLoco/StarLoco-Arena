/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Xm
 */
public enum xm_2 {
    bXk,
    bXl,
    bXm,
    bXn,
    bXo,
    bXp,
    bXq,
    bXr,
    bXs,
    bXt,
    bXu,
    bXv,
    bXw;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static xm_2 iR(int n2) {
        xm_2[] xm_2Array = (xm_2[])xm_2.class.getEnumConstants();
        if (n2 < xm_2Array.length && n2 >= 0 && xm_2Array[n2].hU == n2) {
            return xm_2Array[n2];
        }
        for (xm_2 xm_22 : xm_2Array) {
            if (xm_22.hU != n2) continue;
            return xm_22;
        }
        throw new IllegalArgumentException("No enum " + xm_2.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private xm_2() {
        this.hU = aKW.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private xm_2() {
        void var3_1;
        this.hU = var3_1;
        aKW.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private xm_2() {
        void var3_1;
        this.hU = var3_1.hU;
        aKW.aT(this.hU + 1);
    }
}

