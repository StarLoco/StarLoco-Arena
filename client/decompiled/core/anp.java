/*
 * Decompiled with CFR 0.152.
 */
public enum anp {
    cIJ,
    cIK;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static anp lz(int n2) {
        anp[] anpArray = (anp[])anp.class.getEnumConstants();
        if (n2 < anpArray.length && n2 >= 0 && anpArray[n2].hU == n2) {
            return anpArray[n2];
        }
        for (anp anp2 : anpArray) {
            if (anp2.hU != n2) continue;
            return anp2;
        }
        throw new IllegalArgumentException("No enum " + anp.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private anp() {
        this.hU = FX.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private anp() {
        void var3_1;
        this.hU = var3_1;
        FX.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private anp() {
        void var3_1;
        this.hU = var3_1.hU;
        FX.aT(this.hU + 1);
    }
}

