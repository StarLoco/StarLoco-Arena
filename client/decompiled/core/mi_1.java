/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from mi
 */
public enum mi_1 {
    Jf,
    Jg;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static mi_1 ce(int n2) {
        mi_1[] mi_1Array = (mi_1[])mi_1.class.getEnumConstants();
        if (n2 < mi_1Array.length && n2 >= 0 && mi_1Array[n2].hU == n2) {
            return mi_1Array[n2];
        }
        for (mi_1 mi_12 : mi_1Array) {
            if (mi_12.hU != n2) continue;
            return mi_12;
        }
        throw new IllegalArgumentException("No enum " + mi_1.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private mi_1() {
        this.hU = ary_0.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private mi_1() {
        void var3_1;
        this.hU = var3_1;
        ary_0.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private mi_1() {
        void var3_1;
        this.hU = var3_1.hU;
        ary_0.aT(this.hU + 1);
    }
}

