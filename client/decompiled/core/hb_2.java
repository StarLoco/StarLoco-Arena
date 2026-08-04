/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Hb
 */
public enum hb_2 {
    bdp,
    bdq;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static hb_2 gb(int n2) {
        hb_2[] hb_2Array = (hb_2[])hb_2.class.getEnumConstants();
        if (n2 < hb_2Array.length && n2 >= 0 && hb_2Array[n2].hU == n2) {
            return hb_2Array[n2];
        }
        for (hb_2 hb_22 : hb_2Array) {
            if (hb_22.hU != n2) continue;
            return hb_22;
        }
        throw new IllegalArgumentException("No enum " + hb_2.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private hb_2() {
        this.hU = iw_1.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private hb_2() {
        void var3_1;
        this.hU = var3_1;
        iw_1.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private hb_2() {
        void var3_1;
        this.hU = var3_1.hU;
        iw_1.aT(this.hU + 1);
    }
}

